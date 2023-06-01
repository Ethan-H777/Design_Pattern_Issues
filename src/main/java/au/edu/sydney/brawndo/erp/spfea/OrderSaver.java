package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

public class OrderSaver {
    public void save(AuthToken token, Order order) {
        TestDatabase.getInstance().saveOrder(token, order);
    }
}
