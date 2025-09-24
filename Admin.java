import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        ticket.updateStatus("TERMINÉ");
        System.out.println(name + " ferme le ticket #" + ticket.getTicketID());
    }

    public List<Ticket> viewAllTickets() {
        return new ArrayList<>(tickets); // copie défensive
    }

    // utilitaire: enregistrer un ticket créé ailleurs
    public void registerTicket(Ticket t) {
        if (t != null && !tickets.contains(t)) tickets.add(t);
    }

    @Override
    public String toString() {
        return "Admin{adminID=" + adminID + ", name='" + name + "', email='" + email + "'}";
    }
}
