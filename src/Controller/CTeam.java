package Controller;
import Model.MTeam;
import javax.swing.JOptionPane;

public class CTeam {
    public boolean saveTeam(int teamId, String teamName) {
        MTeam teamModel = new MTeam();
        boolean isSaved = teamModel.updateTeam(teamId, teamName); 
        if (isSaved) {
            JOptionPane.showMessageDialog(null, "Team details saved successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to save Team details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return isSaved; 
    }
}
