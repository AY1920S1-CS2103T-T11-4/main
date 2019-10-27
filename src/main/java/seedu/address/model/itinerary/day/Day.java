package seedu.address.model.itinerary.day;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.EventList;

/**
 * Represents a Day in TravelPal.
 * Compulsory fields: name, startDate, endDate, destination, eventList.
 * Optional fields: totalBudget, description.
 */
public class Day {
    // Compulsory Fields
    private final Name name;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Location destination;
    private final EventList eventList;

    // Optional Fields
    private final Budget totalBudget;
    private final Description description;

    /** Constructs a Day */
    public Day(Name name, LocalDateTime startDate, LocalDateTime endDate, Description description,
               Location destination, Budget totalBudget, EventList eventList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.destination = destination;
        this.totalBudget = totalBudget;
        this.eventList = eventList;
    }

    /** Constructs a day with optional fields */
    public Day (Name name, LocalDateTime startDate, LocalDateTime endDate, Optional<Description> description,
                Location destination, Optional<Budget> totalBudget, EventList eventList) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description.orElse(null);
        this.destination = destination;
        this.totalBudget = totalBudget.orElse(null);
        this.eventList = eventList;
    }

    // Compulsory Field getters
    public Name getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public EventList getEventList() {
        return eventList;
    }

    public Location getDestination() {
        return destination;
    }

    // Optional field getters
    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Budget> getTotalBudget() {
        return Optional.of(totalBudget);
    }


    /**
     * Soft check of whether the days clash.
     *
     * @param otherDay The other day to check.
     * @return Boolean of whether the days clash.
     */
    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        } else {
            return otherDay.getName().equals(getName())
                    && otherDay.getStartDate().equals(getStartDate())
                    && otherDay.getEndDate().equals(getEndDate())
                    && otherDay.getDestination().equals(getDestination());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.getName().equals(getName())
                && otherDay.getStartDate().equals(getStartDate())
                && otherDay.getEndDate().equals(getEndDate())
                && otherDay.getDescription().equals(getDescription())
                && otherDay.getDestination().equals(getDestination())
                && otherDay.getEventList().equals(getEventList());
    }

    /**
     * Checks whether this day clashes with another.
     *
     * @param other The other day instance to check.
     * @return Boolean of whether the days clash.
     */
    public boolean isClashingWith(Day other) {
        return (this.getStartDate().compareTo(other.getStartDate()) >= 0
                && this.getStartDate().compareTo(other.getEndDate()) <= 0)
                || (this.getEndDate().compareTo(other.getStartDate()) >= 0
                && this.getEndDate().compareTo(other.getEndDate()) <= 0);
    }

    @Override
    public String toString() {
        return "Name: " + name
                + ", Start Date:" + startDate
                + ", End Date: " + endDate
                + ", Destination: " + destination
                + ", TotalBudget: " + totalBudget
                + ", Description: " + description;
    }
}
