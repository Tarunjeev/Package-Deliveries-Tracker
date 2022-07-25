package cmpt213.assignment3.packagedeliveries.model;
import java.io.*;
import java.time.LocalDateTime;

public class PerishablePkg extends Package {
    public PerishablePkg(String name, String notes, double price, double weight, LocalDateTime deliverydate, LocalDateTime expirydate){
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.delivered = delivered;
        this.deliverydate = deliverydate;
        this.expirydate = expirydate;
    }
}