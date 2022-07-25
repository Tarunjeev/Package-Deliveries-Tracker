package cmpt213.assignment3.packagedeliveries.control;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import cmpt213.assignment3.packagedeliveries.model.BookPkg;
import cmpt213.assignment3.packagedeliveries.model.ElectronicPkg;
import cmpt213.assignment3.packagedeliveries.model.Package;
import cmpt213.assignment3.packagedeliveries.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment3.packagedeliveries.model.PerishablePkg;

public class PackageManager {
    private static ArrayList<Package> packList = new ArrayList<>();
    private static final String filename = "data.json";
    private static PackageManager instance;
    public static PackageManager getInstance() {
        if(instance == null) {
            instance = new PackageManager();
        }
        return instance;
    }
    public int getSize() {
        return packList.size();
    }
    public void addPackage(Package pkg) {
        packList.add(pkg);
        Collections.sort(packList);
    }
    public void removePackage(int index) {
        packList.remove(index);
    }
    public String getAllPackages() {
        if(packList.isEmpty()) {
            return "There are no packages to show";
        }
        StringBuilder pkgstring = new StringBuilder();
        int j =0;
        for (Package p : packList ){
            String pstr = "Package no." + (j+1) + "\n" + "Name:" + p.getName() + "\n" +
                    "Notes: " + p.getNotes() + "\n" + "Price : " + p.getPrice() + "\n" +
                    "Weight :" + p.getWeight() + "\n" + "Expected delivery date" + p.getDeliverydate() + "\n";
            pkgstring.append(pstr).append("\n\n");
            j++;
        }
//        for (int i=0; i < packList.size(); i++) {
//            String pstr = "Package no." + (i+1) + "\n" + packList.get(i);
//            pkgstring.append(pstr).append("\n\n");
//        }
        return pkgstring.toString();
    }

    public String getAllOverdue() {
        if(packList.isEmpty()) {
            return "There are no packages to show";
        }
        StringBuilder pkgstring = new StringBuilder();
        int i=0;
        for (Package p : packList ) {
            if(!packList.get(i).isDelivered()) {
                String pstr = "Package no." + (i+1) + "\n" + "Name:" + p.getName() + "\n" +
                        "Notes: " + p.getNotes() + "\n" + "Price : " + p.getPrice() + "\n" +
                        "Weight :" + p.getWeight() + "\n" + "Expected delivery date" + p.getDeliverydate() + "\n";
                pkgstring.append(pstr).append("\n\n");
                i++;
            }
        }
        if(pkgstring.toString().equals("")) {
            return "There are no overdue packages";
        } else {
            return pkgstring.toString();
        }
    }
    public String getAllUpcoming() {
        if(packList.isEmpty()) {
            return "There are no packages to show";
        }
        StringBuilder pkgstring = new StringBuilder();
        int i=0;
        for (Package p : packList) {
            if(packList.get(i).isDelivered()) {
                String pstr = "Package no." + (i+1) + "\n" + "Name:" + p.getName() + "\n" +
                        "Notes: " + p.getNotes() + "\n" + "Price : " + p.getPrice() + "\n" +
                        "Weight :" + p.getWeight() + "\n" + "Expected delivery date" + p.getDeliverydate() + "\n";
                pkgstring.append(pstr).append("\n\n");
                i++;
            }
        }
        if(pkgstring.toString().equals("")) {
            return "There are no upcoming packages";
        } else {
            return pkgstring.toString();
        }
    }
    private static final RuntimeTypeAdapterFactory<Package> runTimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(Package.class, "type")
            .registerSubtype(BookPkg.class, "Book")
            .registerSubtype(ElectronicPkg.class, "Electronic")
            .registerSubtype(PerishablePkg.class, "Perishable");

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
            File packagestored = new File(filename);
            if (packagestored.createNewFile()) {
                System.out.println("File is now created!");
            }
        } catch (IOException e) {
            System.out.println("Error when creating  the file");
            e.printStackTrace();
        }
    }
    public void loadFile() {
        try {
            if (!packList.isEmpty()) {
                Reader reader = Files.newBufferedReader(Paths.get(filename));
                packList = myGson.fromJson(reader, new TypeToken<List<Package>>() {
                }.getType());
                for (Package packet : packList) {
                    if (packet instanceof BookPkg) {
                        packet.setType("Book");
                    } else if (packet instanceof ElectronicPkg) {
                        packet.setType("Electronic");
                    } else if (packet instanceof PerishablePkg ) {
                        packet.setType("Perishable");
                    }
                }
                reader.close();
            }
        } catch (NoSuchFileException e) {
            createFile();
            packList.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeFile() {

        try {
            Writer writer = Files.newBufferedWriter(Paths.get(filename));
            myGson.toJson(packList, writer);
            writer.close();
        } catch (NoSuchFileException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
