package tasks.ui.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class UiHelper {
    /**
     * shows an error
     * @param error - the error shown
     */
    public static void showError(String error){
        System.out.println(error);
        Alert alert = new Alert(Alert.AlertType.ERROR,error, ButtonType.OK);
        alert.setHeaderText(null);
        alert.show();
    }
}
