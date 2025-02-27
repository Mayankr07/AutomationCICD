package com.diligenta.pages;


import com.diligenta.testSuitBase.SuitBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class LoginPage  extends SuitBase
{

    public LoginPage(WebDriver driver)
    {
//          this.driver=driver;
          PageFactory.initElements(driver,this);
    }

    @FindBy(id="userEmail")
    private WebElement txt_uerEmail;
    @FindBy(id="userPassword")
    private WebElement txt_userPassword;
    @FindBy(id="login")
    private WebElement btn_login;

    public void login()
    {
        driver.get("https://rahulshettyacademy.com/client");
    }

    public void loginApplication(String email,String password)
    {
        txt_uerEmail.sendKeys("anshika@gmail.com");
        txt_userPassword.sendKeys("Iamking@000");
        btn_login.click();
        Add_Log.info("Done with Git Changes");
        Add_Log.info("Done with Git Changes");
        Add_Log.info("Done with Git Changes");
        Add_Log.info("Done with Git Changes");
        Add_Log.info("Done with Git Changes Sucess ");
        Add_Log.info("Done with Git Changes Sucess ");
        Add_Log.info("Done with Git Changes Sucess ");

    }



}
