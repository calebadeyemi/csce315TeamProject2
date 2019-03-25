package application;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 * The root scene is the main frame of the GUI. It handles the overall layout of the program. This is where more
 * "global" changes should take place and should be the highest point of abstraction to transfer logic.
 */
class RootSceneController {
    /**
     * Generates the root scene for display.
     * @return A fully generated root layout.
     */
    Scene generateRootScene(Pane root) {
        GameController controller = new GameController();
        updateScene(null, controller, root);

        return new Scene(root);
    }

    private void updateScene(Pane oldView, GameController controller, Pane root) {
        Pane newView = controller.drawCurrentState();
        newView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateScene(newView, controller, root));

        root.getChildren().remove(oldView);
        root.getChildren().add(newView);
    }
}
