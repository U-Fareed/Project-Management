package Controller;
import Model.MProjectPerformance;
import javax.swing.table.DefaultTableModel;


public class CProjectPerformance {
    public void loadProjectPerformance(DefaultTableModel model) {
        MProjectPerformance mPperformance = new MProjectPerformance();
        mPperformance.loadProjectPerformance(model);
    }
    private final MProjectPerformance model;

    public CProjectPerformance() {
        this.model = new MProjectPerformance();
    }

    public boolean insertPerformance(int projectId, double calculatedPerformance, String status) {
        return model.insertPerformance(projectId, calculatedPerformance, status);
    }
    public boolean updatePerformance(int projectId, double calculatedPerformance, String status) {
        return model.updatePerformance(projectId, calculatedPerformance, status);
    }
}
