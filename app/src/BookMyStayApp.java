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
}


// ===== ADD-ON SERVICE =====
class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}


// ===== ADD-ON SERVICE MANAGER =====
class AddOnServiceManager {

    // Map: Reservation ID -> List of Services
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // View services for a reservation
    public void viewServices(String reservationId) {

        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " (₹" + s.getCost() + ")");
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null) return 0;

        double total = 0;
        for (AddOnService s : services) {
            total += s.getCost();
        }
        return total;
    }
}


// ===== APPLICATION =====
public class UseCase7AddOnServicesApp {

    public static void main(String[] args) {

        // Existing reservation (from booking system)
        Reservation reservation = new Reservation("RES-101", "Alice", "Single Room");

        // Initialize service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Create add-on services
        AddOnService breakfast = new AddOnService("Breakfast", 500);
        AddOnService wifi = new AddOnService("WiFi", 200);
        AddOnService airportPickup = new AddOnService("Airport Pickup", 1500);

        // Guest selects services
        manager.addService(reservation.getReservationId(), breakfast);
        manager.addService(reservation.getReservationId(), wifi);
        manager.addService(reservation.getReservationId(), airportPickup);

        // View selected services
        manager.viewServices(reservation.getReservationId());

        // Calculate additional cost
        double totalCost = manager.calculateTotalCost(reservation.getReservationId());

        System.out.println("\nTotal Add-On Cost: ₹" + totalCost);

        System.out.println("\nNote: Booking and inventory remain unchanged.");
    }
}