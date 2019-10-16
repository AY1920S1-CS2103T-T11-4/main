package seedu.address.logic.parser.preferences;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_TYPE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.preferences.CancelEditPrefsCommand;
import seedu.address.logic.commands.preferences.DoneEditPrefsCommand;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand;
import seedu.address.logic.parser.PageParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses commands related to the preferences page.
 */
public class PreferencesParser implements PageParser<Command> {
    private static final String MESSAGE_COMMAND_TYPES = " Available command types: \n"
            + EditPrefsFieldCommand.COMMAND_WORD + " "
            + DoneEditPrefsCommand.COMMAND_WORD + " "
            + CancelEditPrefsCommand.COMMAND_WORD;

    @Override
    public Command parse(String command, String arguments) throws ParseException {
        PreferencesCommand commandType;
        try {
            commandType = PreferencesCommand.valueOf(command);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }

        switch (commandType) {
        case EDIT:
            return new EditPrefsFieldParser().parse(arguments);
        case CANCEL:
            return new CancelEditPrefsParser().parse(arguments);
        case DONE:
            return new DoneEditPrefsParser().parse(arguments);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_TYPE, MESSAGE_COMMAND_TYPES));
        }
    }
}
