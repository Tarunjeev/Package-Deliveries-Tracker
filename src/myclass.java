
import com.sun.tools.javac.Main;
import cmpt213.assignment3.packagedeliveries.view.MainFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class myclass {
    public static void main(String[] args)
    {   //create the frame on the event dispatching method
        SwingUtilities.invokeLater(() -> {
            MainFrame app = new MainFrame();
            app.display();
        });
    }


}
