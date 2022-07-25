package cmpt213.assignment3.packagedeliveries.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class TextMenu {
    private final String title = "My Package Deliveries Tracker";
    private final String[] menu = new String[]{"List all packages","Add a package","Remove a package","List overdue packages",
    "List upcoming packages","Mark package as delivered","Exit"};
    private final int options = 7;
    LocalDate LDT = LocalDate.now();
    public void slashes(int num) {
        if (num > 0) {
            System.out.print("#");
            slashes(num - 1);
        }
    }
    public void displayTitle(){
        System.out.println(title);
    }
    public void display() {
        final int morewide = 4;
        System.out.print("\n");
        slashes(title.length() + morewide);
        System.out.println("\n" + "# " + title + " #");
        slashes(title.length() + morewide);
        System.out.println("\n");
        System.out.println("Today is:" + LDT);
        for (int i = 0; i < menu.length; i++) {
            int itemNum = i + 1;
            System.out.println(itemNum + ": " + menu[i]);
        }
    }
    public int displayMenu(){
        display();
        int value = -1;
        Scanner in = new Scanner(System.in);
        while (value < 1 || value > 7) {
            System.out.println("Choose an option by entering 1 - 7: ");
            try {
                value = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException nfe) {
                System.out.println("Not an integer");
            }
        }
        return value;
    }

}
