package cmpt213.assignment3.packagedeliveries.view;
import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonReader;
import java.nio.file.Files;
import com.google.gson.reflect.TypeToken;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import cmpt213.assignment3.packagedeliveries.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment3.packagedeliveries.model.Package;
import cmpt213.assignment3.packagedeliveries.control.PackageFactory;
import cmpt213.assignment3.packagedeliveries.model.BookPkg;
import cmpt213.assignment3.packagedeliveries.model.ElectronicPkg;
import cmpt213.assignment3.packagedeliveries.model.PerishablePkg;
import jdk.swing.interop.SwingInterOpUtils;

public class MainMenu {
    private static final String fileName = "data1.json";
    private static ArrayList<Package> arr = new ArrayList<>();
    public static final DateTimeFormatter DATE_TIME_STYLE_MEDIUM = DateTimeFormatter.ofLocalizedDateTime(
            FormatStyle.MEDIUM,
            FormatStyle.SHORT
    );
    private static  int getInt(){
        int number;
        Scanner in = new Scanner(System.in);
        while(true) {
            try {
                number = Integer.parseInt(in.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("input is not a Integer");
            }
        }
    }
    public static double getDouble(){
        double number;
        Scanner in = new Scanner(System.in);
        while(true) {
            try {
                number = Double.parseDouble(in.nextLine());
                return number;
            }catch (NumberFormatException e) {
                System.out.println("input is not a double");
            }
        }
    }
    private static void Pacakgelist(){
        if (arr.isEmpty()){
            System.out.println("No packages to show");
        }
        int index = 1;
        for (Package pkg : arr) {
            System.out.println("Package #" + index);
            System.out.println("Notes: " + pkg.getNotes());
            System.out.println("Price: " + pkg.getPrice());
            System.out.println("Weight: " + pkg.getWeight());
            System.out.println("Expected Delivery Date: " + pkg.getDeliverydate());
            System.out.println("Delivered: " + pkg.isDelivered());
            index++;
        }
    }
    private static void  addPackage(){
        int packageYear;
        int packageMonth;
        int packageDay;
        int packageHour;
        int packageMinute;
        int packageSecond;
        boolean isPrint = true;
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the type of pacakge (1:Book, 2:Perishable, 3:Electronic)");
        int numType = getInt();
        while(numType < 1 || numType > 3) {
            System.out.println("Error: type must be 1-3");
            System.out.println("Enter the type of pacakge (1:Book, 2:Perishable, 3:Electronic)");
            numType = getInt();
        }
        String type;
        if (numType == 1) {
            type = "Book";
        } else if (numType == 2) {
            type = "Perishable";
        } else {
            type = "Electronic";
        }
        System.out.println("Enter the name of the" + type + ":");
        String pkgName = in.nextLine();
        while (pkgName.equals("")) {
            System.out.println("Enter the name of the" + type + ":");
            pkgName = in.nextLine();
        }
        System.out.println("Enter the notes of the" + type + ":");
        String pkgNotes = in.nextLine();
        System.out.println("Enter the price of the" + type + ":");
        double pkgprice = getDouble();
        while (pkgprice < 0) {
            System.out.println("Price can't be negative");
            System.out.println("Enter the price of the" + type + ":");
            pkgprice = getDouble();
        }
        System.out.println("Enter the weight of the" + type + ":");
        double pkgweight = getDouble();
        while (pkgweight < 0) {
            System.out.println("Weight can't be negative");
            System.out.println("Enter the weight of the" + type + ":");
            pkgweight = getDouble();
        }
        LocalDateTime deliverydate;
        do {
            System.out.println("Enter the year of delivery date");
            packageYear = Integer.parseInt(in.nextLine());
            System.out.println("Enter the month of delivery date");
            packageMonth = Integer.parseInt(in.nextLine());
            System.out.println("Enter the day of the expected delivery date(1-28/29/20/31");
            packageDay = Integer.parseInt(in.nextLine());
            System.out.println("Enter the hour of the expected delivery time 0-23)");
            packageHour = Integer.parseInt(in.nextLine());
            System.out.println("Enter the minute of the expected delivery date (0-59)");
            packageMinute = Integer.parseInt(in.nextLine());
            System.out.println("Enter the second of the expected delivery date (0-59)");
            packageSecond = Integer.parseInt(in.nextLine());
            deliverydate = LocalDateTime.of(packageYear, packageMonth, packageDay, packageHour, packageMinute, packageSecond);
            try {
                isPrint = true;
//                  deliverydate.getHour();
            } catch (Exception e) {
                System.out.println("\nInvalid date. Try inputting date again");
                isPrint = false;
            }
        } while (!isPrint);
        Package newpackage;
        String pkgauthor;
        LocalDateTime pkgexpiry = LocalDateTime.now();
        int expiryYear;
        int expiryMonth;
        int expirydate;
        int expiryHour;
        int expiryMin;
        double pkghandling = 0.0;
        if (numType == 1) {
            System.out.println("Enter author of the book");
            pkgauthor = in.nextLine();
            newpackage = PackageFactory.getInstance(numType, pkgName, pkgauthor, pkgNotes, pkgprice, pkgweight, deliverydate,pkgexpiry, pkghandling);
        } else if (numType==2) {
            pkgauthor="";
            System.out.println("Enter the year of delivery date:");
            expiryYear = Integer.parseInt(in.nextLine());
            System.out.println("Enter the month of the expiry date (1-12):");
            expiryMonth = Integer.parseInt(in.nextLine());
            System.out.println("Enter the day of the expiry date (1-28/29/30/31");
            expirydate = Integer.parseInt(in.nextLine());
            System.out.println("Enter the hour of the expiry date (0-23)");
            expiryHour = Integer.parseInt(in.nextLine());
            System.out.println("Enter the minute of the expiry date (0-59");
            expiryMin = Integer.parseInt(in.nextLine());
            pkgexpiry = LocalDateTime.of(expiryYear,expiryMonth,expirydate,expiryHour,expiryMin);
            newpackage = PackageFactory.getInstance(numType, pkgName, pkgauthor, pkgNotes, pkgprice, pkgweight, deliverydate,pkgexpiry, pkghandling);

        } else {
            pkgauthor="";
            System.out.println("Enter the environmental handling fee (in dollar):");
            pkghandling = getDouble();
            newpackage = PackageFactory.getInstance(numType, pkgName, pkgauthor, pkgNotes, pkgprice, pkgweight, deliverydate,pkgexpiry, pkghandling);
        }
        arr.add(newpackage);
        System.out.println(pkgName + "has been added to the list");
    }
    private static void removePackage(){
        Pacakgelist();
        int delindex = -1;
        while(delindex < 1 || delindex > arr.size()) {
            System.out.println("Enter the package number you want to remove and choose 0 to cancel");
            delindex = getInt();
            if (delindex == 0) {
                return;
            }
        }
        Package removal = arr.get(delindex-1);
        arr.remove(delindex-1);
        System.out.println(removal.getName() + "has been removed from the list");
    }
    private static void overduePackages() {
        if (arr.isEmpty()) {
            System.out.println("No packages in the list");
        }
        int choice = 1;
        int numb = 1;
        boolean flag = true;
        for (Package pack: arr) {
            choice = pack.getDeliverydate().compareTo(LocalDateTime.now());
            if(pack.isDelivered() == false && choice == -1) {
                System.out.println("Package #" + numb);
                System.out.println(pack);
                flag = false;
                numb++;
            }
        }
        if (flag && arr.size() != 0) {
            System.out.println("No overdue packages found");
        }
    }
    private static void upcomingPackages(){
        if (arr.isEmpty()) {
            System.out.println("No packages in the list");
        }
        int choice = 1;
        int numb = 1;
        boolean flag = true;
        for (Package item: arr) {
            if(item.isDelivered() == false) {
                System.out.println("Package #" + numb);
                System.out.println(item);
                flag = false;
                numb++;
            }
        }
        if(flag && arr.size()!=0) {
            System.out.println("No upcoming packages in the list");
        }
    }
    private static void markPackages() {
        int getval;
        do {
            System.out.println("Please select a package number you want to mark as complete or 0 to cancel ");
            getval = getInt();
        }while(getval > arr.size() || getval < 0);
        if (getval != 0) {
            int index = getval - 1;
            arr.get(index).setDelivered(true);
            System.out.println(arr.get(index).getName() + "has been delivered");
        } else {
            //break;
        }
    }
    private static final RuntimeTypeAdapterFactory<Package> runTimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Package.class, "type")
            .registerSubtype(BookPkg.class, "Book")
            .registerSubtype(PerishablePkg.class, "Perishable")
            .registerSubtype(ElectronicPkg.class, "Electronic");

    private static final Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
            new TypeAdapter<LocalDateTime>() {
                @Override
                public void write(JsonWriter jsonWriter,
                                  LocalDateTime localDateTime) throws IOException {
                    jsonWriter.value(localDateTime.toString());
                }

                @Override
                public LocalDateTime read(JsonReader jsonReader) throws IOException {
                    return LocalDateTime.parse(jsonReader.nextString());
                }
            }).registerTypeAdapterFactory(runTimeTypeAdapterFactory).create();
    private static void createFile() {
        try {
            File pkgsys = new File(fileName);
            if (pkgsys.createNewFile()) {
                System.out.println("File data.json created!");
            }
        } catch (IOException e) {
            System.out.println("Error while creating file");
            e.printStackTrace();
        }
    }

    private static void loadFile() {

        try {
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            arr = myGson.fromJson(reader, new TypeToken<List<Package>>() {
            }.getType());
            for (Package packg : arr) {
                if (packg instanceof BookPkg) {
                    packg.setType("Book");
                } else if (packg instanceof PerishablePkg) {
                    packg.setType("Perishable");
                } else {
                    packg.setType("Electronic");
                }
            }
            reader.close();
        } catch (NoSuchFileException e) {
            //if the file is not there, create it
            createFile();
            arr.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeFile() {

        try {
            Writer writer = Files.newBufferedWriter(Paths.get(fileName));
            myGson.toJson(arr, writer);
            writer.close();

        } catch (NoSuchFileException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mainMenu() {
        loadFile();
        TextMenu menu = new TextMenu();
        menu.displayTitle();
        int choice = 0;
        while (choice != 7) {
            choice = menu.displayMenu();
            switch (choice) {
                case 1 -> Pacakgelist();
                case 2 -> addPackage();
                case 3 -> removePackage();
                case 4 -> overduePackages();
                case 5 -> upcomingPackages();
                case 6 -> markPackages();
                case 7 -> writeFile();
                default -> System.out.println("Choose an option by entering 1-7:");
            }
        }
    }
}
