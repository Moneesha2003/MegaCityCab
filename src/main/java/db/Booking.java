package db;

public class Booking {
    private int id;
    private String pickupLocation;
    private String dropoffLocation;
    private int passengers;
    private String vehicleType;
    private double distanceKm;
    private double price;
    private String status;

    public Booking() {}

    public Booking(String pickupLocation, String dropoffLocation, int passengers, String vehicleType, double distanceKm, double price, String status) {
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.passengers = passengers;
        this.vehicleType = vehicleType;
        this.distanceKm = distanceKm;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }

    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
