package Controller;

import Model.MLogin;
import javax.swing.*;

public class CLogin {
    private JFrame parentFrame;

    // Constructor to set the parent frame
    public CLogin(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public boolean login(String username, String password) {
         MLogin mLogin = new MLogin();
        boolean isValid = mLogin.validateUser(username, password);
        
        SwingUtilities.invokeLater(() -> {
            if (isValid) {
                // Show success message in front of the parent frame
                JOptionPane.showMessageDialog(parentFrame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Show error message in front of the parent frame
                JOptionPane.showMessageDialog(parentFrame, "Invalid Username or Password!", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        return isValid;
    }
}
