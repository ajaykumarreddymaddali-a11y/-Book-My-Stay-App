/**
 * UseCase2RoomModelingApp
 *
 * Demonstrates object modeling using abstraction, inheritance,
 * encapsulation, and polymorphism in a Hotel Booking System.
 *
 * @author YourName
 * @version 1.0
 */

// Abstract class representing a generic Room
abstract class Room {

    // Encapsulated attributes
    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    // Constructor
    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    // Getters (Encapsulation)
    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    // Abstract method (forces subclasses to implement)
    public abstract void displayRoomDetails();
}

// Concrete class: Single Room
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 2000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price per night: ₹" + getPricePerNight());
    }
}

// Concrete class: Double Room
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 3500.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price per night: ₹" + getPricePerNight());
    }
}

// Concrete class: Suite Room
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 6000.0);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + getNumberOfBeds());
        System.out.println("Price per night: ₹" + getPricePerNight());
    }
}

// Main Application Class
public class UseCase2RoomModelingApp {

    public static void main(String[] args) {

        // Create room objects (Polymorphism)
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static availability (simple variables)
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable = 2;

        System.out.println("===== Available Room Types =====\n");

        // Display Single Room details
        singleRoom.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailable);
        System.out.println("--------------------------------");

        // Display Double Room details
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailable);
        System.out.println("--------------------------------");

        // Display Suite Room details
        suiteRoom.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailable);
        System.out.println("--------------------------------");

        System.out.println("Application terminated.");
    }
}