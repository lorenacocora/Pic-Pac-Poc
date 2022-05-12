package steps;

import net.thucydides.core.annotations.Step;
import pages.ContactPage;

public class EndUserSteps {
    ContactPage contactPage;
    @Step
    public void goes_to_contact_page(){
        contactPage.open();
    }
    @Step
    public void enters_full_name(String name){
        contactPage.enter_full_name(name);
    }

}
