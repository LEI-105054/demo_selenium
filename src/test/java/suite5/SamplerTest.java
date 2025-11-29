package suite5;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SamplerTest {

    @BeforeAll
    public static void setUp() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        // Importante: Manter a janela grande para o menu lateral aparecer sempre
        Configuration.browserSize = "1920x1080";
    }

    @Test
    public void testSuite5_AccessInteraction() {
        SamplerPage page = new SamplerPage();

        page.openPage();

        // Esta ação agora clica diretamente no botão "Button"
        page.enterInteractionCategory();

        // Valida se a página do botão abriu
        page.validateInteractionContents();
    }
}