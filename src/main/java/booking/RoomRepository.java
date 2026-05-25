package booking;

import java.time.LocalDateTime;

public interface RoomRepository {
    Room findById(String roomId);
    boolean isAvailable(String roomId, LocalDateTime startDate, LocalDateTime endDate);
    void save(Reservation reservation);
}
