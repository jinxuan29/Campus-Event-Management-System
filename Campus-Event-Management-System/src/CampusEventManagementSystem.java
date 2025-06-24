import javax.swing.SwingUtilities;
import shared.Navigation;
import view.CampusEventManagementSystemFrame;

public class CampusEventManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 1. Create the frame (this will initialize UI and register pages)
            CampusEventManagementSystemFrame mainFrame = new CampusEventManagementSystemFrame();

            // 2. Initialize navigation system
            Navigation.initialize(mainFrame);

            // 3. Make the frame visible
            mainFrame.setVisible(true);

            // 4. Now safely navigate to default page
            Navigation.navigateTo("HOME");
        });
    }
}
