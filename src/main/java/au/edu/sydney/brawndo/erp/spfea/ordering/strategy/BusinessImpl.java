package au.edu.sydney.brawndo.erp.spfea.ordering.strategy;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessStrategy;

import java.util.Map;

public class BusinessImpl implements BusinessStrategy {
    @Override
    public String generateInvoiceData(Map<Product, Integer> products, double cost, double subCost) {
        return String.format("Your business account has been charged: $%,.2f" +
                "\nPlease see your Brawndo© merchandising representative for itemised details.", cost);
    }
}
