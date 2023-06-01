package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.concurrent.Future;

public class OrderSaverProxy {
    private final OrderSaverScheduler scheduler;

    public OrderSaverProxy(OrderSaverScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Future<Void> doTask(AuthToken token, Order order) {
        return scheduler.schedule(token, order);
    }
}
