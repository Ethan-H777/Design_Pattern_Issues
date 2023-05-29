package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.PhoneCall;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.CoR.Handler;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;

public class PhoneCallHandler extends Handler {
    public PhoneCallHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (ContactMethod.PHONECALL != method) {
            return super.handleRequest(token, customer, method, data);
        } else {
            String phone = customer.getPhoneNumber();
            if (null != phone) {
                PhoneCall.sendInvoice(token, customer.getfName(), customer.getlName(), data, phone);
                return true;
            }
            return false;
        }
    }



}
