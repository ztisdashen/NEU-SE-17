
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("Main123.fxml"));
            root.getStylesheets().add(
                    getClass().getResource("BackGround.css")
                            .toExternalForm());
            primaryStage.setTitle("医疗保险报销系统_1701_20175276_贾敬哲");

            primaryStage.setScene(new Scene(root));

            primaryStage.setResizable(false);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}