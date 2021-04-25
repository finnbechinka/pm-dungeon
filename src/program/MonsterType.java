package program;

public enum MonsterType {
	TANK("tank"),
	RUNNER("runner");
	
	private final String val;
	
	private MonsterType(String val) {
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
