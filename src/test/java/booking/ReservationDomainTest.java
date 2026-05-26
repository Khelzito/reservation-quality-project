package booking;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ReservationDomainTest {
    @Test
    void cancelReservationDoitModifierLeStatut() {
        Reservation reservation = createReservation();
        reservation.cancel();
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }
/*ex cuse pour test*/
    @Test
    void cancelReservationDeuxFoisDoitLeverException() {
        Reservation reservation = createReservation();
        reservation.cancel();
        assertThrows(IllegalStateException.class, reservation::cancel);
    }

    @Test
    void overlapsDoitDetecterUnConflit() {
        Reservation reservation = createReservation();
        assertTrue(reservation.overlaps(LocalDateTime.of(2026, 6, 10, 9, 30), LocalDateTime.of(2026, 6, 10, 10, 30)));
    }

    @Test
    void overlapsDoitRetournerFalseSansConflit() {
        Reservation reservation = createReservation();
        assertFalse(reservation.overlaps(LocalDateTime.of(2026, 6, 10, 11, 0), LocalDateTime.of(2026, 6, 10, 12, 0)));
    }

    @Test
    void roomCapaciteNegativeDoitLeverException() {
        assertThrows(IllegalArgumentException.class, () -> new Room("ROOM-1", "Salle", -1));
    }

    private Reservation createReservation() {
        return new Reservation("RES-001", new Room("ROOM-101", "Salle Innovation", 15), LocalDateTime.of(2026, 6, 10, 9, 0), LocalDateTime.of(2026, 6, 10, 10, 0));
    }
}
