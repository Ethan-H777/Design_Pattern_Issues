package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;

import java.time.LocalDateTime;
import java.util.Set;

public interface OrderBuilder {

    int getOrderID();
    double getTotalCost();
    LocalDateTime getOrderDate();
    void setProduct(Product product, int qty);
    Set<Product> getAllProducts();
    int getProductQty(Product product);
    String generateInvoiceData();
    int getCustomer();
    void finalise();
    Order copy();
    String shortDesc();
    String longDesc();

    double getRecurringCost();
    int numberOfShipmentsOrdered();

    Order build();
}
