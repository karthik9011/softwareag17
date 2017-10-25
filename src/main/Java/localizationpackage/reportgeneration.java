package localizationpackage;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by KAMO on 10/25/2017.
 */
public class reportgeneration {

    public static void main() {
        Connection con=null;
        try{

            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/reportdetails","postgres","postgresql");

        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //String query="SELECT *FROM userdetails";
        String query="SELECT * FROM attributedetails ";
        JasperReportBuilder report= DynamicReports.report();
        report.columns(
                // Columns.column("Employee Id","id",DataTypes.integerType()),
                Columns.column("WebElement Name","xpathvalue", DataTypes.stringType()),
                Columns.column("Element Status","status",DataTypes.stringType()))

                .title(
                        Components.text("Testing Status")
                                .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
                .pageFooter(Components.pageXofY())
                .setDataSource(query,con);

        try{
            report.show();
            report.toPdf(new FileOutputStream("C:\\Users\\kamo\\Desktop\\output\\Myfile.pdf"));
        } catch (DRException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



}


