package com.diligenta.testSuitBase;

import com.diligenta.utility.Read_XLS;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
//import java.util.logging.Logger;

public class SuitBase
{

    public static Logger Add_Log=null;
    public static Properties Param=null;
    public static WebDriver driver=null;
    public static Read_XLS TestSuiteListExcel=null;

    public void init() throws IOException {


        //To Initiate Logger Services
        System.setProperty("log4j.comnfigurationFile", System.getProperty("user.dir")+"/src/main/java/com/diligenta/logreporting/log4j2.xml");
        Add_Log= LogManager.getLogger();

        //Initialising Param.Properties
        Param=new Properties();
        FileInputStream fin=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\diligenta\\properties\\Param.properties");
//      FileInputStream fin=new FileInputStream("C:\\Users\\Hp\\Intelije\\SeleniumFrameworkDesigne\\src\\main\\java\\com\\diligenta\\properties\\Param.properties");
        Param.load(fin);
//        System.out.println("File loaded Successfully");
        Add_Log.info("File Loaded");

        //Initialising SuiteList
        TestSuiteListExcel=new Read_XLS(System.getProperty("user.dir")+"\\src\\main\\java\\com\\diligenta\\excelfiles\\TestSuiteList.xlsx");
        System.out.println("Printing Excel file "+TestSuiteListExcel);
    }

    public void loadBrowser()
    {
        //Selecting Driver for the Execution
        String browserName=Param.getProperty("browser");
        System.out.println("browser name "+browserName);
        if(browserName.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"browserDrivers");
              WebDriverManager.chromedriver().setup();
              driver=new ChromeDriver();
            Add_Log.info("Chrome Browser Selected To Launch");
        }else if(browserName.equalsIgnoreCase("Edge")) {
            System.setProperty("webdriver.edge.driver","C:\\Users\\Hp\\Intelije\\SeleniumFrameworkDesigne\\browserDrivers\\msedgedriver.exe");
              driver=new EdgeDriver();
            Add_Log.info("Edge Browser Selected To Launch");
        }
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

}
