import java.util.HashMap;
import java.util.Map;

/**
 * UseCase3RoomInventoryApp
 *
 * Demonstrates centralized inventory management using HashMap
 * to maintain room availability in a Hotel Booking System.
 *
 * @author YourName
 * @version 1.0
 */

// Inventory Management Class
class RoomInventory {

    // Centralized data structure (Single Source of Truth)
    private Map<String, Integer> roomAvailability;

    // Constructor initializes inventory
    public RoomInventory() {
        roomAvailability = new HashMap<>();

        // Initialize room types with availability
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    // Retrieve availability for a specific room type
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    // Update availability (controlled modification)
    public void updateAvailability(String roomType, int newCount) {
        if (roomAvailability.containsKey(roomType)) {
            roomAvailability.put(roomType, newCount);
        } else {
            System.out.println("Room type not found: " + roomType);
        }
    }

    // Display full inventory
    public void displayInventory() {
        System.out.println("===== Room Inventory =====");

        for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
            System.out.println(entry.getKey() + " -> Available: " + entry.getValue());
        }
    }
}

// Main Application Class
public class UseCase3RoomInventoryApp {

    public static void main(String[] args) {

        // Initialize inventory (Single Source of Truth)
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        System.out.println("\nUpdating availability...\n");

        // Controlled updates
        inventory.updateAvailability("Single Room", 4);
        inventory.updateAvailability("Suite Room", 1);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication terminated.");
    }
}