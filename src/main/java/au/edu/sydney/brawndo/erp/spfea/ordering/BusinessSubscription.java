package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.util.Map;

public class BusinessSubscription extends BusinessImpl{
    @Override
    public String generateInvoiceData(Map<Product, Integer> products, double cost, double subCost) {
        return String.format("Your business account will be charged: $%,.2f each week, with a total overall cost of: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", cost, subCost);
    }
}
