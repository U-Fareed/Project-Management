package Model;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MMember {

    public void loadMemberPerformance(DefaultTableModel model) {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DBConnection.createDBConnection().createStatement();
            rs = st.executeQuery("SELECT Member.M_ID, Member.M_name, Member.M_role, MemberPerformance.M_performance "
                    + "FROM Member "
                    + "LEFT JOIN MemberPerformance ON Member.M_ID = MemberPerformance.M_ID");

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
    
    //Insert value if not exist in database
    public void insertMember(int memberId, float performance) throws SQLException {
        Connection con = DBConnection.createDBConnection();
        String query = "INSERT INTO memberperformance (M_ID, M_performance) VALUES (?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, memberId);
            pst.setFloat(2, performance);

            pst.executeUpdate();
        }
    }
    
    // Check if a member exists in the database
    public boolean memberExists(int memberId) throws SQLException {
        String query = "SELECT COUNT(*) FROM memberperformance WHERE M_ID = ?";
        try (Connection con = DBConnection.createDBConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, memberId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Return true if count > 0
                }
            }
        }
        return false;
    }

    // Update member performance in the database
    public void updateMember(int memberId, float performance) throws SQLException {
        String query = "UPDATE memberperformance SET M_performance = ? WHERE M_ID = ?";
        try (Connection con = DBConnection.createDBConnection(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setFloat(1, performance);
            pst.setInt(2, memberId);

            pst.executeUpdate();
        }
    }

}
