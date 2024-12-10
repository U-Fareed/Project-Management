package Controller;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import Model.DBConnection;

public class CReport {
     public void generatePerformanceReport() {
        try {
            String reportPath = "src/View/performancereport.jasper";
            Connection conn = DBConnection.createDBConnection();
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath, null, conn);

            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            System.err.println("Error generating the report: " + e.getMessage());
        }
    }
}
