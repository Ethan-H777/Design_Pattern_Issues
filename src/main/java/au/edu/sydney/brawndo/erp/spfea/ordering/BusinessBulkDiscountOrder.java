package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessImpl;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessStrategy;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.PersonalImpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SuppressWarnings("Duplicates")
public class BusinessBulkDiscountOrder implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private final int id;
    private LocalDateTime date;
    private int customerID;
    private double discountRate;
    private int discountThreshold;
    private boolean finalised = false;
    protected boolean isBusiness;
    private BusinessStrategy businessStrategy;

    public BusinessBulkDiscountOrder(int id, int customerID, LocalDateTime date, int discountThreshold, double discountRate, boolean isBusiness) {
        this.id = id;
        this.customerID = customerID;
        this.date = date;
        this.discountThreshold = discountThreshold;
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

        for (Product contained: products.keySet()) {
            // check two products are the same
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
    public void finalise() {
        this.finalised = true;
    }

    protected double getDiscountRate() {
        return this.discountRate;
    }

    protected int getDiscountThreshold() {
        return this.discountThreshold;
    }

    /**
     * Creates a copy of the current order.
     *
     * @return a new Order object that is a copy of the current order
     */
    @Override
    public Order copy() {
        Order copy = new BusinessBulkDiscountOrder(id, customerID, date, discountThreshold, discountRate, isBusiness);
        for (Product product: products.keySet()) {
            copy.setProduct(product, products.get(product));
        }

        return copy;
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

    @Override
    public String generateInvoiceData() {
        return businessStrategy.generateInvoiceData(products, getTotalCost(), getTotalCost());
    }

    /**
     * Calculates the total cost of the order, taking into account any applicable discounts.
     *
     * @return the total cost of the order
     */
    @Override
    public double getTotalCost() {
        double cost = 0.0;
        for (Product product: products.keySet()) {
            int count = products.get(product);
            if (count >= discountThreshold) {
                cost +=  count * product.getCost() * discountRate;
            } else {
                cost +=  count * product.getCost();
            }
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

    protected boolean isFinalised() {
        return finalised;
    }
}
