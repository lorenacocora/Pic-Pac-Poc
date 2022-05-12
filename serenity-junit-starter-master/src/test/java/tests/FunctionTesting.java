package tests;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import steps.EndUserSteps;

@ExtendWith(SerenityJUnit5Extension.class)
public class FunctionTesting {
    /**
     * Define the webdriver instance to be used for these tests
     */
    @Managed(driver = "chrome", options = "headless")
    WebDriver driver;
    @Steps
    public EndUserSteps anna;

    @Test
    public void contact_with_valid_info() {
        anna.goes_to_contact_page();
        anna.enters_full_name("anna");
        // de introdus tot si de facut in csv.. work in progress
    }
}
