package com.deathClaim;
import com.diligenta.pages.LoginPage;
import com.diligenta.utility.SuitUtility;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;


public class TSL_DeathClaim extends deathClaimBase
{

     static int DataSet =-1;
    String TestCaseName = null;

    String[] TestDataToRun=null;
    String ToRunColumnNameTestCase = null;
    String ToRunColumnNameTestData = null;


    @BeforeTest
    public void checkCaseToRun() throws IOException {
       //Called init() function  from Suite Base class ti initialise .xls File
        init();

        FilePath= TestCaseListExcelOne;
        TestCaseName=this.getClass().getSimpleName();
        System.out.println("Test Case Name"+TestCaseName);
        //Sheet Name to Check CaseToRun Flag  against test case
        SheetName="TestCasesList";
        //Name of column In TestCaseSList Excel Data sheets
        ToRunColumnNameTestCase="CaseToRun";
        //Name of colum in Test Case Data Sheet
        ToRunColumnNameTestData="DataToRun";

        if(!SuitUtility.checkToRunUtility(FilePath,SheetName,ToRunColumnNameTestCase,TestCaseName))
        {
            Add_Log.info(TestCaseName +" : CaseToRun = N for So Skipping Execution.");
            SuitUtility.writeResultUtility(FilePath,SheetName,"Pass/Fail/Skip",TestCaseName,"SKIP");
            throw  new SkipException(TestCaseName+" Case to run flag is N or Blank. So Skipping the execution");
        }
        else {
            System.out.println("executing Test Excel");
            TestDataToRun=  SuitUtility.checkToRunUtilityOfData(FilePath,SheetName,ToRunColumnName);
        }
    }


    @Test
    public void DeathClaim()
    {
        System.out.println("Main Death Claim Code");
        loadBrowser();
        Add_Log.info("Browser launched");
        LoginPage obj_loginPage=new LoginPage(driver);
        obj_loginPage.login();

    }
}
