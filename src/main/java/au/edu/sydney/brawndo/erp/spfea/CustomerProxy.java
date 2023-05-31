package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;

import java.util.concurrent.Future;

public class CustomerProxy {
    private final CustomerScheduler scheduler;

    public CustomerProxy(CustomerScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Future<String> doTask(AuthToken token, int id, String fieldName) {
        return scheduler.schedule(token,id,fieldName);
    }
}
