package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CustomerScheduler {
    private static final int N_THREADS = 11;
    private final ExecutorService pool;

    public CustomerScheduler() {
        this.pool = Executors.newFixedThreadPool(N_THREADS);
    }

    public Future<String> schedule(AuthToken token, int id, String fieldName) {
        // To schedule just submit to the pool; whatever thread is available within it
        // will pick it up and start working
        return pool.submit(() -> TestDatabase.getInstance().getCustomerField(token, id, fieldName));
    }

    // Pools definitely need shutdowns!
    public void cleanUp() {
        pool.shutdown();
    }
}
