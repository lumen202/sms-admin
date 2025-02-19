package finalproject.admin.app.viewstudent.studentform;

import dev.sol.core.application.loader.FXLoader;
import finalproject.admin.App;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StudentFormLoader extends FXLoader {

    @Override
    public void load() {
        Stage ownerStage = (Stage) params.get("OWNER_STAGE");
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Edit Profile");
        stage.setScene(scene);
        scene.getStylesheets().add(
                getClass().getResource("/finalproject/admin/assets/styles/skins/primer_light.css").toExternalForm());
        stage.setResizable(false);
        stage.initOwner(ownerStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        StudentFormController controller = loader.getController();
        App.CONTROLLER_REGISTRY.register("STUDENT_FORM", controller);
        controller.load();

    }
}