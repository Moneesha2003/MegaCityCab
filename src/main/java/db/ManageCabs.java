package db;

public class ManageCabs {
    private int id;
    private String vehicle;
    private String driver;
    private int passengers;
    private double price_per_km;

    public ManageCabs() {}

    public ManageCabs(String vehicle, String driver, int passengers, double price_per_km) {
        this.vehicle = vehicle;
        this.driver = driver;
        this.passengers = passengers;
        this.price_per_km = price_per_km;
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
    
    public double getPrice_per_km() { return price_per_km;}
    public void setPrice_per_km(double price_per_km) { this.price_per_km = price_per_km;}
}
