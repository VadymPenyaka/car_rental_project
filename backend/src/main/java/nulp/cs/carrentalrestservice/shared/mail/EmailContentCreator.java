package nulp.cs.carrentalrestservice.shared.mail;

import nulp.cs.carrentalrestservice.shared.event.OrderEmailEvent;


public class EmailContentCreator {
    public static String generateSubjectForStatusEmail(OrderEmailEvent event) {
        return "Your order status has been changed to " +
                event.getCarOrder().getStatus().toString().toLowerCase() + "!";
    }

    public static String generateBodyForStatusEmail(OrderEmailEvent event) {
        return "Dear " + event.getCustomer().getPerson().getFirstName()
                + ". The status of your order has been changed to "
                + event.getCarOrder().getStatus().toString().toLowerCase();
    }
}
