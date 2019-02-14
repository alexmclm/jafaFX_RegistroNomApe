
package registrousuaios;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RegistroUsuaios extends Application {
    
    @Override
    public void start (Stage stage) throws Exception{
         Parent root = FXMLLoader.load(getClass().getResource("/FXML/fxmlUsuarios.fxml"));
         Scene scene = new Scene(root);
         stage.setTitle("Registro");
         stage.setScene(scene);
         stage.show();
    }
    public static void main(String[] args)  {
        launch(args);
    }
    
}
