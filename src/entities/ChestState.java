package entities;

/**
 * Represents all possible states of chests.
 * 
 * @author Finn Bechinka
 * 
 */
public enum ChestState {
	OPEN("open"), CLOSED("closed"), OPENING("opening");

	private final String val;

	private ChestState(String val) {
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
