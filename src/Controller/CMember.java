package Controller;

import Model.MMember;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class CMember {

    public void loadMemberWithPerformance(DefaultTableModel model) {
        MMember mMperformance = new MMember();
        mMperformance.loadMemberPerformance(model);
    }

    public void addMember(int memberId, int totalRate, int quality) {
        try {
            // Calculate performance
            float performance = ((totalRate / 100.0f) * 60f) + ((quality / 10.0f) * 40f);

            // Initialize the model
            MMember model = new MMember();

            // Check if the member already exists
            if (model.memberExists(memberId)) {
                // Update the record if it exists
                model.updateMember(memberId, performance);
            } else {
                // Insert a new record if it doesn't exist
                model.insertMember(memberId, performance);
            }
        } catch (SQLException e) {
            System.err.println("Error while adding/updating member performance: " + e.getMessage());
        }
    }

}
