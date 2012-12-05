package fi.sandman.navici;

/**
 * Represents settings specific for "To" and "From" locations.
 * 
 * @author Jouni Latvatalo<jouni.latvatalo@gmail.com>
 * 
 */
public enum Location {
	FROM(0), TO(1);

	private int order;

	/**
	 * Order tells Navici server weather the used location is "To" or "From"
	 * location. For "From" the value is 0 and for "To" the value is 1.
	 * 
	 * @return
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Order tells Navici server weather the used location is "To" or "From"
	 * location. For "From" the value is 0 and for "To" the value is 1.
	 * 
	 * @param order
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	private Location(int order) {
		this.order = order;
	}
}
