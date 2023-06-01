package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class OrderSaverScheduler {
    private static final int N_THREADS = 10;
    private OrderSaver task;
    private final ExecutorService pool;

    public OrderSaverScheduler(OrderSaver task) {
        this.task = task;
        this.pool = Executors.newFixedThreadPool(N_THREADS);
    }

    public Future<Void> schedule(AuthToken token, Order order) {
        // To schedule just submit to the pool; whatever thread is available within it
        // will pick it up and start working
        return pool.submit(() -> {
            task.save(token, order);
            return null;
        });
    }

    public void cleanUp() {
        pool.shutdown();
    }
}
