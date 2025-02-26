package db;

public class ManageCabs {
    private int id;
    private String vehicle;
    private int passengers;
    private String number;
    private String driver;

    public ManageCabs() {}

    public ManageCabs(String vehicle, int passengers, String number, String driver) {
        this.vehicle = vehicle;
        this.passengers = passengers;
        this.number = number;
        this.driver = driver;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getVehicle() { return vehicle; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }

    public int getPassengers() { return passengers; }
    public void setPassengers(int passengers) { this.passengers = passengers; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }
}
