package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthModule;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.ordering.*;
import au.edu.sydney.brawndo.erp.spfea.products.ProductDatabase;

import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("Duplicates")
public class SPFEAFacade {
    private AuthToken token;
    private Map<Integer, CustomerImpl> customers;
    private List<Integer> customerIDs;
    private UnitOfWork uow;

    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);
        this.customers = new HashMap<>();
        this.uow = new UnitOfWork(token);

        return null != token;
    }

    /**
     * Retrieves a list of all order IDs associated with the authenticated token.
     *
     * @return a list of order IDs
     * @throws SecurityException if the authentication token is null
     */
    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order: orders) {
            result.add(order.getOrderID());
        }

        return result;
    }


    /**
     * Creates a new order with the specified parameters and returns the order ID.
     *
     * @param customerID        the ID of the customer associated with the order
     * @param date              the date of the order creating
     * @param isBusiness        indicates whether this is a business order
     * @param isSubscription    indicates whether this is a subscription order
     * @param discountType      the type of discount applied to the order
     * @param discountThreshold the discount threshold value
     * @param discountRateRaw   the discount rate value (in percentage)
     * @param numShipments      the number of shipments for the order (applicable for subscription orders)
     * @return the order ID of the created order, or null if the discount type is invalid
     * @throws SecurityException          if the authentication token is null
     * @throws IllegalArgumentException if the discount rate is not a valid percentage or if the customer ID is invalid
     */
    public Integer createOrder(int customerID, LocalDateTime date, boolean isBusiness, boolean isSubscription, int discountType, int discountThreshold, int discountRateRaw, int numShipments) {
        if (null == token) {
            throw new SecurityException();
        }

        if (discountRateRaw < 0 || discountRateRaw > 100) {
            throw new IllegalArgumentException("Discount rate not a percentage");
        }

        double discountRate = 1.0 - (discountRateRaw / 100.0);

        Order order;

        if (!TestDatabase.getInstance().getCustomerIDs(token).contains(customerID)) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();

        // the boolean isBusiness will decide which strategy the order will have
        if (isSubscription) {
            if (1 == discountType) {
                //subscription + discount 1
                order = new NewOrderImplSubscription(id, date, customerID, discountRate, numShipments, isBusiness);
                } else if (2 == discountType) {
                //subscription + discount 2
                order = new BusinessBulkDiscountSubscription(id, customerID, date, discountThreshold, discountRate, numShipments, isBusiness);
            } else {return null;}

        } else {
            if (1 == discountType) {
                //non-subscription + discount 1
                order = new NewOrderImpl(id, date, customerID, discountRate, isBusiness);
            } else if (2 == discountType) {
                //non-subscription + discount 2
                order = new BusinessBulkDiscountOrder(id, customerID, date, discountThreshold, discountRate, isBusiness);
            } else {return null;}
        }

        //add the task of saving order to the uow
        uow.registerOrder(order);

        return order.getOrderID();
    }

    public List<Integer> getAllCustomerIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        if (this.customerIDs == null) {
            TestDatabase database = TestDatabase.getInstance();
            this.customerIDs = database.getCustomerIDs(token);
        }
        return this.customerIDs;
    }

    public Customer getCustomer(int id) {
        if (null == token) {
            throw new SecurityException();
        }
        if (!this.customers.containsKey(id)) {
            this.customers.put(id, new CustomerImpl(token, id));

        }
        return this.customers.get(id);
    }

    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    public List<Product> getAllProducts() {
        if (null == token) {
            throw new SecurityException();
        }

        return new ArrayList<>(ProductDatabase.getTestProducts());
    }

    /**
     * Finalizes an order by saving it to the database and sending an invoice to the customer
     * through the specified contact methods.
     *
     * @param orderID         the ID of the order to be finalized
     * @param contactPriority the priority list of contact methods for sending the invoice
     * @return true if the invoice was successfully sent through any of the contact methods, false otherwise
     * @throws SecurityException if the authentication token is null
     */
    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        List<ContactMethod> contactPriorityAsMethods = new ArrayList<>();
        // add user's inputs for contact methods into the priority list
        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "merchandiser" -> contactPriorityAsMethods.add(ContactMethod.MERCHANDISER);
                    case "email" -> contactPriorityAsMethods.add(ContactMethod.EMAIL);
                    case "carrier pigeon" -> contactPriorityAsMethods.add(ContactMethod.CARRIER_PIGEON);
                    case "mail" -> contactPriorityAsMethods.add(ContactMethod.MAIL);
                    case "phone call" -> contactPriorityAsMethods.add(ContactMethod.PHONECALL);
                    case "sms" -> contactPriorityAsMethods.add(ContactMethod.SMS);
                    default -> {
                    }
                }
            }
        }

        if (contactPriorityAsMethods.size() == 0) { // needs setting to default
            contactPriorityAsMethods = Arrays.asList(
                    ContactMethod.MERCHANDISER,
                    ContactMethod.EMAIL,
                    ContactMethod.CARRIER_PIGEON,
                    ContactMethod.MAIL,
                    ContactMethod.PHONECALL
            );
        }

        //actually save the order to database before accessing from database for finalising
        uow.commit();
        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        order.finalise();

        //save the finalised order into database
        TestDatabase.getInstance().saveOrder(token, order);

        return ContactHandler.sendInvoice(token, getCustomer(order.getCustomer()), contactPriorityAsMethods, order.generateInvoiceData());
    }

    public void logout() {
        //save all orders that is still awaiting inside uow.
        uow.commit();

        AuthModule.logout(token);
        token = null;
    }

    public double getOrderTotalCost(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        uow.commit();
        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return 0.0;
        }

        return order.getTotalCost();
    }

    public void orderLineSet(int orderID, Product product, int qty) {
        if (null == token) {
            throw new SecurityException();
        }

        uow.commit();
        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setProduct(product, qty);
        //add to the saving queue in uow
        uow.registerOrder(order);
    }

    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        uow.commit();
        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        uow.commit();
        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    public List<String> getKnownContactMethods() {if (null == token) {
        throw new SecurityException();
    }

        return ContactHandler.getKnownMethods();
    }
}
