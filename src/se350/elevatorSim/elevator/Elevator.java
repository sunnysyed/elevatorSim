package se350.elevatorSim.elevator;

import se350.elevatorSim.expections.InvalidParameterException;
import se350.elevatorSim.person.Person;

/**
 * An Elevator interface that defines behavior for every elevator
 * implementation.
 * 
 * Contains methods for adding destinations, getting remaining destinations, and
 * getting the current floor of the elevator.
 * 
 * @author Shahzaib Syed
 * 
 */
public interface Elevator extends Runnable {

	/**
	 * Enum that defines the different states that an Elevator instance can be
	 * in depending on it's current action.
	 * 
	 * @see #getStatus()
	 */
	enum Status {
		WAITING, WAITING_DEFAULT, MOVING_UP, MOVING_DOWN
	}

	/**
	 * Adds a destination to the Elevator implementation.
	 * 
	 * @param floorNum
	 *            - the floor the elevator should stop at.
	 * @throws InvalidParameterException
	 *             if the floor is not in the building
	 */
	public void addDestination(int floorNum) throws InvalidParameterException;

	/**
	 * Indicates the current floor that the elevator is stopped at or passing.
	 * 
	 * @return The current floor that the elevator is stopped at or passing.
	 */
	public int getCurrentFloor();

	/**
	 * Adds a Person implementation to the elevator.
	 * 
	 * @param p
	 *            - The person implementation to add
	 * @return true if there is room for the person and they were added, false
	 *         if no room left and they were not added
	 * @throws InvalidParameterException
	 */
	public boolean addPerson(Person p) throws InvalidParameterException;

	/**
	 * Gets information about the pending requests on the Elevator instance.
	 * 
	 * @return A string containing the remaining pending requests owned by the
	 *         Elevator.
	 */
	public String getRemainingDestinations();

	/**
	 * Gets the Status of an elevator as defined in Elevator.Status
	 * 
	 * @return The status of the elevator as defined in Elevator.Status
	 */
	public Elevator.Status getStatus();

}