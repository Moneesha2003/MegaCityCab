package db;

public class Booking {
    private int id;
    private String vehicle;
    private String driver;
    private int passengers;
    private String pickupLocation;
    private String dropoffLocation;
    private String time;
    private double price;
    private String customerName; 
    private String customerEmail;

    public Booking() {}

    public Booking(String vehicle, String driver, int passengers, String pickupLocation, String dropoffLocation, String time, double price) {
        this.vehicle = vehicle;
        this.driver = driver;
        this.passengers = passengers;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.time = time;
        this.price = price;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getVehicle() { return vehicle; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    
    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }

    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }
    
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    
    public String getDropoffLocation() { return dropoffLocation; }
    public void setDropoffLocation(String dropoffLocation) { this.dropoffLocation = dropoffLocation; }
    
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
}