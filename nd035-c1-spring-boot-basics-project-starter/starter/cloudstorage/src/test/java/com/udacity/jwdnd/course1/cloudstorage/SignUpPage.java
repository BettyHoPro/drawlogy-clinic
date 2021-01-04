package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitbutton;

    @FindBy(id = "back-to-login-page")
    private WebElement backtologinpage;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    public void setUpNewUser(User user)
    {
        inputFirstName.sendKeys(user.getFirstName());
        inputLastName.sendKeys(user.getLastName());
        inputUsername.sendKeys(user.getUsername());
        inputPassword.sendKeys(user.getPassword());
        submitbutton.click();
    }
}
