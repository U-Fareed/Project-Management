package Model;

import java.sql.*;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MTeamPerformance {
    //Table for team performance

    public void loadTeamPerformance(DefaultTableModel model) {
        try (Statement st = DBConnection.createDBConnection().createStatement(); ResultSet rs = st.executeQuery("SELECT team.T_ID, team.T_name, team.T_Assigned, teamperformance.T_performance FROM team JOIN teamperformance ON team.T_ID = teamperformance.T_ID")) {

            ResultSetMetaData rsd = rs.getMetaData();
            int columnCount = rsd.getColumnCount();

            model.setRowCount(0); // Clear existing rows in the table model

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                model.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error loading team performance: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add member to a team
    public void addMemberToTeam(int teamId, int memberId) throws SQLException {
        Connection con = DBConnection.createDBConnection();
        String insertQuery = "INSERT INTO teammembers (T_ID, M_ID) VALUES (?, ?)";
        PreparedStatement pstmt = con.prepareStatement(insertQuery);
        pstmt.setInt(1, teamId);
        pstmt.setInt(2, memberId);
        pstmt.executeUpdate();
    }

    // Remove member from a team
    public void removeMemberFromTeam(int teamId, int memberId) throws SQLException {
        Connection con = DBConnection.createDBConnection();
        String deleteQuery = "DELETE FROM teammembers WHERE T_ID = ? AND M_ID = ?";
        PreparedStatement pstmt = con.prepareStatement(deleteQuery);
        pstmt.setInt(1, teamId);
        pstmt.setInt(2, memberId);
        pstmt.executeUpdate();
    }

    // Calculate team performance
    public double calculateTeamPerformance(int teamId) throws SQLException {
        Connection con = DBConnection.createDBConnection();
        String query = "SELECT AVG(mp.M_performance) AS avgPerformance "
                + "FROM teammembers tm "
                + "JOIN memberperformance mp ON tm.M_ID = mp.M_ID "
                + "WHERE tm.T_ID = ?";
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setInt(1, teamId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble("avgPerformance");
        }
        return 0;
    }

    // Update team performance in the database
    public void updateTeamPerformance(int teamId, double performance) throws SQLException {
        Connection con = DBConnection.createDBConnection();
        String updateQuery = "UPDATE teamperformance SET T_performance = ? WHERE T_ID = ?";
        String insertQuery = "INSERT INTO teamperformance (T_ID, T_performance) VALUES (?, ?)";

        try (PreparedStatement pstmtUpdate = con.prepareStatement(updateQuery)) {
            pstmtUpdate.setDouble(1, performance);
            pstmtUpdate.setInt(2, teamId);

            int rowsAffected = pstmtUpdate.executeUpdate();

            if (rowsAffected == 0) {
                // If no rows were updated, insert a new record
                try (PreparedStatement pstmtInsert = con.prepareStatement(insertQuery)) {
                    pstmtInsert.setInt(1, teamId);
                    pstmtInsert.setDouble(2, performance);
                    pstmtInsert.executeUpdate();
                    System.out.println("New team performance record inserted.");
                }
            } else {
                System.out.println("Team performance updated successfully.");
            }
        }
    }
}
