package au.edu.sydney.brawndo.erp.spfea.ordering;


import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderImpl implements Order {
    private Map<Product, Integer> products = new HashMap<>();
    private int id;
    private LocalDateTime date;
    private int customerID;
    private double discountRate;
    private boolean finalised = false;
    private int discountThreshold;
    private int numShipments;

    private DiscountStrategy discountStrategy;
    private BusinessStrategy businessStrategy;

    public OrderImpl(int id, int customerID, LocalDateTime date, boolean isBusiness, boolean isSubscription, int discountType, int discountThreshold, int discountRateRaw, int numShipments) {
        this.id = id;
        this.date = date;
        this.discountRate = discountRate;
        this.customerID = customerID;
        this.numShipments = numShipments;

        if (discountType == 1) {
            this.discountStrategy = new FlatDiscount();
        } else {
            this.discountStrategy = new BulkDiscount();
        }

        if (isBusiness) this.businessStrategy = new BusinessImpl();
        else this.businessStrategy = new PersonalImpl();

    }


    @Override
    public int getOrderID() {
        return id;
    }

    @Override
    public double getTotalCost() {
        return discountStrategy.getTotalCost(this);
    }

    @Override
    public LocalDateTime getOrderDate() {
        return date;
    }

    @Override
    public void setProduct(Product product, int qty) {

    }

    @Override
    public Set<Product> getAllProducts() {
        return products.keySet();
    }

    @Override
    public int getProductQty(Product product) {
        return 0;
    }

    @Override
    public String generateInvoiceData() {
        return businessStrategy.generateInvoiceData(products, getTotalCost(), getSubCost());
    }

    @Override
    public int getCustomer() {
        return 0;
    }

    @Override
    public void finalise() {

    }

    @Override
    public Order copy() {
        return null;
    }

    @Override
    public String shortDesc() {
        return null;
    }

    @Override
    public String longDesc() {
        return null;
    }

    //subscription methods
    public double getSubCost() {
        return getTotalCost() * numShipments;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public double getDiscountThreshold() {
        return discountThreshold;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

}
