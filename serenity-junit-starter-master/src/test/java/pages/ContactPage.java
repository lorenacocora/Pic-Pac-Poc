package pages;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;
@DefaultUrl("https://abstracta.us/contact-us")
public class ContactPage extends PageObject {
    String link = "https://abstracta.us/contact-us";
    @FindBy(id="fullname")
    private WebElementFacade fullNameField;

    public void enter_full_name(String name){
        fullNameField.type(name);
    }

}
