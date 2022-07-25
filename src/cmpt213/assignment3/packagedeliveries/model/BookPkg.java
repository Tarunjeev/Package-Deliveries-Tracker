package cmpt213.assignment3.packagedeliveries.model;
import java.io.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ArrayList;

public class BookPkg extends Package {
    public BookPkg(String name, String authorname, String notes, double price, double weight, LocalDateTime deliverydate){
        this.name = name;
        this.authorname = authorname;
        this.notes = notes;
        this.price = price;
        this.weight = weight;
        this.delivered = delivered;
        this.deliverydate = deliverydate;
    }

}

