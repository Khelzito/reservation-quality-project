package booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceMockitoTest {
    @Mock
    private RoomRepository repository;

    @InjectMocks
    private ReservationService service;

    private Room room;
    private LocalDateTime start;
    private LocalDateTime end;

    @BeforeEach
    void setUp() {
        room = new Room("ROOM-101", "Salle Innovation", 15);
        start = LocalDateTime.of(2026, 6, 10, 9, 0);
        end = LocalDateTime.of(2026, 6, 10, 10, 0);
    }

    @Test
    void createReservationDoitRetournerUnRecu() {
        when(repository.findById("ROOM-101")).thenReturn(room);
        when(repository.isAvailable("ROOM-101", start, end)).thenReturn(true);
        ReservationReceipt receipt = service.createReservation("ROOM-101", start, end);
        assertNotNull(receipt);
        assertEquals("ROOM-101", receipt.roomId());
        assertEquals(ReservationStatus.CONFIRMED, receipt.status());
    }

    @Test
    void createReservationDoitSauvegarderReservation() {
        when(repository.findById("ROOM-101")).thenReturn(room);
        when(repository.isAvailable("ROOM-101", start, end)).thenReturn(true);
        service.createReservation("ROOM-101", start, end);
        verify(repository).save(any(Reservation.class));
    }

    @Test
    void createReservationDoitVerifierLaDisponibilite() {
        when(repository.findById("ROOM-101")).thenReturn(room);
        when(repository.isAvailable("ROOM-101", start, end)).thenReturn(true);
        service.createReservation("ROOM-101", start, end);
        verify(repository).isAvailable("ROOM-101", start, end);
    }

    @Test
    void createReservationDoitLeverExceptionSiSalleIntrouvable() {
        when(repository.findById("ROOM-404")).thenReturn(null);
        assertThrows(RoomNotFoundException.class, () -> service.createReservation("ROOM-404", start, end));
    }

    @Test
    void createReservationDoitLeverExceptionSiSalleIndisponible() {
        when(repository.findById("ROOM-101")).thenReturn(room);
        when(repository.isAvailable("ROOM-101", start, end)).thenReturn(false);
        assertThrows(RoomUnavailableException.class, () -> service.createReservation("ROOM-101", start, end));
    }

    @Test
    void createReservationNeDoitPasSauvegarderSiSalleIndisponible() {
        when(repository.findById("ROOM-101")).thenReturn(room);
        when(repository.isAvailable("ROOM-101", start, end)).thenReturn(false);
        assertThrows(RoomUnavailableException.class, () -> service.createReservation("ROOM-101", start, end));
        verify(repository, never()).save(any());
    }

    @Test
    void createReservationDoitCapturerLaReservationSauvegardee() {
        when(repository.findById("ROOM-101")).thenReturn(room);
        when(repository.isAvailable("ROOM-101", start, end)).thenReturn(true);
        service.createReservation("ROOM-101", start, end);
        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(repository).save(captor.capture());
        assertEquals("ROOM-101", captor.getValue().getRoom().id());
    }

    @Test
    void createReservationAvecRoomIdNullDoitLeverException() {
        assertThrows(IllegalArgumentException.class, () -> service.createReservation(null, start, end));
    }

    @Test
    void createReservationAvecRoomIdVideDoitLeverException() {
        assertThrows(IllegalArgumentException.class, () -> service.createReservation(" ", start, end));
    }

    @Test
    void createReservationAvecDateInvalideDoitLeverException() {
        assertThrows(IllegalArgumentException.class, () -> service.createReservation("ROOM-101", end, start));
    }
}
