package BookstoreAddProduct;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestAddProduct {

    private final Random random = new Random();

    @BeforeEach
    public void setup() {
        // Open the bookstore
        open("https://vaadin-bookstore-example.demo.vaadin.com/");
        // Maximize window for better visibility of elements
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @Test
    public void testAddProduct() {
        Random random = new Random();

        // Step 1: Log in
        $("#vaadinLoginUsername").shouldBe(visible).setValue("admin");
        $("#vaadinLoginPassword").shouldBe(visible).setValue("admin");
        $(Selectors.byText("Log in")).shouldBe(enabled).click();

        // Step 2: Click "New product"
        $(Selectors.byText("New product")).shouldBe(visible).click();

        // Step 3: Fill product details
        ElementsCollection nameFields = $$("vaadin-text-field[placeholder='Name']");
        ElementsCollection priceFields = $$("vaadin-text-field[placeholder='Price']");
        SelenideElement stockField = $("vaadin-text-field[placeholder='Stock quantity']");

        String productName = "Test Product " + random.nextInt(1000);

        nameFields.get(0).shouldBe(visible).setValue(productName);
        priceFields.get(0).shouldBe(visible).setValue(String.valueOf(10 + random.nextInt(90)));
        stockField.shouldBe(visible).setValue(String.valueOf(1 + random.nextInt(50)));

        // Step 4: Select random value from vaadin-select
        // Use /deep/ or ::shadow CSS selector
        SelenideElement select = $("vaadin-select[aria-required='true']");
        select.shouldBe(visible).click();

        ElementsCollection options = $$("vaadin-select::shadow vaadin-list-box vaadin-item");
        options.get(random.nextInt(options.size())).click();

        // Step 5: Select random checkbox in category group
        ElementsCollection checkboxes = $$("vaadin-checkbox-group#category::shadow vaadin-checkbox");
        checkboxes.get(random.nextInt(checkboxes.size())).click();

        // Step 6: Submit product
        SelenideElement submitButton = $("vaadin-button[theme='primary']");
        submitButton.shouldBe(enabled).click();

        // Step 7: Verification
        $$("vaadin-grid-cell-content").findBy(text(productName)).should(appear);
    }


}
