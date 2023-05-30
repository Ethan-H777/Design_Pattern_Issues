package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.Merchandiser;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.CoR.Handler;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;

public class MerchandiserHandler extends Handler {
    public MerchandiserHandler(Handler nextHandler) {
        super(nextHandler);
    }

    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (ContactMethod.MERCHANDISER == method) {
            String merchandiser = customer.getMerchandiser();
            String businessName = customer.getBusinessName();
            if (null != merchandiser && null != businessName) {
                Merchandiser.sendInvoice(token, customer.getfName(), customer.getlName(), data, merchandiser,businessName);
                return true;
            }
        }
        return super.handleRequest(token, customer, method, data);

    }


}
