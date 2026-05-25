package booking;

import java.time.LocalDateTime;

public record ReservationReceipt(
        String reservationId,
        String roomId,
        ReservationStatus status,
        LocalDateTime createdAt
) {}
