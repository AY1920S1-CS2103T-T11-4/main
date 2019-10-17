package seedu.address.ui.components;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;

import seedu.address.logic.commands.inventory.EnterInventoryCommand;
import seedu.address.logic.commands.sidebar.EnterDayPageCommand;
import seedu.address.logic.commands.sidebar.EnterTripManagerCommand;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;

/**
 * Abstraction of a vertical sidebar displayed on the left side.
 */
public class NavigationSidebarLeft extends UiPart<Region> {

    private static final String FXML = "/components/SidebarLeft.fxml";
    private MainWindow mainWindow;

    public NavigationSidebarLeft(MainWindow mainWindow) {
        super(FXML);
        this.mainWindow = mainWindow;
    }

    @FXML
    void handleEnterTripManager() {
        mainWindow.executeGuiCommand(EnterTripManagerCommand.COMMAND_WORD);
    }

    @FXML
    void handleEnterOverallView() {
        mainWindow.executeGuiCommand(EnterDayPageCommand.COMMAND_WORD);
    }

    @FXML
    private void handleEnterBookingsManager() {

    }

    @FXML
    private void handleEnterInventoryManager() {
        mainWindow.executeGuiCommand(EnterInventoryCommand.COMMAND_WORD);
    }

}
