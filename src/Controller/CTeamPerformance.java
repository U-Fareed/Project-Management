package Controller;

import Model.MTeamPerformance;
import javax.swing.table.DefaultTableModel;

public class CTeamPerformance {

    private final MTeamPerformance model;

    public CTeamPerformance() {
        this.model = new MTeamPerformance();
    }

    public void addMember(int teamId, int memberId) throws Exception {
        model.addMemberToTeam(teamId, memberId);
        double performance = model.calculateTeamPerformance(teamId);
        model.updateTeamPerformance(teamId, performance);
    }

    public void removeMember(int teamId, int memberId) throws Exception {
        model.removeMemberFromTeam(teamId, memberId);
        double performance = model.calculateTeamPerformance(teamId);
        model.updateTeamPerformance(teamId, performance);
    }

    public void loadTeamPerformance(DefaultTableModel model) {
        MTeamPerformance mMperformance = new MTeamPerformance();
        mMperformance.loadTeamPerformance(model);
    }

    public double getPerformance(int teamId) throws Exception {
        return model.calculateTeamPerformance(teamId);
    }

    public void updateTeamPerformance(int teamId, double updatedPerformance) throws Exception {
        model.updateTeamPerformance(teamId, updatedPerformance);
    }
}
