package localizationpackage;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by KAMO on 10/25/2017.
 */
public class validation {

    //Creating the Selenium Driver

    public String RetrievingAttribute(WebDriver firefoxdriver, String pathvalue)
    {
        //Finding an Element
        By ElementbyPath=By.xpath(pathvalue);


        //wait for an element
        WebDriverWait wait = new WebDriverWait(firefoxdriver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(pathvalue)));


        WebElement web_element=firefoxdriver.findElement(ElementbyPath);
        String elementtype= web_element.getTagName();
        if(elementtype.equals("input"))
            return web_element.getAttribute("value");

        else {
            String attributeName = web_element.getText();
            firefoxdriver.close();
            return attributeName;
        }
    }

    //Getting the Property Value
    public String ReadProperty(String property_key) throws IOException {

        Properties prop=new Properties();
        FileInputStream fs=new FileInputStream("src/main/resources/credential_en_US.properties");
        prop.load(fs);
        String property_element=prop.getProperty(property_key);
        return property_element;
    }


    public static void main(String[] args) throws IOException, ParseException, SQLException {


        //db connection
        Connection con=null;
        try{

            Class.forName("org.postgresql.Driver");
            con= DriverManager.getConnection("jdbc:postgresql://localhost:5432/reportdetails","postgres","postgresql");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        //ArrayList to store Xpath and keys from json File
        ArrayList<String> xpath_values=new ArrayList<String>();
        ArrayList<String> key_data=new ArrayList<String>();

        fileread readJsonFile=new fileread();

        //Creating the Webdriver
        WebDriver drivername =new FirefoxDriver();
        String name="https://www.facebook.com/";
        drivername.get(name);
        drivername.manage().window().maximize();

        // Creating the instance of this class
        validation validate_framework=new validation();

        //Getting Xpath from JsonFile
        xpath_values= (ArrayList<String>) readJsonFile.GetXpath();

        //Getting key from JsonFile
        key_data=(ArrayList<String>) readJsonFile.GetKey();

        //Iterating and finding the match of the translated elements
        for(int t=0;t<xpath_values.size();t++)
        {

            String route_Value=xpath_values.get(t);
            String route_key=key_data.get(t);
            String elementAttribute=validate_framework.RetrievingAttribute(drivername,route_Value);
            String property_value=validate_framework.ReadProperty(route_key);
            System.out.println("Translated value"+elementAttribute+"  "+"Property value"+" " +property_value);
            if(elementAttribute.equals(property_value)) {
                String sql = "INSERT INTO attributedetails(xpathvalue,status) VALUES(?,?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, route_Value);
                statement.setString(2, "PASS");
                statement.executeUpdate();
                System.out.println("The values are Translated perfectely");
            }
            else {
                String sqlquery = "INSERT INTO attributedetails(xpathvalue,status) VALUES(?,?)";
                PreparedStatement stmt = con.prepareStatement(sqlquery);
                stmt.setString(1, route_key);
                stmt.setString(2, "FAIL");
                stmt.executeUpdate();
                System.out.println("Translation Failed");
            }

        }
        reportgeneration rgenerate=new reportgeneration();
        rgenerate.main();


    }




}
