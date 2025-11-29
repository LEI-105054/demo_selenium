package forms;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selectors.withText; // IMPORTANTE

public class FormPage {
    private static final String URL = "https://vaadin-form-example.demo.vaadin.com/";

    // --- SELETORES ---
    @FindBy(css = "vaadin-form-layout > vaadin-text-field:nth-of-type(1)")
    private SelenideElement firstNameInput;

    @FindBy(css = "vaadin-form-layout > vaadin-text-field:nth-of-type(2)")
    private SelenideElement lastNameInput;

    @FindBy(css = "vaadin-form-layout > vaadin-text-field:nth-of-type(3)")
    private SelenideElement userHandleInput;

    @FindBy(css = "vaadin-form-layout > vaadin-password-field:nth-of-type(1)")
    private SelenideElement passwordInput;

    @FindBy(css = "vaadin-form-layout > vaadin-password-field:nth-of-type(2)")
    private SelenideElement confirmPasswordInput;

    @FindBy(css = "vaadin-form-layout > vaadin-email-field")
    private SelenideElement emailInput;

    @FindBy(css = "vaadin-checkbox")
    private SelenideElement agreementCheckbox;

    @FindBy(css = "vaadin-button[colspan='2']")
    private SelenideElement submitButton;

    // Removemos o seletor 'successNotification' daqui porque vamos buscá-lo dinamicamente

    public FormPage() {
        page(this);
    }

    public void openPage() {
        open(URL);
    }

    public void fillForm(String fName, String lName, String handle, String pass, String email) {
        // 1. Checkbox
        executeJavaScript("arguments[0].click();", agreementCheckbox);

        // 2. Dados
        setVaadinValue(firstNameInput, fName);
        setVaadinValue(lastNameInput, lName);
        setVaadinValue(userHandleInput, handle);
        setVaadinValue(passwordInput, pass);
        setVaadinValue(confirmPasswordInput, pass);

        // 3. Email
        fillEmailWithKeys(email);

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        // 4. Submeter
        if (submitButton.isEnabled()) {
            submitButton.click();
        }
    }

    public String getOutputText() {
        // CORREÇÃO DEFINITIVA:
        // Procuramos um elemento que contenha o texto parcial "Data saved".
        // O Selenide é inteligente o suficiente para encontrar isto mesmo dentro de estruturas complexas.

        SelenideElement notification = $(withText("Data saved"));

        try {
            // Espera até 8s para o texto aparecer
            notification.shouldBe(Condition.visible);
            return notification.getText();
        } catch (Error e) {
            System.out.println("Aviso: Mensagem 'Data saved' não encontrada. A devolver texto da página.");
            return $("body").getText();
        }
    }

    // --- MÉTODOS AUXILIARES ---
    private void setVaadinValue(SelenideElement element, String value) {
        String js =
                "var target = arguments[0];" +
                        "if (target.shadowRoot) {" +
                        "   var internalInput = target.shadowRoot.querySelector('input');" +
                        "   if (internalInput) target = internalInput;" +
                        "}" +
                        "target.focus();" +
                        "target.value = arguments[1];" +
                        "target.dispatchEvent(new Event('input', { bubbles: true, composed: true }));" +
                        "target.dispatchEvent(new Event('change', { bubbles: true, composed: true }));" +
                        "target.blur();";
        executeJavaScript(js, element, value);
    }

    private void fillEmailWithKeys(String emailText) {
        emailInput.scrollIntoView(true);
        emailInput.click();
        emailInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        emailInput.sendKeys(emailText);
        emailInput.sendKeys(Keys.TAB);
    }
}