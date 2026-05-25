package booking;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ReservationParameterizedTest {
    @ParameterizedTest
    @CsvSource({"ROOM-101, Salle Innovation, 10", "ROOM-202, Salle Projet, 20", "ROOM-303, Salle Reunion, 6"})
    void plusieursSallesDoiventEtreValides(String id, String name, int capacity) {
        Room room = new Room(id, name, capacity);
        assertEquals(id, room.id());
        assertEquals(name, room.name());
        assertEquals(capacity, room.capacity());
    }

    @ParameterizedTest
    @CsvSource({"2026-06-10T09:30, 2026-06-10T10:30", "2026-06-10T08:30, 2026-06-10T09:30", "2026-06-10T09:15, 2026-06-10T09:45"})
    void plusieursCreneauxDoiventEtreEnConflit(String startText, String endText) {
        Reservation reservation = new Reservation("RES-001", new Room("ROOM-101", "Salle Innovation", 15), LocalDateTime.parse("2026-06-10T09:00"), LocalDateTime.parse("2026-06-10T10:00"));
        assertTrue(reservation.overlaps(LocalDateTime.parse(startText), LocalDateTime.parse(endText)));
    }

    @ParameterizedTest
    @CsvSource({"'', Salle, 10", "' ', Salle, 10"})
    void roomIdInvalideDoitLeverException(String id, String name, int capacity) {
        assertThrows(IllegalArgumentException.class, () -> new Room(id, name, capacity));
    }
}
