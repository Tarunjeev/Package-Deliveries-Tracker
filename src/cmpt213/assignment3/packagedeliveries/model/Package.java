package cmpt213.assignment3.packagedeliveries.model;
import java.io.*;
import java.time.LocalDateTime;

public class Package implements Comparable<Package> {
    protected String name;
    protected String authorname;

    protected String notes;
    protected double price;
    protected double handlingfee;

    protected double weight;
    protected LocalDateTime expirydate;
    protected boolean delivered;
    protected LocalDateTime deliverydate;

    protected String type;

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getNotes() {
        return notes;
    }
    public double getWeight() {
        return weight;
    }

    public LocalDateTime getDeliverydate() {
        return deliverydate;
    }

    public double getPrice() {
        return price;
    }


    //    public Package(String pkg) {
//        this.pkg = pkg;
//    }
    public String getName() {
        return name;
    }
    public LocalDateTime getExpirydate() {
        return expirydate;
    }
    public boolean isDelivered() {
        return delivered;
    }



    @Override
    public int compareTo(Package o) {
        return 0;
    }
}
