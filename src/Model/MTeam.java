package Model;
import java.sql.*;

public class MTeam {
     public boolean updateTeam(int teamId, String teamName) {
        try {
            Statement statement = DBConnection.createDBConnection().createStatement();
            String query = "INSERT INTO team (T_ID, T_name) VALUES ('" +
                            teamId + "', '" + teamName + "')";
            statement.executeUpdate(query);
            return true;  
        } catch (SQLException e) {
            System.err.println("Error updating Team: " + e.getMessage());
            return false;
        }
    }

    
}
