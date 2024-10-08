package nulp.cs.carrentalrestservice.util;

import nulp.cs.carrentalrestservice.event.EmailEvent;


public class EmailContentCreator {
    public static String generateSubjectForStatusEmail(EmailEvent event) {
        return "Your order status has been changed to " +
                event.getCarOrder().getStatus().toString().toLowerCase() + "!";
    }

    public static String generateBodyForStatusEmail(EmailEvent event) {
        return "Dear " + event.getCustomer().getFirstName()
                + ". The status of your order has been changed to "
                + event.getCarOrder().getStatus().toString().toLowerCase();
    }
}
