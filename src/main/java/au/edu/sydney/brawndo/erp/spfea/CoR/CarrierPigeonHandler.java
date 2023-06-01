package au.edu.sydney.brawndo.erp.spfea.CoR;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.contact.CarrierPigeon;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.CoR.Handler;
import au.edu.sydney.brawndo.erp.spfea.ContactMethod;

public class CarrierPigeonHandler extends Handler {
    public CarrierPigeonHandler(Handler nextHandler) {
        super(nextHandler);
    }
    public boolean handleRequest(AuthToken token, Customer customer, ContactMethod method, String data) {
        if (ContactMethod.CARRIER_PIGEON == method) {
            String pigeonCoopID = customer.getPigeonCoopID();
            if (null != pigeonCoopID) {
                CarrierPigeon.sendInvoice(token, customer.getfName(), customer.getlName(), data, pigeonCoopID);
                return true;
            }
        }
        return super.handleRequest(token, customer, method, data);
    }


}
