package forms;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormTest {
    private FormPage formPage;

    @BeforeAll
    static void setUpAll() {
        Configuration.browser = "chrome";
        Configuration.headless = false; // Mete true se não quiseres ver o browser
        Configuration.timeout = 8000;   // Dá tempo ao site para responder
    }

    @BeforeEach
    void setUp() {
        formPage = new FormPage();
        formPage.openPage();
    }

    @Test
    void testFullFormSubmission() {
        String firstName = "Tiago";
        String lastName = "Silva";
        String userHandle = "tiagosilva";
        String password = "PasswordForte123";
        String email = "tiago@iscte.pt";

        formPage.fillForm(firstName, lastName, userHandle, password, email);

        String result = formPage.getOutputText();
        System.out.println("RESULTADO: " + result);

        // Ajusta esta validação conforme o texto exato que vês no ecrã a verde
        // Se a mensagem for "Data saved", verifica "Data saved"
        assertTrue(result.contains("Data saved"), "Deve conter a mensagem de sucesso");

        // Se a mensagem também tiver o handle:
        assertTrue(result.contains(userHandle), "Deve conter o user handle");
    }
}