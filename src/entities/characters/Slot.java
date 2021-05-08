package entities.characters;

/**
 * Represents all possible slots (inventory, chest, bag).
 * 
 * @author Finn Bechinka
 * 
 */
public enum Slot {
	INVENTORY1("0"),
	INVENTORY2("1"),
	INVENTORY3("2"),
	INVENTORY4("3"),
	INVENTORY5("4"),
	CHEST1("0"),
	CHEST2("1"),
	CHEST3("2"),
	CHEST4("3"),
	BAG1("0"),
	BAG2("1"),
	BAG3("2");

	private final String val;

	private Slot(String val) {
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
