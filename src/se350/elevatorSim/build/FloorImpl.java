package se350.elevatorSim.build;

import java.util.ArrayList;

import se350.elevatorSim.elevator.Elevator;
import se350.elevatorSim.elevatorcontroller.ElevatorController;
import se350.elevatorSim.expections.InvalidParameterException;
import se350.elevatorSim.person.Person;

/**
 * A simple implementation of the Floor interface.
 * 
 * @author James Hagle
 */
public class FloorImpl implements Floor {

	/**
	 * The story of the Floor, used as an id
	 * 
	 * @see #getStory()
	 * @see #setStory(int)
	 */
	int story;

	/**
	 * A collection of all the Person implementations contained on this floor
	 * 
	 * @see #addPerson(Person)
	 */
	ArrayList<Person> people;

	/**
	 * Control box on the floor that people press when
	 */
	ControlBox controlBox;

	/**
	 * Constructor for a Simple Floor.
	 * 
	 * @param story
	 *            - The story that this floor will simulate
	 * @throws InvalidParameterException
	 */
	public FloorImpl(int story) throws InvalidParameterException {
		setStory(story);
		people = new ArrayList<Person>();
		controlBox = new ControlBox(getStory());
	}

	/**
	 * Adds a Person to this Floor
	 * 
	 * @param person
	 *            - Person implementation to add
	 */
	public void addPerson(Person person) {
		synchronized (this) {
			people.add(person);
		}
	}

	/**
	 * Removes a Person from this floor
	 * 
	 * @param id
	 *            - Id of the person to remove
	 */
	public void removePerson(int id) {
		synchronized (this) {
			for (int i = 0; i < people.size(); i++) {
				if (people.get(i).getId() == id) {
					people.remove(i);
				}
			}
		}
	}

	/**
	 * Method to add people from this floor to an elevator that is going in the
	 * people's desired direction. Only adds them if the elevator is not at full
	 * capacity yet.
	 * 
	 * @param e
	 *            - The elevator instance to add the people to.
	 */
	@Override
	public void addPeopleToElevator(Elevator e)
			throws InvalidParameterException {
		synchronized (this) {
			ArrayList<Person> peopleToRemove = new ArrayList<Person>();

			for (int i = 0; i < people.size(); i++) {

				if (e.getStatus() == Elevator.Status.MOVING_UP) {

					if (people.get(i).getDestination() > this.story) {
						if (e.addPerson(people.get(i))) {
							peopleToRemove.add(people.get(i));
						} else {
							break; // Not enough room
						}
					}

				} else if (e.getStatus() == Elevator.Status.MOVING_DOWN) {

					if (people.get(i).getDestination() < this.story) {
						if (e.addPerson(people.get(i))) {
							peopleToRemove.add(people.get(i));
						} else {
							break; // Not enough room
						}
					}

				} else {

					if (people.get(i).getDestination() != this.story) {
						if (e.addPerson(people.get(i))) {
							peopleToRemove.add(people.get(i));
						} else {
							break; // Not enough room
						}
					}

				}

			}

			for (int i = 0; i < peopleToRemove.size(); i++) {
				peopleToRemove.get(i).setAddedToElevatorTime();
			}

			people.removeAll(peopleToRemove);
		}
	}

	/**
	 * Sets the story of the floor
	 * 
	 * @param storyIn
	 *            - the story to set
	 */
	private void setStory(int storyIn) {
		story = storyIn;
	}

	/**
	 * Gets the story of the floor.
	 */
	public int getStory() {
		return story;
	}

	/**
	 * Called when a Person is generated and needs to get to a new floor. This
	 * floor gets added to the ElevatorController's pending requests.
	 * 
	 * @param direction
	 *            - The direction that the new Person needs to go
	 * @throws InvalidParameterException
	 */
	@Override
	public void pressControlBox(int direction) throws InvalidParameterException {
		if (direction == ElevatorController.UP)
			controlBox.pressUp();
		else
			controlBox.pressDown();
	}

}
