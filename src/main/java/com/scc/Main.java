
package com.scc;

import dao.ScenarioDao;
import model.Ticket;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ScenarioDao ticketDao = new ScenarioDao();

    public static void main(String[] args) {

        ticketDao.createTable();

        int choice = 0;

        do {
            displayMenu();
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        addNewTicket();
                        break;

                    case 2:
                        viewSoldTickets();
                        break;

                    case 3:
                        System.out.println(
                                "\nExiting System... Thank you!");
                        break;

                    default:
                        System.out.println(
                                "\nInvalid choice! Select 1, 2, or 3.");
                }

            } catch (NumberFormatException e) {
                choice = 0;
                System.out.println(
                        "\nInvalid input! Please enter a number.");
            }

            System.out.println();

        } while (choice != 3);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("==============================================");
        System.out.println("           EVENT TICKET SALES SYSTEM");
        System.out.println("==============================================");
        System.out.println("[1] Add New Ticket");
        System.out.println("[2] View Sold Tickets");
        System.out.println("[3] Exit System");
        System.out.println("==============================================");
    }

    private static void addNewTicket() {
        System.out.println("\n--- ISSUE EVENT TICKET ---");

        System.out.print("Enter Attendee Name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("ERROR: Name cannot be empty!");
            return;
        }

        System.out.print(
                "Enter Ticket Tier (REGULAR / VIP / VVIP): ");
        String tier = scanner.nextLine().trim().toUpperCase();

        if (!tier.equals("REGULAR")
                && !tier.equals("VIP")
                && !tier.equals("VVIP")) {
            System.out.println("ERROR: Invalid ticket tier!");
            return;
        }

        double price;

        try {
            System.out.print("Enter Price: ");
            price = Double.parseDouble(scanner.nextLine());

            if (!Double.isFinite(price) || price < 0) {
                System.out.println("ERROR: Enter a valid price!");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("ERROR: Invalid price!");
            return;
        }

        System.out.print(
                "Enter Payment Method (CASH / GCASH / CARD): ");
        String payment = scanner.nextLine().trim().toUpperCase();

        if (!payment.equals("CASH")
                && !payment.equals("GCASH")
                && !payment.equals("CARD")) {
            System.out.println("ERROR: Invalid payment method!");
            return;
        }

        Ticket ticket = new Ticket(name, tier, price, payment);

        if (ticketDao.addTicket(ticket)) {
            System.out.println(
                    "\nSUCCESS: Ticket issued to attendee!");
        } else {
            System.out.println(
                    "\nFAILED: Ticket could not be saved.");
        }
    }

    private static void viewSoldTickets() {
        List<Ticket> tickets = ticketDao.getAllTickets();

        System.out.println("\n================ EVENT TICKET PURCHASES ================");

        System.out.printf(
                "| %-4s | %-22s | %-10s | %10s | %-10s |%n",
                "ID", "ATTENDEE NAME", "TICKET TIER",
                "PRICE", "PAYMENT");

        System.out.println(
                "+------+------------------------+------------+------------+------------+");

        for (Ticket ticket : tickets) {
            System.out.printf(
                    "| %-4d | %-22s | %-10s | %10.2f | %-10s |%n",
                    ticket.getId(),
                    ticket.getAttendeeName(),
                    ticket.getTicketTier(),
                    ticket.getPrice(),
                    ticket.getPayment());
        }

        System.out.println(
                "+------+------------------------+------------+------------+------------+");

        System.out.println("Total Records Displayed: " + tickets.size());
    }
}
