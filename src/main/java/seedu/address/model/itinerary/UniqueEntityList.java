package seedu.address.model.itinerary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;


public abstract class UniqueEntityList<T> implements Iterable<T>{

    public final ObservableList<T> internalList = FXCollections.observableArrayList();
    public final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent person as the given argument.
     */
    public abstract boolean contains(T toCheck);

    /**
     * Adds a person to the list.
     * The person must not already exist in the list.
     */
    public abstract void add(T toAdd);

    /**
     * Replaces the person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the list.
     */
    public abstract void setPerson(T target, T edited);


    /**
     * Removes the equivalent person from the list.
     * The person must exist in the list.
     */
    public abstract void remove(T toRemove);

    public abstract void set(UniqueEntityList<T> replacement);
    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public abstract void set(List<T> entities);

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueEntityList // instanceof handles nulls
                && internalList.equals(((UniqueEntityList<T>) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code persons} contains only unique persons.
     */
    public abstract boolean areUnique(List<T> persons);

}
