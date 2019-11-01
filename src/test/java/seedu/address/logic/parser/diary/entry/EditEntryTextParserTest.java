package seedu.address.logic.parser.diary.entry;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.diary.entry.EditEntryTextCommand;
import seedu.address.model.diary.DiaryTestUtil;

/**
 * Mix of unit and integration tests of {@link EditEntryTextParser}.
 */
class EditEntryTextParserTest {

    private static final int TEST_FACTORY_COUNT = 15;

    @Test
    void parse_inputNull_throwsNullPointer() {
        EditEntryTextParser editEntryTextParser = new EditEntryTextParser();
        assertThrows(NullPointerException.class, () -> editEntryTextParser.parse(null));
    }

    @Test
    void parse_absentDescription_throwsParseException() {
        assertParseFailure(new EditEntryTextParser(), String.format(" %1$s%2$d", PREFIX_INDEX, 10),
                String.format(MESSAGE_MISSING_FIELDS, EditEntryTextCommand.MESSAGE_USAGE));
        assertParseFailure(new EditEntryTextParser(), " ",
                String.format(MESSAGE_MISSING_FIELDS, EditEntryTextCommand.MESSAGE_USAGE));
    }

    @Test
    void parse_invalidIndex_throwsParseException() {
        String testDescription = "abc";
        String invalidIndex = "abc 10";

        assertParseFailure(new EditEntryTextParser(),
                String.format(" %1$s%2$s %3$s%4$s", PREFIX_DESCRIPTION, testDescription, PREFIX_INDEX, invalidIndex),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryTextCommand.MESSAGE_USAGE));
    }

    /**
     * Generates test cases of valid descriptions using {@link DiaryTestUtil}, with no index.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    @TestFactory
    Stream<DynamicTest> parse_indexAbsent_returnsEditEntryTextCommandWithoutIndex() {
        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    String testDescription = DiaryTestUtil.generateRandomText().trim();
                    String userInput = String.format(" %1$s%2$s", PREFIX_DESCRIPTION, testDescription);

                    return dynamicTest(String.format(
                            "EditEntryTextParser index absent random description: %s",
                            testDescription), () ->
                                    assertParseSuccess(
                                            new EditEntryTextParser(),
                                            userInput,
                                            new EditEntryTextCommand(testDescription)));
                });
    }

    /**
     * Generates test cases of valid indexes and descriptions using {@code Math.random()} and {@link DiaryTestUtil}.
     *
     * @return {@code Stream<DynamicTest>} for use by the Junit testing engine.
     */
    @TestFactory
    Stream<DynamicTest> parse_indexPresent_returnsEditEntryTextCommand() {
        return IntStream.range(0, TEST_FACTORY_COUNT)
                .mapToObj(testCount -> {
                    String testDescription = DiaryTestUtil.generateRandomText().trim();
                    int randNum = (int) (Math.random() * (Integer.MAX_VALUE - 1)) + 1;
                    String userInput = String.format(" %1$s%2$s %3$s%4$d",
                            PREFIX_DESCRIPTION, testDescription,
                            PREFIX_INDEX, randNum);

                    return dynamicTest(
                            String.format("EditEntryTextParser index present random description: %s", userInput), () ->
                                    assertParseSuccess(new EditEntryTextParser(), userInput,
                                            new EditEntryTextCommand(testDescription, Index.fromOneBased(randNum))));
                });
    }
}
