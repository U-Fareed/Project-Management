package Controller;
import Model.MProject;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CProject {
    
     public void loadProjects(DefaultTableModel model) {
        MProject mProject = new MProject();
        mProject.loadProjects(model);
    }

     public void saveProject(int projectId, String projectName, String description, int time, String difficulty) {
        MProject projectModel = new MProject();
        boolean isSaved=projectModel.saveProject(projectId, projectName, description, time, difficulty);
   if (isSaved) {
            JOptionPane.showMessageDialog(null, "Project details saved successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to save project details. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
