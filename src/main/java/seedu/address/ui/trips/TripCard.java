package seedu.address.ui.trips;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.model.trip.Trip;
import seedu.address.ui.UiPart;

/**
 * A component for displaying the details of a singular {@code Trip}.
 */
public class TripCard extends UiPart<GridPane> {
    private static final String FXML = "trips/TripCard.fxml";

    @FXML
    private Label tripDisplayIndexLabel;
    @FXML
    private Label tripNameLabel;
    @FXML
    private Label tripBudgetLabel;
    @FXML
    private Label tripDestinationLabel;
    @FXML
    private Label tripStartDateLabel;
    @FXML
    private Label tripEndDateLabel;
    @FXML
    private ImageView tripImageView;

    private Trip trip;
    private Index displayedIndex;

    public TripCard(Trip trip, Index displayedIndex) {
        super(FXML);
        this.trip = trip;
        this.displayedIndex = displayedIndex;
        fillTripCardLabels();
    }

    /**
     * Fills the labels of this trip card with the info in {@code trip}.
     */
    private void fillTripCardLabels() {
        tripDisplayIndexLabel.setText(displayedIndex.getOneBased() + "");
        tripNameLabel.setText(trip.getName().toString());
        tripBudgetLabel.setText("$" + trip.getBudget().toString());
        tripDestinationLabel.setText(trip.getDestination().toString());
        tripStartDateLabel.setText(ParserDateUtil.getDisplayTime(trip.getStartDate()));
        tripEndDateLabel.setText(ParserDateUtil.getDisplayTime(trip.getEndDate()));
        trip.getPhoto().ifPresent(photo -> {
            Image image = photo.getImage();
            tripImageView.setImage(photo.getImage());
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TripCard)) {
            return false;
        }

        // state check
        TripCard otherCard = (TripCard) other;
        return trip.equals(otherCard.trip)
                && this.displayedIndex.equals(otherCard.displayedIndex);
    }


}
