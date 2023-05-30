package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Email;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.CoR.Handler;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;

public class EmailHandler extends Handler {

    public EmailHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (ContactMethod.EMAIL == method) {
            String email = customer.getEmailAddress();
            if (null != email) {
                Email.sendInvoice(token, customer.getfName(), customer.getlName(), data, email);
                return true;
            }

        }
        return super.handleRequest(token, customer, method, data);
    }



}
