package db;

public class ManageCabs {
    private int id;
    private String vehicle;
    private String driver;
    private int passengers;

    public ManageCabs() {}

    public ManageCabs(String vehicle, String driver, int passengers) {
        this.vehicle = vehicle;
        this.driver = driver;
        this.passengers = passengers;
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
}
