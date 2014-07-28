package se350.elevatorSim.build;

import se350.elevatorSim.elevator.Elevator;
import se350.elevatorSim.expections.InvalidParameterException;
import se350.elevatorSim.person.Person;

/**
 * Interface representing the behavior of a Floor. So far it just defines adding
 * a person to the floor and getting the story of it.
 * 
 * @author Shahzaib Syed
 * 
 */
public interface Floor {

	/**
	 * Adds a person implementation to the floor.
	 * 
	 * @param person
	 *            - Person to add to the floor
	 */
	public void addPerson(Person person);

	/**
	 * Adds all people on this floor that want to go in the direction of the
	 * elevator to said elevator.
	 * 
	 * @param e
	 *            - The elevator to add people to if they want to go in the same
	 *            direction
	 * @throws InvalidParameterException
	 *             if input data is invalid - specified by error message
	 */
	public void addPeopleToElevator(Elevator e)
			throws InvalidParameterException;

	/**
	 * Get the height/id of the floor. The story of the building which it
	 * represents.
	 * 
	 * @return The story for the building which the Floor represents.
	 */
	public int getStory();

	/**
	 * Method that simulates a Person pressing a ControlBox on whatever Floor
	 * they are on in their desired direction.
	 * 
	 * @param direction
	 *            - The direction the Person wants to go
	 * @throws InvalidParameterException
	 *             if input data is invalid - specified by error message
	 */
	public void pressControlBox(int direction) throws InvalidParameterException;

}
