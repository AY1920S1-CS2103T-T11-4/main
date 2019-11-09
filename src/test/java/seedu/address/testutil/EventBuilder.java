package seedu.address.testutil;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;

import seedu.address.model.booking.Booking;
import seedu.address.model.expense.Expense;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.Event;

/**
 * Builder class to accommodate optional properties using builder pattern.
 * Can be used to construct {@link Event} without optional fields.
 */
public class EventBuilder {
    private Name name;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Booking booking;
    private Location destination;
    private Expense totalBudget;
    private Inventory inventory;

    /**
     * Constructs an empty {@code EventBuilder}.
     */
    private EventBuilder() {
    }

    public EventBuilder setName(Name name) {
        this.name = name;
        return this;
    }

    public static EventBuilder newInstance() {
        return new EventBuilder();
    }

    /**
     * Constructs an {@code EventBuilder} from the specified event.
     *
     * @param event {@code Event} to use.
     * @return new EventBuilder instance.
     */
    public static EventBuilder of(Event event) {
        requireAllNonNull(event.getName(), event.getStartDate(), event.getEndDate(), event.getDestination());
        EventBuilder e = EventBuilder.newInstance()
                .setName(event.getName())
                .setStartDate(event.getStartDate())
                .setEndDate(event.getEndDate())
                .setLocation(event.getDestination());
        event.getExpense().ifPresent(e::setTotalBudget);
        event.getInventory().ifPresent(e::setInventory);
        event.getBooking().ifPresent(e::setBooking);
        return e;
    }

    public EventBuilder setStartDate(LocalDateTime startTime) {
        this.startDate = startTime;
        return this;
    }

    public EventBuilder setEndDate(LocalDateTime endTime) {
        this.endDate = endTime;
        return this;
    }

    public EventBuilder setBooking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public EventBuilder setLocation(Location location) {
        this.destination = location;
        return this;
    }

    public EventBuilder setTotalBudget(Expense totalBudget) {
        this.totalBudget = totalBudget;
        return this;
    }

    public EventBuilder setInventory(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public Event build() {
        return new Event(name, startDate, endDate, booking, totalBudget, inventory, destination);
    }

}
