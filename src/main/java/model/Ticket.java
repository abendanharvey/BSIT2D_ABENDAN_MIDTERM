
package model;

public class Ticket {

    private int id;
    private String attendeeName;
    private String ticketTier;
    private double price;
    private String payment;

    public Ticket(int id, String attendeeName,
                  String ticketTier, double price,
                  String payment) {
        this.id = id;
        this.attendeeName = attendeeName;
        this.ticketTier = ticketTier;
        this.price = price;
        this.payment = payment;
    }

    public Ticket(String attendeeName, String ticketTier,
                  double price, String payment) {
        this(0, attendeeName, ticketTier, price, payment);
    }

    public int getId() {
        return id;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    public String getTicketTier() {
        return ticketTier;
    }

    public double getPrice() {
        return price;
    }

    public String getPayment() {
        return payment;
    }
}
