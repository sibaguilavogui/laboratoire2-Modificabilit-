import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Administrateur du système.
 * (Conserve une liste locale des tickets qu’il suit.)
 */
public class Admin {
    private int adminID;
    private String name;
    private String email;

    private final List<Ticket> tickets = new ArrayList<>();

    public Admin(int adminID, String name, String email) {
        this.adminID = adminID;
        this.name = name;
        this.email = email;
    }

    public void assignTicket(Ticket ticket, User user) {
        Objects.requireNonNull(ticket, "ticket");
        Objects.requireNonNull(user, "user");
        ticket.assignTo(user);
        if (!tickets.contains(ticket)) tickets.add(ticket);
        System.out.println(name + " assigne le ticket #" + ticket.getTicketID()
                + " à " + user.getName());
    }

    public void closeTicket(Ticket ticket) {
        Objects.requireNonNull(ticket, "ticket");
        ticket.updateStatus(Status.TERMINE);
        System.out.println(name + " ferme le ticket #" + ticket.getTicketID());
    }

    public List<Ticket> viewAllTickets() {
        return new ArrayList<>(tickets); // copie défensive
    }

    /** Enregistre un ticket pour être suivi par l’admin (optionnel) */
    public void registerTicket(Ticket t) {
        if (t != null && !tickets.contains(t)) tickets.add(t);
    }

    @Override
    public String toString() {
        return "Admin{adminID=" + adminID + ", name='" + name + "', email='" + email + "'}";
    }
}
