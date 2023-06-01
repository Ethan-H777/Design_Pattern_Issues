package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.spfea.CoR.*;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {
    /**
     * Sends an invoice to the customer through the specified contact methods, based on the priority order.
     *
     * @param token    the authentication token
     * @param customer the customer to whom the invoice is being sent
     * @param priority the list of contact methods in priority order
     * @param data     the invoice data
     * @return true if the invoice was successfully sent through any of the contact methods, false otherwise
     */
    public static boolean sendInvoice(AuthToken token, Customer customer, List<ContactMethod> priority, String data) {
        // set up the chain
        Handler carrier = new CarrierPigeonHandler(null);
        Handler mer = new MerchandiserHandler(carrier);
        Handler phone = new PhoneCallHandler(mer);
        Handler email = new EmailHandler(phone);
        Handler mail = new MailHandler(email);
        Handler sms = new SMSHandler(mail);

        for (ContactMethod method : priority) {
            boolean success = sms.handleRequest(token, customer, method, data);
            if (success) return true;
        }
        return false;
    }

    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Merchandiser",
                "Phone call",
                "SMS"
        );
    }
}
