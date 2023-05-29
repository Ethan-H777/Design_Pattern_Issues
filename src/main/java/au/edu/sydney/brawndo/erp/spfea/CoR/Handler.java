package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;

public abstract class Handler {
    protected Handler nextHandler;

    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(token, customer, method, data);
        }
        return false;
    }
}
