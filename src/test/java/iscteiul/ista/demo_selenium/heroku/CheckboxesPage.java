package iscteiul.ista.demo_selenium.heroku;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckboxesPage {
    private WebDriver driver;

    // O site tem um form com dois inputs do tipo checkbox
    @FindBy(css = "form#checkboxes input[type='checkbox']")
    public List<WebElement> allCheckboxes;

    public CheckboxesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/checkboxes");
    }

    // Método auxiliar para clicar numa checkbox específica (1 ou 2)
    public void toggleCheckbox(int index) {
        if (index >= 0 && index < allCheckboxes.size()) {
            allCheckboxes.get(index).click();
        }
    }

    public boolean isCheckboxSelected(int index) {
        return allCheckboxes.get(index).isSelected();
    }
}