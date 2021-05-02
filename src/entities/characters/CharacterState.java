package entities.characters;

/**
 * Represents all possible states of characters.
 * 
 * @author Finn Bechinka
 * 
 */
public enum CharacterState {
	IDLE("idle"), RUNNING_LEFT("running_left"), RUNNING_RIGHT("running_right"), RUNNING_FORWARDS("running_forwards"),
	RUNNING_BACKWARDS("running_backwards"), DEAD("dead"), ATTACKING("attacking");

	private final String val;

	private CharacterState(String val) {
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
