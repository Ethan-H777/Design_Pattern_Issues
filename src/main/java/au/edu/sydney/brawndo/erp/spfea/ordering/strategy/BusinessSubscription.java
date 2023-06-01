package au.edu.sydney.brawndo.erp.spfea.ordering.strategy;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessImpl;

import java.util.Map;

public class BusinessSubscription extends BusinessImpl {
    /**
     * Generates the invoice data for business subscription order.
     *
     * @param products the map of products and their quantities
     * @param cost     the total cost for non subscription order (the recurring cost for subscription order)
     * @param subCost  the total cost for subscription order
     * @return the generated invoice data
     */
    @Override
    public String generateInvoiceData(Map<Product, Integer> products, double cost, double subCost) {
        return String.format("Your business account will be charged: $%,.2f each week, with a total overall cost of: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", cost, subCost);
    }
}
