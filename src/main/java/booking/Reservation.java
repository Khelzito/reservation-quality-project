package booking;

import java.time.LocalDateTime;

public class Reservation {
    private final String id;
    private final Room room;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private ReservationStatus status;

    public Reservation(String id, Room room, LocalDateTime startDate, LocalDateTime endDate) {
        validateId(id);
        validateRoom(room);
        validateSlot(startDate, endDate);
        this.id = id;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ReservationStatus.CONFIRMED;
    }

    public String getId() { return id; }
    public Room getRoom() { return room; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public ReservationStatus getStatus() { return status; }

    public void cancel() {
        if (status == ReservationStatus.CANCELLED) {
            throw new IllegalStateException("Reservation deja annulee");
        }
        status = ReservationStatus.CANCELLED;
    }

    public boolean overlaps(LocalDateTime start, LocalDateTime end) {
        validateSlot(start, end);
        return start.isBefore(endDate) && end.isAfter(startDate);
    }

    private void validateId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Identifiant invalide");
        }
    }

    private void validateRoom(Room value) {
        if (value == null) {
            throw new IllegalArgumentException("Salle invalide");
        }
    }

    private void validateSlot(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || !end.isAfter(start)) {
            throw new IllegalArgumentException("Creneau invalide");
        }
    }
}
