package se350.elevatorSim.person;

/**
 * Simple Data Transfer Object that helps a user send their completed report of
 * waiting and ride times to the Report Generator. The data is already checked
 * for errors before this, so no error checking is necessary, therefore all data
 * members are public.
 * 
 * @author James R Hagle
 * 
 */
public class PersonResults implements Comparable<PersonResults> {

	/**
	 * The id of the person whose stats are being reported.
	 */
	public int id;

	/**
	 * The amount of time the person waited for an elevator.
	 */
	public int waitTime;

	/**
	 * The amount of time the person rode the elevator to the destination.
	 */
	public int rideTime;

	/**
	 * The floor the person started out on.
	 */
	public int startingFloor;

	/**
	 * The floor the person ended on.
	 */
	public int endingFloor;

	/**
	 * Useful for sorting collections of PersonResult.
	 */
	@Override
	public int compareTo(PersonResults o) {
		if (id < o.id)
			return -1;
		if (id > o.id)
			return 1;
		return 0;
	}

}
