package Controller;
import Model.MAssignTeam;

public class CAssignTeam {
    public boolean assignTeamToProject(int projectId, String projectName, int teamId) {
        MAssignTeam assignModel = new MAssignTeam();

        // Check if the team is already assigned
        if (assignModel.isTeamAssigned(teamId)) {
            javax.swing.JOptionPane.showMessageDialog(null, "Team is already assigned to a project.", "Error", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Assign the team to the project
        return assignModel.assignTeam(projectId, projectName, teamId);
    }
}
