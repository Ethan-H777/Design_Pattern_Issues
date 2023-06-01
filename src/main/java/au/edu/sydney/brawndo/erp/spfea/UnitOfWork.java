package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.ArrayList;
import java.util.List;

public class UnitOfWork {
    private List<Order> createdOrders;
    private TestDatabase db;
    private AuthToken token;

    public UnitOfWork(AuthToken token) {
        this.token = token;
        this.db = TestDatabase.getInstance();
        this.createdOrders = new ArrayList<>();
    }

    public void registerOrder(Order order) {
        this.createdOrders.add(order);
    }

    /**
     * Commits the created orders by saving them to the database.
     * If there are no created orders, this method does nothing.
     */
    public void commit() {
        if (createdOrders.isEmpty()) return;

        int index = 0;
        for (Order order : createdOrders) {
            if (index == createdOrders.size() - 1) {
                db.saveOrder(token, order);
            }
            index++;
        }
        this.createdOrders.clear();
    }

}