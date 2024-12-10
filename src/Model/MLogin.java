
package Model;
import java.sql.*;

public class MLogin {
     public boolean validateUser(String username, String password) {
        boolean isValid = false;
        try {
            Connection con = DBConnection.createDBConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM login WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return isValid;
    }
}
