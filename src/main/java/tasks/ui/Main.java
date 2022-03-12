package tasks.ui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import tasks.ui.controller.Controller;
import tasks.ui.controller.Notificator;
import tasks.model.ArrayTaskList;
import tasks.services.TaskIO;
import tasks.services.TasksService;
import tasks.ui.helpers.UiHelper;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;
    private static final int defaultWidth = 820;
    private static final int defaultHeight = 520;

    private static final Logger log = Logger.getLogger(Main.class.getName());

    private ArrayTaskList savedTasksList = new ArrayTaskList();

    private static ClassLoader classLoader = Main.class.getClassLoader();
    public static File savedTasksFile;

    private TasksService service = new TasksService(savedTasksList);//savedTasksList);

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("saved data reading");
        if (savedTasksFile.length() != 0) {
            TaskIO.readBinary(savedTasksList, savedTasksFile);
        }
        try {
            log.info("application start");
            savedTasksFile = new File(classLoader.getResource("data/tasks.txt").getFile());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
            Parent root = loader.load();//loader.load(this.getClass().getResource("/fxml/main.fxml"));
            Controller ctrl= loader.getController();
            service = new TasksService(savedTasksList);

            ctrl.setService(service);
            primaryStage.setTitle("Task Manager");
            primaryStage.setScene(new Scene(root, defaultWidth, defaultHeight));
            primaryStage.setMinWidth(defaultWidth);
            primaryStage.setMinHeight(defaultHeight);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
            log.error("error reading main.fxml");
            UiHelper.showError("Error starting application");
        }
        catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
            UiHelper.showError("Error starting application");
        }
        primaryStage.setOnCloseRequest(we -> {
                System.exit(0);
            });
        new Notificator(FXCollections.observableArrayList(service.getObservableList())).start();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
