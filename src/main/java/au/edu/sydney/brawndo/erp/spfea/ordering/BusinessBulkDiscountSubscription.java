package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessStrategy;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessSubscription;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.PersonalSubscription;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("Duplicates")
public class BusinessBulkDiscountSubscription extends BusinessBulkDiscountOrder implements SubscriptionOrder {
    private int numShipments;
    private BusinessStrategy businessStrategy;

    public BusinessBulkDiscountSubscription(int id, int customerID, LocalDateTime date, int discountThreshold, double discountRate, int numShipments, boolean isBusiness) {
        super(id, customerID, date, discountThreshold, discountRate, isBusiness);
        this.numShipments = numShipments;
        if (isBusiness) this.businessStrategy = new BusinessSubscription();
        else this.businessStrategy = new PersonalSubscription();
    }

    @Override
    public double getRecurringCost() {
        return super.getTotalCost();
    }

    @Override
    public int numberOfShipmentsOrdered() {
        return numShipments;
    }

    @Override
    public double getTotalCost() {
        return super.getTotalCost() * numShipments;
    }

    @Override
    public String generateInvoiceData() {
        return businessStrategy.generateInvoiceData(super.getProducts(), getRecurringCost(), getTotalCost());
    }

    @Override
    public Order copy() {
        Map<Product, Integer> products = super.getProducts();

        Order copy = new BusinessBulkDiscountSubscription(getOrderID(), getCustomer(), getOrderDate(), getDiscountThreshold(), getDiscountRate(), numShipments, isBusiness);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f per shipment, $%,.2f total", super.getOrderID(), getRecurringCost(), super.getTotalCost());
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = super.getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(super.getProducts().keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * super.getProducts().get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    super.getProducts().get(product),
                    product.getCost(),
                    subtotal));
        }

        return String.format(super.isFinalised() ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of shipments: %d\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                super.getOrderID(),
                super.getOrderDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                numShipments,
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost,
                getTotalCost()

        );
    }
}
