package Model;
import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MProjectPerformance {
    //Data for table
    public void loadProjectPerformance(DefaultTableModel model) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DBConnection.createDBConnection().createStatement();
            rs = st.executeQuery("SELECT projects.P_ID, projects.P_name, projectperformance.P_status, projectperformance.P_performance FROM projects JOIN projectperformance ON projects.P_ID = projectperformance.P_ID");

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


    // Calculate performance logic
    public static double calculatePerformance(int overTime, int avgPerformance) {
        // Ensure valid inputs
        if (overTime < 0 || avgPerformance < 0) {
            throw new IllegalArgumentException("Overtime and average performance cannot be negative.");
        }
        //calculate project performance
        double performance = ((overTime / 100.0) * 0.6) + ((avgPerformance / 10.0) * 0.4 );
         // Round to 2 decimal places
        return Math.round(performance * 100.0) / 100.0;
        
    }

    // Insert performance into the database
    public boolean insertPerformance(int projectId, double calculatedPerformance, String status) {
        String sql = "INSERT INTO projectperformance (P_ID, P_performance, P_status) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.createDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, projectId); // Set project ID
            pstmt.setDouble(2, calculatedPerformance); // Set calculated performance
            pstmt.setString(3, status); // Set status

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful
        } catch (SQLException e) {
            System.out.println("Error inserting project performance: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updatePerformance(int projectId, double calculatedPerformance, String status) {
        String sql = "UPDATE projectperformance SET P_performance = ?, P_status = ? WHERE P_ID = ?";

        try (Connection conn = DBConnection.createDBConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, calculatedPerformance); // Set performance
            pstmt.setString(2, status); // Set status
            pstmt.setInt(3, projectId); // Set project ID

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful
        } catch (SQLException e) {
            System.out.println("Error updating project performance: " + e.getMessage());
            return false;
        }
    }
}
