package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.SMS;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.CoR.Handler;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;


public class SMSHandler extends Handler {

    public SMSHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (ContactMethod.SMS != method) {
            return super.handleRequest(token, customer, method, data);
        } else {
            String smsPhone = customer.getPhoneNumber();
            if (null != smsPhone) {
                SMS.sendInvoice(token, customer.getfName(), customer.getlName(), data, smsPhone);
                return true;
            }
            return false;
        }
    }

}
