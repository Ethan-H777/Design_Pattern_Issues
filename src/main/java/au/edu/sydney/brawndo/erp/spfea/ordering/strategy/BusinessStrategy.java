package au.edu.sydney.brawndo.erp.spfea.ordering.strategy;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.util.Map;

public interface BusinessStrategy {
    public String generateInvoiceData(Map<Product, Integer> products, double cost, double subCost);
}
