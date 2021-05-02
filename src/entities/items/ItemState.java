package entities.items;

/**
 * Represents all possible states of items.
 * 
 * @author Finn Bechinka
 * 
 */
public enum ItemState {
	ON_GROUND("on_ground"), IN_INVENTORY("in_inventory");

	private final String val;

	private ItemState(String val) {
		this.val = val;
	}

	/**
	 * Returns the elements string value.
	 * 
	 * @return String value of the item.
	 */
	@Override
	public String toString() {
		return val;
	}
}
