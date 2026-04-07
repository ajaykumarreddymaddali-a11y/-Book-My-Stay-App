import java.util.*;

// ===== RESERVATION =====
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType);
    }
}


// ===== BOOKING HISTORY =====
class BookingHistory {

    // Ordered storage (chronological)
    private List<Reservation> bookingList = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        bookingList.add(reservation);
        System.out.println("Added to history: " + reservation.getReservationId());
    }

    // Retrieve all bookings (read-only)
    public List<Reservation> getAllReservations() {
        return bookingList;
    }

    // Display all bookings
    public void displayHistory() {
        System.out.println("\n===== Booking History =====");

        if (bookingList.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : bookingList) {
            r.display();
        }
    }
}


// ===== REPORT SERVICE =====
class BookingReportService {

    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    // Generate summary report
    public void generateSummaryReport() {

        System.out.println("\n===== Booking Summary Report =====");

        List<Reservation> reservations = history.getAllReservations();

        if (reservations.isEmpty()) {
            System.out.println("No data available.");
            return;
        }

        // Count bookings per room type
        Map<String, Integer> reportMap = new HashMap<>();

        for (Reservation r : reservations) {
            reportMap.put(
                    r.getRoomType(),
                    reportMap.getOrDefault(r.getRoomType(), 0) + 1
            );
        }

        // Display report
        for (String roomType : reportMap.keySet()) {
            System.out.println(roomType + " -> " + reportMap.get(roomType) + " bookings");
        }

        System.out.println("Total Bookings: " + reservations.size());
    }
}


// ===== APPLICATION =====
public class UseCase8BookingHistoryApp {

    public static void main(String[] args) {

        // Initialize history
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        Reservation r1 = new Reservation("RES-101", "Alice", "Single Room");
        Reservation r2 = new Reservation("RES-102", "Bob", "Double Room");
        Reservation r3 = new Reservation("RES-103", "Charlie", "Single Room");

        // Store in history (chronological order)
        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin views history
        history.displayHistory();

        // Generate report
        BookingReportService reportService = new BookingReportService(history);
        reportService.generateSummaryReport();

        System.out.println("\nNote: Reporting does not modify stored data.");
    }
}