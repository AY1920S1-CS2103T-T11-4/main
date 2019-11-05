package seedu.address.storage;

import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.Event;

/**
 * Jackson friendly version of {@code Event}.
 */
public class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    private final String name;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final String destination;
    //private final Optional<Booking> booking;
    private final Optional<JsonAdaptedExpenditure> expenditure;
    //private final Optional<Inventory> inventory;
    private final Optional<String> description;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given event details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("name") String name,
            @JsonProperty("startTime") LocalDateTime from,
            @JsonProperty("endTime") LocalDateTime to,
            @JsonProperty("destination") String destination,
            @JsonProperty("expenditure") Optional<JsonAdaptedExpenditure> expenditure,
            @JsonProperty("description") Optional<String> description
    //, @JsonProperty("booking")Optional<Booking> booking,
    // @JsonProperty("inventory")Optional<Inventory> inventory
    ) {
        this.name = name;
        this.startTime = from;
        this.endTime = to;
        this.destination = destination;
        this.expenditure = expenditure;
        this.description = description;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.name = source.getName().fullName;
        this.startTime = source.getStartDate();
        this.endTime = source.getEndDate();
        this.destination = source.getDestination().value;
        if (source.getExpenditure().isPresent()) {
            this.expenditure = Optional.of(new JsonAdaptedExpenditure(source.getExpenditure().get()));
        } else {
            this.expenditure = Optional.empty();
        }
        if (source.getDescription().isPresent()) {
            this.description = Optional.of(source.getDescription().get().description);
        } else {
            this.description = Optional.empty();
        }
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        if (startTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        // Assumes validation done upon creation

        final LocalDateTime modelStartTime = startTime;

        if (endTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName()));
        }

        // Assumes validation done upon creation

        final LocalDateTime modelEndTime = endTime;

        if (destination == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }

        if (!Location.isValidLocation(destination)) {
            throw new IllegalValueException(Location.MESSAGE_CONSTRAINTS);
        }

        final Location modelDestination = new Location(destination);

        //No check for TotalBudget (defaults endTime 0)
        final Optional<Expenditure> modelExpenditure;

        if (expenditure.isPresent()) {

            modelExpenditure = Optional.of(expenditure.get().toModelType());
        } else {
            modelExpenditure = Optional.empty();
        }

        final Optional<Description> modelDescription;

        if (description.isPresent()) {
            if (!Description.isValidDescription(description.get())) {
                throw new IllegalValueException(
                        String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
            }
            modelDescription = Optional.of(new Description(description.get()));
        } else {
            modelDescription = Optional.empty();
        }



        return new Event(modelName, modelStartTime, modelEndTime, modelExpenditure, modelDestination, modelDescription);
    }
}


