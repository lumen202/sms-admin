package finalproject.admin.app.management.viewstudent;

import dev.sol.core.application.loader.FXLoader;
import finalproject.admin.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewStudentLoader extends FXLoader {

    private Parent root;
    private FXMLLoader loader;

    @Override
    public void load() {
        try {
            loader = new FXMLLoader(App.class.getResource("/finalproject/admin/app/management/viewstudent/VIEWSTUDENT.fxml"));
            root = loader.load();
            Stage ownerStage = (Stage) params.get("OWNER");
            Stage stage = new Stage();
            stage.setTitle("View Student");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initOwner(ownerStage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            ViewStudentController controller = loader.getController();
            if (controller != null) {
                App.CONTROLLER_REGISTRY.register("VIEWSTUDENT", controller);
                controller.addParameter("SCENE", stage.getScene())
                        .addParameter("OWNER", params.get("OWNER"))
                        .load();
            } else {
                System.err.println("Controller is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
