package au.edu.sydney.brawndo.erp.spfea.ordering.strategy;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessStrategy;

import java.util.Map;

public class BusinessImpl implements BusinessStrategy {
    /**
     * Generates the invoice data for business non-subscription order.
     *
     * @param products the map of products and their quantities
     * @param cost     the total cost for non subscription order
     * @param subCost  the total cost for subscription order
     * @return the generated invoice data
     */
    @Override
    public String generateInvoiceData(Map<Product, Integer> products, double cost, double subCost) {
        return String.format("Your business account has been charged: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", cost);
    }
}
