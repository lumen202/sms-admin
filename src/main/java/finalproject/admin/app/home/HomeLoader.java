package finalproject.admin.app.home;

import dev.sol.core.application.loader.FXLoader;
import finalproject.admin.util.ControllerLoader;

public class HomeLoader extends FXLoader implements ControllerLoader<HomeController> {

    @Override
    public void load() {
        HomeController controller = (HomeController) loader.getController();
        controller.load();
    }

    @Override
    public HomeController getController() {
        return (HomeController) loader.getController();
    }
}
