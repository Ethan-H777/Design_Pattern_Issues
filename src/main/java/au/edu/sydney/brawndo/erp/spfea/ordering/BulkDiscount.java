package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;


public class BulkDiscount implements DiscountStrategy{
    @Override
    public double getTotalCost(OrderImpl order) {
        double cost = 0.0;
        for (Product product: order.getProducts().keySet()) {
            int count = order.getProducts().get(product);
            if (count >= order.getDiscountThreshold()) {
                cost +=  count * product.getCost() * order.getDiscountRate();
            } else {
                cost +=  count * product.getCost();
            }
        }
        return cost;
    }

}
