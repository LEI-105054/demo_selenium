package iscteiul.ista.demo_selenium.heroku;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        loginPage = new LoginPage(driver);
        loginPage.open();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testValidLogin() {
        // Credenciais oficiais do site The Internet Heroku App
        loginPage.login("tomsmith", "SuperSecretPassword!");

        String message = loginPage.getAlertText();
        assertTrue(message.contains("You logged into a secure area"), "Login devia ter sucesso");
    }

    @Test
    public void testInvalidLogin() {
        loginPage.login("aluno", "passwordErrada");

        String message = loginPage.getAlertText();
        assertTrue(message.contains("Your username is invalid"), "Devia mostrar erro de login");
    }
}