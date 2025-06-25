import javax.swing.SwingUtilities;
import shared.Navigation;
import view.CampusEventManagementSystemFrame;

public class CampusEventManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            CampusEventManagementSystemFrame mainFrame = new CampusEventManagementSystemFrame();

            Navigation.initialize(mainFrame);

            mainFrame.setVisible(true);

            Navigation.navigateTo("HOME");
        });
    }
}
