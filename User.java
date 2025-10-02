import java.util.Objects;

/**
 * Utilisateur (dev/testeur/etc.)
 */
public class User {
    private int userID;
    private String name;
    private String email;
    private String role;

    public User(int userID, String name, String email, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // createTicket(ticket: Ticket): void
    public void createTicket(Ticket ticket) {
        Objects.requireNonNull(ticket, "ticket");
        if (ticket.getStatus() == null) {
            ticket.updateStatus(Status.OUVERT);
        }
        ticket.touchCreationIfMissing(); // initialise creationDate si null
        System.out.println(name + " crée le ticket #" + ticket.getTicketID());
    }

    public void viewTicket(Ticket ticket) {
        Objects.requireNonNull(ticket, "ticket");
        System.out.println("==== Vue du ticket par " + name + " ====");
        System.out.println(ticket);
    }

    public void updateTicket(Ticket ticket, Status newStatus) {
        Objects.requireNonNull(ticket, "ticket");
        ticket.updateStatus(newStatus);
        System.out.println(name + " met à jour le ticket #" + ticket.getTicketID()
                + " -> status=" + ticket.getStatus());
    }

    // Getters
    public int getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return "User{userID=" + userID + ", name='" + name + "', email='" + email +
                "', role='" + role + "'}";
    }
}
