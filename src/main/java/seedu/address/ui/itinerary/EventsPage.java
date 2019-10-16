package seedu.address.ui.itinerary;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.EnterPrefsCommand;
import seedu.address.logic.commands.itinerary.events.EnterCreateEventCommand;
import seedu.address.model.Model;
import seedu.address.model.itinerary.event.Event;
import seedu.address.ui.MainWindow;
import seedu.address.ui.components.NavigationSidebarLeft;
import seedu.address.ui.components.NavigationSidebarRight;
import seedu.address.ui.template.PageWithSidebar;

/**
 * {@code Page} for displaying the event details.
 */
public class EventsPage extends PageWithSidebar<AnchorPane> {

    private static final String FXML = "itinerary/events/EventsPage.fxml";

    @FXML
    private VBox eventCardContainer;

    private Label inventoryLabel;

    @FXML
    private Label totalBudgetLabel;

    @FXML
    private Label bookingLabel;

    @FXML
    private VBox sideBarLeft;

    @FXML
    private VBox sideBarRight;


    public EventsPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillPage() {
        // nav bar
        sideBarRight.getChildren().clear();
        sideBarLeft.getChildren().clear();
        NavigationSidebarRight navigationSidebarRight = new NavigationSidebarRight(mainWindow);
        NavigationSidebarLeft navigationSidebarLeft = new NavigationSidebarLeft(mainWindow);
        sideBarLeft.getChildren().add(navigationSidebarLeft.getRoot());
        sideBarRight.getChildren().add(navigationSidebarRight.getRoot());

        // Filling events
        eventCardContainer.getChildren().clear();
        List<Event> events = model.getPageStatus().getDay().getEventList().internalUnmodifiableList;

        List<Node> eventCards = IntStream.range(0, events.size())
                .mapToObj(i -> Index.fromZeroBased(i))
                .map(index -> {
                    EventCard eventCard = new EventCard(events.get(index.getZeroBased()), index);
                    eventCard.getRoot().addEventFilter(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<javafx.scene.input.MouseEvent>() {
                                @Override
                                public void handle(javafx.scene.input.MouseEvent event) {
                                    if (events.get(index.getZeroBased()).getExpenditure().isPresent()) {
                                        totalBudgetLabel.setText("Total Budget: "
                                                + events.get(index.getZeroBased()).getExpenditure().get().getBudget().toString());
                                    } else {
                                        totalBudgetLabel.setText("NO BUDGET SET");
                                    }
                                }
                            });
                    return eventCard.getRoot();
                }).collect(Collectors.toList());
        eventCardContainer.getChildren().addAll(FXCollections.observableArrayList(eventCards));
    }

    @FXML
    private void handleAddEvent() {
        mainWindow.executeGuiCommand(EnterCreateEventCommand.COMMAND_WORD);
    }

    @FXML
    private void handlePreferences() {
        mainWindow.executeGuiCommand(EnterPrefsCommand.COMMAND_WORD);
    }

}
