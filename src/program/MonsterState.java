package program;

/**
 * Represents all possible states of a monster.
 * 
 * @author Finn Bechinka
 * 
 */
public enum MonsterState {
	IDLE("alt"),
	RUNNING_LEFT("running_left"),
	RUNNING_RIGHT("running_right"),
	RUNNING_FORWARDS("running_forwards"),
	RUNNING_BACKWARDS("running_backwards"),
	DEAD("dead");
	
	private final String val;

    private MonsterState(String val) {
        this.val = val;
    }

    /**
     * Returns the elements string value.
     * 
     * @return String value of the element.
     */
    @Override
    public String toString() {
        return val;
    }
}
