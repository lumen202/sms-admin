package finalproject.admin.app.management;

import dev.sol.core.application.loader.FXLoader;
import finalproject.admin.App;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagementLoader extends FXLoader {

    @Override
    public void load() {
        Stage ownerStage = (Stage) params.get("OWNER_STAGE");
        Stage stage = new Stage();
        stage.setTitle("Management");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initOwner(ownerStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

        ManagementController controller = loader.getController();
        App.CONTROLLER_REGISTRY.register("MANAGEMENT", controller);
        controller.load();

    }
}
