package booking;

public record Room(String id, String name, int capacity) {
    public Room {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Identifiant de salle invalide");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nom de salle invalide");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacite invalide");
        }
    }
}
