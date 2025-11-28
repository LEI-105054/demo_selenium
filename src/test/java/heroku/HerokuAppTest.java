package heroku;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class HerokuAppTest {
    private WebDriver driver;

    // Page Objects
    private HerokuLandingPage landingPage;
    private DropdownPage dropdownPage;
    private InputsPage inputsPage;

    @BeforeEach
    public void setUp() {
        // Configurações para o Chrome (incluindo fixes para versão Canary)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        landingPage = new HerokuLandingPage(driver);
        landingPage.open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("Interação Básica 1: Dropdown List")
    public void testDropdown() {
        // 1. Navegar
        landingPage.clickDropdown();
        dropdownPage = new DropdownPage(driver);

        // 2. Validar estado inicial ("Please select an option")
        String initialSelection = dropdownPage.getSelectedOption();
        assertTrue(initialSelection.contains("Please select an option"),
                "A opção por defeito devia ser 'Please select an option'");

        // 3. Selecionar "Option 1" e validar
        dropdownPage.selectOption("Option 1");
        assertEquals("Option 1", dropdownPage.getSelectedOption());

        // 4. Selecionar "Option 2" e validar
        dropdownPage.selectOption("Option 2");
        assertEquals("Option 2", dropdownPage.getSelectedOption());
    }

    @Test
    @DisplayName("Interação Básica 2: Inputs Numéricos")
    public void testInputs() {
        // 1. Navegar
        landingPage.clickInputs();
        inputsPage = new InputsPage(driver);

        // 2. Escrever um número positivo
        inputsPage.typeNumber("123");
        assertEquals("123", inputsPage.getInputValue());

        // 3. Escrever um número negativo
        inputsPage.typeNumber("-50");
        assertEquals("-50", inputsPage.getInputValue());

        // 4. Testar interação com teclas (Seta para cima)
        // Se estava em -50 e carregamos para cima, deve ir para -49
        inputsPage.pressArrowUp();
        assertEquals("-49", inputsPage.getInputValue());
    }
}