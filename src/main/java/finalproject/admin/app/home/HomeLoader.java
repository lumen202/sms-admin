package finalproject.admin.app.home;

import dev.sol.core.application.loader.FXLoader;
import finalproject.admin.App;
import javafx.scene.Scene;

public class HomeLoader extends FXLoader {

    @Override
    public void load() {
        Scene scene = (Scene) params.get("scene");
        scene.setRoot(root);
        HomeController controller = loader.getController();
        App.CONTROLLER_REGISTRY.register("HOME", controller);
        controller.addParameter("SCENE", scene)
                  .addParameter("OWNER", params.get("OWNER"))
                  .load();
    }
}
