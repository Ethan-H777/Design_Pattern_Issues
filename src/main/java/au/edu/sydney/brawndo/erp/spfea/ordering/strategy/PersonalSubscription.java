package au.edu.sydney.brawndo.erp.spfea.ordering.strategy;

import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.strategy.BusinessStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PersonalSubscription implements BusinessStrategy {
    /**
     * Generates the invoice data for personal subscription order.
     *
     * @param products the map of products and their quantities
     * @param cost     the total cost for non subscription order (the recurring cost for subscription order)
     * @param subCost  the total cost for subscription order
     * @return the generated invoice data
     */
    @Override
    public String generateInvoiceData(Map<Product, Integer> products, double cost, double subCost) {
//        Map<Product, Integer> products = getProducts();

        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Brawndo© order!\n");
        sb.append("Your order comes to: $");
        sb.append(String.format("%,.2f", cost));
        sb.append(" each week, with a total overall cost of: $");
        sb.append(String.format("%,.2f",subCost));
        sb.append("\nPlease see below for details:\n");
        List<Product> keyList = new ArrayList<>(products.keySet());
        keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

        for (Product product: keyList) {
            sb.append("\tProduct name: ");
            sb.append(product.getProductName());
            sb.append("\tQty: ");
            sb.append(products.get(product));
            sb.append("\tCost per unit: ");
            sb.append(String.format("$%,.2f", product.getCost()));
            sb.append("\tSubtotal: ");
            sb.append(String.format("$%,.2f\n", product.getCost() * products.get(product)));
        }

        return sb.toString();
    }
}
