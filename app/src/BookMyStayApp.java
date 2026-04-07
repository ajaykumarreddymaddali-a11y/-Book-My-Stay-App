import java.util.*;

// ===== CUSTOM EXCEPTIONS =====

// Invalid room type
class InvalidRoomTypeException extends Exception {
    public InvalidRoomTypeException(String message) {
        super(message);
    }
}

// No availability
class NoAvailabilityException extends Exception {
    public NoAvailabilityException(String message) {
        super(message);
    }
}


// ===== RESERVATION =====
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}


// ===== INVENTORY =====
class RoomInventory {
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 1);
        availability.put("Double Room", 1);
        availability.put("Suite Room", 0); // intentionally unavailable
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, -1);
    }

    public void decrement(String roomType) throws NoAvailabilityException {
        int count = availability.get(roomType);

        // Guard against invalid state
        if (count <= 0) {
            throw new NoAvailabilityException("No rooms available for: " + roomType);
        }

        availability.put(roomType, count - 1);
    }
}


// ===== VALIDATOR =====
class BookingValidator {

    private RoomInventory inventory;

    public BookingValidator(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void validate(Reservation reservation)
            throws InvalidRoomTypeException, NoAvailabilityException {

        String roomType = reservation.getRoomType();

        // Validate room type
        if (inventory.getAvailability(roomType) == -1) {
            throw new InvalidRoomTypeException("Invalid room type: " + roomType);
        }

        // Validate availability
        if (inventory.getAvailability(roomType) == 0) {
            throw new NoAvailabilityException("Room not available: " + roomType);
        }
    }
}


// ===== BOOKING SERVICE =====
class BookingService {

    private RoomInventory inventory;
    private BookingValidator validator;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.validator = new BookingValidator(inventory);
    }

    public void bookRoom(Reservation reservation) {

        try {
            // FAIL-FAST validation
            validator.validate(reservation);

            // Safe to proceed
            inventory.decrement(reservation.getRoomType());

            System.out.println("Booking SUCCESS for " + reservation.getGuestName());

        } catch (InvalidRoomTypeException | NoAvailabilityException e) {

            // Graceful failure handling
            System.out.println("Booking FAILED for " + reservation.getGuestName());
            System.out.println("Reason: " + e.getMessage());
        }
    }
}


// ===== APPLICATION =====
public class UseCase9ValidationApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService bookingService = new BookingService(inventory);

        // Test cases
        Reservation r1 = new Reservation("Alice", "Single Room"); // valid
        Reservation r2 = new Reservation("Bob", "Suite Room");    // unavailable
        Reservation r3 = new Reservation("Charlie", "Deluxe Room"); // invalid type

        bookingService.bookRoom(r1);
        bookingService.bookRoom(r2);
        bookingService.bookRoom(r3);

        System.out.println("\nSystem continues running safely.");
    }
}