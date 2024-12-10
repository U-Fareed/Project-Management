package Model;
import java.sql.*;

public class MAssignTeam {
    private final Connection conn;

    public MAssignTeam() {
        conn = DBConnection.createDBConnection();
    }

    public boolean assignTeam(int projectId, String projectName, int teamId) {
        try {
            String insertQuery = "INSERT INTO assignteam (P_ID, P_name, T_ID) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, projectId);
            insertStmt.setString(2, projectName);
            insertStmt.setInt(3, teamId);
            int rowsInserted = insertStmt.executeUpdate();

            if (rowsInserted > 0) {
                // Update the team table's T_Assigned column
                String updateQuery = "UPDATE team SET T_Assigned = 'YES' WHERE T_ID = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, teamId);
                int rowsUpdated = updateStmt.executeUpdate();

                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTeamAssigned(int teamId) {
        try {
            String query = "SELECT T_Assigned FROM team WHERE T_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, teamId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return "YES".equalsIgnoreCase(rs.getString("T_Assigned"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
