import java.util.*;
import java.util.concurrent.*;

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
        availability.put("Single Room", 2);
        availability.put("Double Room", 2);
        availability.put("Suite Room", 1);
    }

    // Thread-safe decrement
    public synchronized boolean allocateRoom(String roomType) {
        int count = availability.getOrDefault(roomType, 0);
        if (count > 0) {
            availability.put(roomType, count - 1);
            return true;
        }
        return false;
    }

    public synchronized void display() {
        System.out.println("\nInventory State:");
        for (String type : availability.keySet()) {
            System.out.println(type + " -> " + availability.get(type));
        }
    }
}

// ===== BOOKING QUEUE =====
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    // Thread-safe enqueue
    public synchronized void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request queued for: " + reservation.getGuestName());
    }

    // Thread-safe dequeue
    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }
}

// ===== BOOKING PROCESSOR THREAD =====
class BookingProcessor implements Runnable {

    private BookingQueue bookingQueue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue bookingQueue, RoomInventory inventory) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        Reservation reservation;
        while ((reservation = bookingQueue.getNextRequest()) != null) {
            String guest = reservation.getGuestName();
            String roomType = reservation.getRoomType();

            // Critical section: allocation
            synchronized (inventory) {
                boolean allocated = inventory.allocateRoom(roomType);
                if (allocated) {
                    System.out.println("Booking SUCCESS for " + guest + " (" + roomType + ")");
                } else {
                    System.out.println("Booking FAILED for " + guest + " (" + roomType + "): No availability");
                }
            }
        }
    }
}

// ===== APPLICATION =====
public class UseCase11ConcurrentBookingApp {

    public static void main(String[] args) throws InterruptedException {

        RoomInventory inventory = new RoomInventory();
        BookingQueue bookingQueue = new BookingQueue();

        // Simulate multiple guest requests
        List<Reservation> reservations = Arrays.asList(
                new Reservation("Alice", "Single Room"),
                new Reservation("Bob", "Single Room"),
                new Reservation("Charlie", "Double Room"),
                new Reservation("David", "Suite Room"),
                new Reservation("Eve", "Double Room"),
                new Reservation("Frank", "Suite Room") // intentional overbooking attempt
        );

        // Queue all requests
        for (Reservation r : reservations) {
            bookingQueue.addRequest(r);
        }

        // Start multiple processor threads
        Thread t1 = new Thread(new BookingProcessor(bookingQueue, inventory));
        Thread t2 = new Thread(new BookingProcessor(bookingQueue, inventory));
        Thread t3 = new Thread(new BookingProcessor(bookingQueue, inventory));

        t1.start();
        t2.start();
        t3.start();

        // Wait for threads to finish
        t1.join();
        t2.join();
        t3.join();

        // Display final inventory
        inventory.display();

        System.out.println("\nAll requests processed safely under concurrency.");
    }
}