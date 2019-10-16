package seedu.address.model.itinerary.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.model.booking.Booking;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.expenditure.Expenditure;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;

/**
 * Represents a Event in TravelPal.
 * Compulsory fields: name, startDate, endDate, destination.
 * Optional fields: expenditure, booking, inventory.
 */
public class Event {
    // Compulsory fields
    private final Name name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Location destination;

    // Optional fields
    private final Inventory inventory;
    private final Expenditure expenditure;
    private final Booking booking;

    /**
     * Constructs an {@code Event}.
     */
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate, Booking booking,
                 Expenditure expenditure, Inventory inventory, Location destination) {
        requireAllNonNull(name, startDate, endDate, booking, expenditure, inventory);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = booking;
        this.destination = destination;
        this.expenditure = expenditure;
        this.inventory = inventory;
    }

    // temporary constructor until we implement booking and inventory, accepts null for now
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate,
                 Expenditure expenditure, Location destination) {
        requireAllNonNull(name, startDate, endDate, expenditure);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = null;
        this.destination = destination;
        this.expenditure = expenditure;
        this.inventory = null;
    }

    /**
     * Constructs a trip with optional expenditure field.
     */
    public Event(Name name, LocalDateTime startDate, LocalDateTime endDate,
                 Optional<Expenditure> expenditure, Location destination) {
        requireAllNonNull(name, startDate, endDate, expenditure);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.booking = null;
        this.destination = destination;
        if (expenditure.isPresent()) {
            this.expenditure = expenditure.get();
        } else {
            this.expenditure = null;
        }
        this.inventory = null;
    }

    public Name getName() {
        return name;
    }


    // Compulsory Field getters
    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Location getDestination() {
        return destination;
    }

    // Optional field getters
    public Optional<Expenditure> getExpenditure() {
        return Optional.ofNullable(expenditure);
    }

    public Optional<Inventory> getInventory() {
        return Optional.ofNullable(inventory);
    }

    public Optional<Booking> getBooking() {
        return Optional.ofNullable(booking);
    }

    /**
     * Returns true if both {@link Event} contain the same booking and their endDate and startDate time are the same.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }
        return otherEvent != null
                && otherEvent.getBooking().equals(getBooking())
                && (otherEvent.getEndDate().equals(getEndDate()) || otherEvent.getStartDate().equals(getStartDate()));

    }

    /**
     * Checks whether this event is clashing with another.
     *
     * @param other The other event to check.
     * @return Boolean of whether the events clash.
     */
    public boolean isClashingWith(Event other) {
        return (this.getStartDate().compareTo(other.getStartDate()) >= 0
                && this.getStartDate().compareTo(other.getEndDate()) <= 0)
                || (this.getEndDate().compareTo(other.getStartDate()) >= 0
                && this.getEndDate().compareTo(other.getEndDate()) <= 0);
    }


}
