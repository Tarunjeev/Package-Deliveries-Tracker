package cmpt213.assignment3.packagedeliveries.control;
import cmpt213.assignment3.packagedeliveries.model.BookPkg;
import cmpt213.assignment3.packagedeliveries.model.ElectronicPkg;
import cmpt213.assignment3.packagedeliveries.model.Package;
import cmpt213.assignment3.packagedeliveries.model.PerishablePkg;

import java.time.LocalDateTime;
public class PackageFactory {
    public static Package getInstance(int typepkg, String name, String authorname, String notes, double price,
                                      double weight, LocalDateTime deliverydate, LocalDateTime expirydate, double handlingfee ){
        if (typepkg == 1) {
            return new BookPkg(name, authorname,  notes,  price,  weight, deliverydate);
        } else if (typepkg == 2) {
            return new PerishablePkg(name, notes, price, weight, deliverydate, expirydate);
        } else if(typepkg == 3) {
            return new ElectronicPkg(name, notes, price, weight, deliverydate, handlingfee);
        } else {
            return null;
        }
    }
}
