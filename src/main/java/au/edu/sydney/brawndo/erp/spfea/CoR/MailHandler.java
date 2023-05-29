package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Mail;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.CoR.Handler;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;

public class MailHandler extends Handler {

    public MailHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (ContactMethod.MAIL != method) {
            return super.handleRequest(token, customer, method, data);
        } else {
            String address = customer.getAddress();
            String suburb = customer.getSuburb();
            String state = customer.getState();
            String postcode = customer.getPostCode();
            if (null != address && null != suburb &&
                    null != state && null != postcode) {
                Mail.sendInvoice(token, customer.getfName(), customer.getlName(), data, address, suburb, state, postcode);
                return true;
            }
            return false;
        }
    }

}
