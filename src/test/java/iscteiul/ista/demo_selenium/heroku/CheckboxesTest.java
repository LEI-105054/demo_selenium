package iscteiul.ista.demo_selenium.heroku;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class CheckboxesTest {
    private WebDriver driver;
    private CheckboxesPage checkboxPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        checkboxPage = new CheckboxesPage(driver);
        checkboxPage.open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testCheckboxInteraction() {
        // A Checkbox 1 começa desmarcada, a 2 começa marcada por defeito no site.

        // 1. Verificar estado inicial
        assertFalse(checkboxPage.isCheckboxSelected(0), "Checkbox 1 devia estar desmarcada");
        assertTrue(checkboxPage.isCheckboxSelected(1), "Checkbox 2 devia estar marcada");

        // 2. Clicar na primeira (marcar)
        checkboxPage.toggleCheckbox(0);
        assertTrue(checkboxPage.isCheckboxSelected(0), "Checkbox 1 devia passar a marcada");

        // 3. Clicar na segunda (desmarcar)
        checkboxPage.toggleCheckbox(1);
        assertFalse(checkboxPage.isCheckboxSelected(1), "Checkbox 2 devia passar a desmarcada");
    }
}