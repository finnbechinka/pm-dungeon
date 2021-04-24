package program;

/**
 * Represents all possible states of the hero.
 * 
 * @author Finn Bechinka
 * 
 */
public enum HeroState {
	IDLE("alt"),
	RUNNING_LEFT("running_left"),
	RUNNING_RIGHT("running_right"),
	RUNNING_FORWARDS("running_forwards"),
	RUNNING_BACKWARDS("running_backwards");
	
	private final String val;

    private HeroState(String val) {
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
