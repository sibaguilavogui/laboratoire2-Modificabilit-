import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Élément de description d’un ticket.
 * - TEXTE  -> utiliser le champ text
 * - IMAGE/VIDEO/FICHIER -> utiliser le champ uri (lien/chemin)
 * Contrainte : { xor text, uri }
 */
public class Description {
    private static final AtomicInteger SEQ = new AtomicInteger(1);

    private final int id;
    private final DescriptionType type;
    private final String text;     // nullable si type != TEXTE
    private final String uri;      // nullable si type == TEXTE
    private final User addedBy;
    private final Date addedAt;

    /** Fabrique pour une description de type TEXTE */
    public static Description ofText(String text, User by) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Le texte ne peut pas être vide.");
        }
        return new Description(DescriptionType.TEXTE, text, null, by);
    }

    /** Fabrique pour un média/fichier (IMAGE/VIDEO/FICHIER) */
    public static Description of(DescriptionType type, String uri, User by) {
        if (type == null || type == DescriptionType.TEXTE) {
            throw new IllegalArgumentException("Type invalide pour une pièce jointe.");
        }
        if (uri == null || uri.isBlank()) {
            throw new IllegalArgumentException("L'URI ne peut pas être vide.");
        }
        return new Description(type, null, uri, by);
    }

    private Description(DescriptionType type, String text, String uri, User by) {
        this.id = SEQ.getAndIncrement();
        this.type = type;
        this.text = text;
        this.uri = uri;
        this.addedBy = by;
        this.addedAt = new Date();
    }

    public int getId() { return id; }
    public DescriptionType getType() { return type; }
    public String getText() { return text; }
    public String getUri() { return uri; }
    public User getAddedBy() { return addedBy; }
    public Date getAddedAt() { return addedAt; }

    @Override
    public String toString() {
        return "Description{id=" + id + ", type=" + type +
                (text != null ? ", text='" + text + "'" : "") +
                (uri  != null ? ", uri='"  + uri  + "'" : "") +
                ", by=" + (addedBy != null ? addedBy.getName() : "n/a") +
                ", at=" + addedAt + "}";
    }
}
