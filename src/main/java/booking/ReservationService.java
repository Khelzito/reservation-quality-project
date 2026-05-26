package booking;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationService {
    private final RoomRepository repository;
    /*excusez moi*/
    public ReservationService(RoomRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository invalide");
        }
        this.repository = repository;
    }

    public ReservationReceipt createReservation(String roomId, LocalDateTime startDate, LocalDateTime endDate) {
        validateRoomId(roomId);
        validateSlot(startDate, endDate);

        Room room = repository.findById(roomId);
        if (room == null) {
            throw new RoomNotFoundException("Salle introuvable");
        }
        if (!repository.isAvailable(roomId, startDate, endDate)) {
            throw new RoomUnavailableException("Salle deja reservee");
        }

        Reservation reservation = new Reservation(UUID.randomUUID().toString(), room, startDate, endDate);
        repository.save(reservation);
        return new ReservationReceipt(reservation.getId(), room.id(), reservation.getStatus(), LocalDateTime.now());
    }

    public ReservationReceipt cancelReservation(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation invalide");
        }
        reservation.cancel();
        return new ReservationReceipt(reservation.getId(), reservation.getRoom().id(), reservation.getStatus(), LocalDateTime.now());
    }

    private void validateRoomId(String roomId) {
        if (roomId == null || roomId.isBlank()) {
            throw new IllegalArgumentException("Room ID invalide");
        }
    }

    private void validateSlot(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            throw new IllegalArgumentException("Creneau invalide");
        }
    }
}
