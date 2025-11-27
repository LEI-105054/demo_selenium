package iscteiul.ista.demo_selenium.heroku;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement messageAlert;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/login");
    }

    public void login(String user, String pass) {
        usernameInput.clear();
        usernameInput.sendKeys(user);
        passwordInput.clear();
        passwordInput.sendKeys(pass);
        loginButton.click();
    }

    public String getAlertText() {
        return messageAlert.getText();
    }
}