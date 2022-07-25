package cmpt213.assignment3.packagedeliveries.model;
import java.io.*;
import java.time.LocalDateTime;
public class ElectronicPkg extends Package {
    public ElectronicPkg(String name, String notes, double price, double weight,  LocalDateTime deliverydate, double handlingfee){
        this.name = name;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.delivered = delivered;
        this.deliverydate = deliverydate;
        this.handlingfee = handlingfee;

    }
}