package seedu.address.logic.commands.inventory;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.inventory.Inventory;

/**
 * This class's execute function is called whenever an item is added to the inventory
 */
public class AddInventoryCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add a thing to the inventory list.";

    public static final String MESSAGE_SUCCESS = "The thing has been added to inventory list";

    private final String name;


    /**
     * Initialise AddInventoryCommand with name of inventory to add
     * @param name name of inventory to add
     */
    public AddInventoryCommand (final String name) {
        this.name = name;
    }

    /**
     * Method executed when an item is added to the inventory list.
     * @param model {@code Model} which the command should operate on.
     * @return the CommandResult
     * @throws CommandException
     */
    @Override
    public CommandResult execute (Model model) throws CommandException {

        System.out.println("name is " + name);

        requireNonNull(model);

        System.out.println("BEFORE");

        Inventory toAdd = new Inventory(name);

        System.out.println("AFTER");

        model.getPageStatus().getTrip().getInventoryList().add(toAdd);

        System.out.println("I AM JEWISH FUCK");

        return new CommandResult(MESSAGE_SUCCESS, false);

    }
}
