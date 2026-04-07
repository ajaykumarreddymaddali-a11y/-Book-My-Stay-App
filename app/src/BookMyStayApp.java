import java.io.*;
import java.util.*;

// ===== RESERVATION =====
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return reservationId + " (" + roomType + ")";
    }
}

// ===== INVENTORY =====
class RoomInventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single Room", 2);
        availability.put("Double Room", 2);
        availability.put("Suite Room", 1);
    }

    public void increment(String roomType) {
        availability.put(roomType, availability.getOrDefault(roomType, 0) + 1);
    }

    public boolean allocateRoom(String roomType) {
        int count = availability.getOrDefault(roomType, 0);
        if (count > 0) {
            availability.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public void display() {
        System.out.println("\nInventory State:");
        for (String type : availability.keySet()) {
            System.out.println(type + " -> " + availability.get(type));
        }
    }
}

// ===== BOOKING HISTORY =====
class BookingHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Reservation> confirmedBookings = new HashMap<>();

    public void addBooking(Reservation r) {
        confirmedBookings.put(r.getReservationId(), r);
    }

    public Reservation getBooking(String reservationId) {
        return confirmedBookings.get(reservationId);
    }

    public void displayBookings() {
        System.out.println("\nConfirmed Bookings:");
        if (confirmedBookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            confirmedBookings.values().forEach(System.out::println);
        }
    }
}

// ===== PERSISTENCE SERVICE =====
class PersistenceService {

    private static final String FILE_NAME = "hotel_system_state.ser";

    public static void saveState(RoomInventory inventory, BookingHistory history) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(inventory);
            oos.writeObject(history);
            System.out.println("\nSystem state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public static Object[] loadState() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("\nNo saved state found. Starting fresh.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            RoomInventory inventory = (RoomInventory) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();
            System.out.println("\nSystem state restored successfully.");
            return new Object[]{inventory, history};
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error restoring state: " + e.getMessage());
            return null;
        }
    }
}

// ===== APPLICATION =====
public class UseCase12PersistenceApp {

    public static void main(String[] args) {

        // Attempt to load previous state
        Object[] restored = PersistenceService.loadState();

        RoomInventory inventory = restored != null ? (RoomInventory) restored[0] : new RoomInventory();
        BookingHistory history = restored != null ? (BookingHistory) restored[1] : new BookingHistory();

        // Simulate booking operations
        Reservation r1 = new Reservation("RES-101", "Single Room");
        Reservation r2 = new Reservation("RES-102", "Double Room");

        if (inventory.allocateRoom(r1.getRoomType())) history.addBooking(r1);
        if (inventory.allocateRoom(r2.getRoomType())) history.addBooking(r2);

        // Display current state
        inventory.display();
        history.displayBookings();

        // Save state before shutdown
        PersistenceService.saveState(inventory, history);
    }
}