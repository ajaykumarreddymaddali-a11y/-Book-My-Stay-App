import java.util.*;

// ===== RESERVATION MODEL =====

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

    public void displayReservation() {
        System.out.println("Guest: " + guestName + ", Requested Room: " + roomType);
    }
}


// ===== BOOKING REQUEST QUEUE =====

class BookingRequestQueue {

    // FIFO Queue
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    // Add booking request (enqueue)
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request added for " + reservation.getGuestName());
    }

    // View all queued requests (read-only)
    public void displayQueue() {
        System.out.println("\n===== Booking Request Queue =====");

        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }

    // Get next request (without removing - optional for preview)
    public Reservation peekNext() {
        return requestQueue.peek();
    }
}


// ===== APPLICATION ENTRY POINT =====

public class UseCase5BookingQueueApp {

    public static void main(String[] args) {

        // Initialize booking queue
        BookingRequestQueue queue = new BookingRequestQueue();

        // Simulate guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Add requests to queue (FIFO order)
        queue.addRequest(r1);
        queue.addRequest(r2);
        queue.addRequest(r3);

        // Display queue (preserves arrival order)
        queue.displayQueue();

        // Peek next request (no removal, no mutation)
        System.out.println("\nNext request to process:");
        Reservation next = queue.peekNext();
        if (next != null) {
            next.displayReservation();
        }

        System.out.println("\nAll requests are queued. No allocation done yet.");
    }
}