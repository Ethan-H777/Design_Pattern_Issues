package au.edu.sydney.brawndo.erp.spfea.ordering;


import au.edu.sydney.brawndo.erp.ordering.Order;

public interface DiscountStrategy {
    public double getTotalCost(OrderImpl order);

}
