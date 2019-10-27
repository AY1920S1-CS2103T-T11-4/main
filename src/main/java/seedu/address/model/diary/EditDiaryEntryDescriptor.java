package seedu.address.model.diary;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.photo.PhotoList;

/**
 * Class for storing diary text edit information as a separate buffer,
 * allowing discarding changes before committing to a {@link DiaryEntry}.
 */
public class EditDiaryEntryDescriptor {
    private static final int MAX_DIARY_TEXT_DISPLAY_LENGTH = 20;

    private final Index dayIndex;
    private final PhotoList photoList;
    private String diaryText;

    public EditDiaryEntryDescriptor(DiaryEntry diaryEntry) {
        this(diaryEntry.getDayIndex(), diaryEntry.getPhotoList(), diaryEntry.getDiaryText());
    }

    /**
     * Constructs a new {@code DiaryEntry} tied to the specified {@code day}, {@code photoList},
     * and {@code diaryText} instances.
     *
     * @param dayIndex The {@code Day} instance to use.
     * @param photoList The {@code PhotoList} to use.
     * @param diaryText The {@code String} to use to hold the entry's text.
     */
    private EditDiaryEntryDescriptor(Index dayIndex, PhotoList photoList, String diaryText) {
        requireAllNonNull(dayIndex, photoList, diaryText);
        this.dayIndex = dayIndex;
        this.photoList = photoList;
        this.diaryText = diaryText;
    }

    /**
     * Builds the diary entry from the instance variables that this edit descriptor holds.
     *
     * @return A new {@link DiaryEntry} constructed from {@code dayIndex}, {@code photoList}, {@code diaryText}.
     */
    public DiaryEntry buildDiaryEntry() {
        requireAllNonNull(dayIndex, diaryText, photoList);

        return new DiaryEntry(dayIndex, photoList, diaryText);
    }

    public String getDiaryText() {
        return diaryText;
    }

    public void setDiaryText(String diaryText) {
        this.diaryText = diaryText;
    }

    /**
     * Appends the a newline, with the specified {@code textToAdd} to the {@code diaryText}.
     *
     * @param textToAdd The {@link String} to append.
     */
    public void addNewTextLine(String textToAdd) {
        this.diaryText += textToAdd;
        this.diaryText += "\n";
    }

    /**
     * Inserts the new line, with the specified {@code textToInsert} to the {@code diaryText},
     * at the specified {@code lineNumber}.
     * If the {@code lineNumber} is more than the current amount of lines in the diaryText,
     * then {@code lineNumber.getZeroBased - numberOfLines} newlines will be inserted, followed
     * by the {@code textToInsert}.
     *
     * @param textToInsert The {@link String} to insert.
     * @param lineNumber The {@link Index} of the line number to insert the {@code textToInsert} at.
     * @throws IllegalArgumentException If the zero-based index of {@code lineNumber} is negative.
     */
    public void insertTextLine(String textToInsert, Index lineNumber) throws IllegalArgumentException {
        if (lineNumber.getZeroBased() < 0) {
            throw new IllegalArgumentException("Illegal argument passed to insertTextLine: " + MESSAGE_INVALID_INDEX);
        }

        String[] paragraphs = this.diaryText.split("\n");
        StringBuilder diaryTextBuilder = new StringBuilder();

        int maxIndexBeforeInsert = Math.min(lineNumber.getZeroBased(), paragraphs.length);
        for (int i = 0; i < maxIndexBeforeInsert; i++) {
            diaryTextBuilder.append(paragraphs[i]).append("\n");
        }

        diaryTextBuilder.append("\n".repeat(
                Math.max(lineNumber.getZeroBased() - paragraphs.length, 0)));
        diaryTextBuilder.append(textToInsert).append("\n");

        for (int i = lineNumber.getZeroBased(); i < paragraphs.length; i++) {
            diaryTextBuilder.append(paragraphs[i]).append("\n");
        }

        this.diaryText = diaryTextBuilder.toString();
    }

    /**
     * Edits the line of text specified at {@code lineToEdit} {@code Index} provided to the {@code newText}.
     *
     * @param newText {@link String} of new text to replace the specified line with.
     * @param lineToEdit The {@link Index} of the line of text to edit.
     * @return True if there a paragraph of text at the specified index exists and was edited.
     * @throws IllegalArgumentException If the zero-based index of {@code lineNumber} is negative.
     */
    public boolean editTextLine(String newText, Index lineToEdit) throws IllegalArgumentException {
        if (lineToEdit.getZeroBased() < 0) {
            throw new IllegalArgumentException("Illegal argument passed to editTextLine: " + MESSAGE_INVALID_INDEX);
        }

        String[] paragraphs = this.diaryText.split("\n");
        if (lineToEdit.getZeroBased() >= paragraphs.length) {
            return false;
        }
        paragraphs[lineToEdit.getZeroBased()] = newText;
        StringBuilder diaryTextBuilder = new StringBuilder();

        for (String paragraph : paragraphs) {
            diaryTextBuilder.append(paragraph).append("\n");
        }
        this.diaryText = diaryTextBuilder.toString();

        return true;
    }

    /**
     * Deletes the paragraph of text at the specified zero based index.
     * Paragraphs are ordered by the number of newlines in its prefix.
     *
     * @return True if there a paragraph of text at the specified index exists and was deleted.
     */
    public boolean deleteTextParagraph(int lineToDelete) {
        String[] paragraphs = this.diaryText.split("\n");
        StringBuilder diaryTextBuilder = new StringBuilder();
        boolean didDelete = false;

        for (int i = 0; i < paragraphs.length; i++) {
            if (i == lineToDelete) {
                didDelete = true;
                continue;
            }

            diaryTextBuilder
                    .append(paragraphs[i])
                    .append(i == (paragraphs.length - 1) ? "" : "\n");
        }
        this.diaryText = diaryTextBuilder.toString();

        return didDelete;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int diaryTextLength = diaryText.length();
        int displayLength = Math.min(MAX_DIARY_TEXT_DISPLAY_LENGTH, diaryTextLength);

        builder.append("Day Number: ")
                .append(dayIndex.getOneBased())
                .append(" Diary Text (Truncated): ")
                .append(diaryText.substring(0, displayLength))
                .append(" Photo List: ")
                .append(photoList.toString());

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof EditDiaryEntryDescriptor)) {
            return false;
        }

        EditDiaryEntryDescriptor otherEntry = (EditDiaryEntryDescriptor) obj;

        return dayIndex.equals(otherEntry.dayIndex)
                && diaryText.equals(otherEntry.diaryText)
                && photoList.equals(otherEntry.photoList);
    }
}
