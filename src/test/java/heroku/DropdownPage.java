package heroku;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DropdownPage {
    private WebDriver driver;

    // O elemento <select> tem o id 'dropdown'
    private By dropdownList = By.id("dropdown");

    public DropdownPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectOption(String visibleText) {
        WebElement element = driver.findElement(dropdownList);
        Select select = new Select(element);
        select.selectByVisibleText(visibleText);
    }

    public String getSelectedOption() {
        WebElement element = driver.findElement(dropdownList);
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }
}