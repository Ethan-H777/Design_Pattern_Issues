package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;

public abstract class Handler {
    protected Handler nextHandler;

    public Handler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    /**
     * Handles a request by either processing it or passing it to the next handler in the chain.
     *
     * @param token    the authentication token
     * @param customer the customer for whom the invoice request is being handled
     * @param method   the contact method
     * @param data     the data associated with the invoice
     * @return true if the request was handled, false otherwise
     */
    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (nextHandler != null) {
            return nextHandler.handleRequest(token, customer, method, data);
        }
        return false;
    }
}
