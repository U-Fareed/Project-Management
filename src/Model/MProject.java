package Model;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MProject {
     public void loadProjects(DefaultTableModel model) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DBConnection.createDBConnection().createStatement();
            rs = st.executeQuery("SELECT P_ID, P_name, P_difficulty, P_time FROM projects");

            ResultSetMetaData rsd = rs.getMetaData();
            int columnCount = rsd.getColumnCount();

            model.setRowCount(0); // Clear existing rows in the table model

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i)); // Fetch each column's data
                }
                model.addRow(row); // Add the row to the table model
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading customer data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (rs != null) {
                try {
                    rs.close(); // Close ResultSet
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (st != null) {
                try {
                    st.close(); // Close Statement
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    
     public boolean saveProject(int projectId, String projectName, String description, int time, String difficulty) {
        try {
            Statement statement = DBConnection.createDBConnection().createStatement();
            String query = "INSERT INTO projects (P_ID, P_name, P_description, P_time, P_difficulty) VALUES ('" +
                            projectId + "', '" + projectName + "', '" + description + "', '" + time + "', '" + difficulty + "')";
            statement.executeUpdate(query);
            return true;    
        } catch (SQLException e) {
            System.err.println("Error saving project: " + e.getMessage());
            return false;
        }
    }
}
