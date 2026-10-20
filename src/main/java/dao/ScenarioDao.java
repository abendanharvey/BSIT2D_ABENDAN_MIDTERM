
package dao;

import model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tickets (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    attendee_name TEXT NOT NULL,
                    ticket_tier TEXT NOT NULL,
                    price REAL NOT NULL,
                    payment TEXT NOT NULL
                )
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.executeUpdate();
            System.out.println("Database table ready.");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public boolean addTicket(Ticket ticket) {
        String sql = """
                INSERT INTO tickets
                (attendee_name, ticket_tier, price, payment)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ticket.getAttendeeName());
            stmt.setString(2, ticket.getTicketTier());
            stmt.setDouble(3, ticket.getPrice());
            stmt.setString(4, ticket.getPayment());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding ticket: " + e.getMessage());
            return false;
        }
    }

    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = new ArrayList<>();

        String sql = """
                SELECT id, attendee_name, ticket_tier, price, payment
                FROM tickets
                ORDER BY id
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        rs.getString("attendee_name"),
                        rs.getString("ticket_tier"),
                        rs.getDouble("price"),
                        rs.getString("payment")
                );

                tickets.add(ticket);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving tickets: " + e.getMessage());
        }

        return tickets;
    }
}
