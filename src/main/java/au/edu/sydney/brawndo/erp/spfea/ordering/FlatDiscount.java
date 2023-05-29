package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;



public class FlatDiscount implements DiscountStrategy{
    @Override
    public double getTotalCost(OrderImpl order) {
        double cost = 0.0;
        for (Product product: order.getProducts().keySet()) {
            cost +=  order.getProducts().get(product) * product.getCost() * order.getDiscountRate();
        }
        return cost;
    }

}
