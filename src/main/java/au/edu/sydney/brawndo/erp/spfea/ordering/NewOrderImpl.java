package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessImpl;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessStrategy;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.PersonalImpl;
import au.edu.sydney.brawndo.erp.spfea.products.ProductData;
import au.edu.sydney.brawndo.erp.spfea.products.ProductFlyweightFactory;
import au.edu.sydney.brawndo.erp.spfea.products.ProductImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Note from Frito: this is a business customer, flat discounted order.
 */
@SuppressWarnings("Duplicates")
public class NewOrderImpl implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private int id;
    private LocalDateTime date;
    private int customerID;
    private double discountRate;
    private boolean finalised = false;
    protected boolean isBusiness;
    private BusinessStrategy businessStrategy;

    public NewOrderImpl(int id, LocalDateTime date, int customerID, double discountRate, boolean isBusiness) {
        this.id = id;
        this.date = date;
        this.customerID = customerID;
        this.discountRate = discountRate;
        this.isBusiness = isBusiness;

        if (isBusiness) this.businessStrategy = new BusinessImpl();
        else this.businessStrategy = new PersonalImpl();
    }

    @Override
    public LocalDateTime getOrderDate() {
        return date;
    }

    @Override
    public void setProduct(Product product, int qty) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");

        // We can't rely on like products having the same object identity since they get
        // rebuilt over the network, so we had to check for presence and same values

        for (Product contained: products.keySet()) {
            if (contained.equals(product)) {
                product = contained;
                break;
            }
        }

        products.put(product, qty);
    }

    @Override
    public Set<Product> getAllProducts() {
        return products.keySet();
    }

    @Override
    public int getProductQty(Product product) {
        for (Product contained: products.keySet()) {
            if (contained.equals(product)) {
                product = contained;
                break;
            }
        }

        Integer result = products.get(product);
        return null == result ? 0 : result;
    }

    @Override
    public int getCustomer() {
        return customerID;
    }

    @Override
    public Order copy() {
        Order copy = new NewOrderImpl(id, date, customerID, discountRate, isBusiness);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
    }

    protected double getDiscountRate() {
        return this.discountRate;
    }

    @Override
    public String generateInvoiceData() {
        return businessStrategy.generateInvoiceData(products, getTotalCost(), getTotalCost());
    }


    @Override
    public double getTotalCost() {
        double cost = 0.0;
        for (Product product: products.keySet()) {
            cost +=  products.get(product) * product.getCost() * discountRate;
        }
        return cost;
    }


    protected Map<Product, Integer> getProducts() {
        return products;
    }

    @Override
    public int getOrderID() {
        return id;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    @Override
    public String shortDesc() {
        return String.format("ID:%s $%,.2f", id, getTotalCost());
    }

    @Override
    public String longDesc() {
        double fullCost = 0.0;
        double discountedCost = getTotalCost();
        StringBuilder productSB = new StringBuilder();

        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            double subtotal = product.getCost() * products.get(product);
            fullCost += subtotal;

            productSB.append(String.format("\tProduct name: %s\tQty: %d\tUnit cost: $%,.2f\tSubtotal: $%,.2f\n",
                    product.getProductName(),
                    products.get(product),
                    product.getCost(),
                    subtotal));
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Products:\n" +
                        "%s" +
                        "\tDiscount: -$%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                productSB.toString(),
                fullCost - discountedCost,
                discountedCost
        );
    }

    protected boolean isFinalised() {
        return finalised;
    }
}
