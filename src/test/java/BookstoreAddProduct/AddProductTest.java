package BookstoreAddProduct;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class AddProductTest {

    private final Random random = new Random();

    @BeforeEach
    public void setup() {
        open("https://vaadin-bookstore-example.demo.vaadin.com/");
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @Test
    public void testAddProduct() {
        String productName = "Test Product " + random.nextInt(1000);

        $("vaadin-login-form-wrapper").shouldBe(visible);
        $("#vaadinLoginUsername input").shouldBe(visible, enabled).setValue("admin");
        $("#vaadinLoginPassword input").shouldBe(visible, enabled).setValue("admin");
        $(byText("Log in")).shouldBe(visible, enabled).click();

        $(byText("New product")).shouldBe(visible, enabled).click();

        $(".product-form").should(appear);

        $(shadowCss("input", ".product-form vaadin-text-field"))
                .shouldBe(visible).setValue(productName);

        $(shadowCss("input", ".product-form vaadin-horizontal-layout vaadin-text-field:nth-of-type(1)"))
                .shouldBe(visible).setValue(String.valueOf(10 + random.nextInt(90)));

        $(shadowCss("input", ".product-form vaadin-horizontal-layout vaadin-text-field:nth-of-type(2)"))
                .shouldBe(visible).setValue(String.valueOf(1 + random.nextInt(50)));
        
        $("vaadin-select[aria-required='true']").shouldBe(visible).click();
        $$("vaadin-select-overlay vaadin-item").shouldHave(sizeGreaterThan(0))
                .get(random.nextInt(3)).click();

        $$("#category vaadin-checkbox").shouldHave(sizeGreaterThan(0))
                .get(random.nextInt(3)).click();
        
        $(".product-form vaadin-button[theme='primary']")
                .shouldHave(text("Save"))
                .click();
        
        sleep(3000);
        
        $(shadowCss("input[placeholder*='Filter']", "vaadin-text-field"))
                .shouldBe(visible)
                .setValue(productName)
                .pressEnter();
        
        $$("vaadin-grid-cell-content")
                .findBy(text(productName))
                .shouldBe(visible)
                .click();
    }
}