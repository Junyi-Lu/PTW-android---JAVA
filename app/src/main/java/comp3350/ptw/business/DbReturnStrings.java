package comp3350.ptw.business;

/**
 * Return Strings enum for the database
 */
public enum DbReturnStrings {

    NOT_IN_LIST("Show not in current list."),
    IN_LIST("Show already added"),
    ADD_SUCCESS("Show successfully added to list."),
    REMOVE_SUCCESS("Show removed from list."),
    UPDATE_SUCCESS("Update successful"),
    NOT_A_RATING("Rating must be between 0-5"),
    NOT_A_PRIORITY("New priority must be greater than 0 no more than the list size and must be different from the old priority.");

    private final String text;

    DbReturnStrings(final String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }

}
