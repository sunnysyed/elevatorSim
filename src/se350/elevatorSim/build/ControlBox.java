package se350.elevatorSim.build;

import se350.elevatorSim.elevatorcontroller.ElevatorController;
import se350.elevatorSim.expections.InvalidParameterException;

/**
 * Class that represents the ControlBox on each Floor of the building. A User
 * presses the ControlBox, either up or down, sending the ElevatorController a
 * request that it must delegate and assign to an Elevator.
 * 
 * @author James Hagle
 * 
 */
public class ControlBox {

	/**
	 * The floor of the Floor the ControlBox belongs to.
	 * 
	 * @see #setFloor(int)
	 * @see #getFloor()
	 */
	int floor;

	/**
	 * Simple Constructor that sets the floor of this ControlBox
	 * 
	 * @param floor
	 *            - The floor of the Control Box
	 */
	public ControlBox(int floor) throws InvalidParameterException {
		setFloor(floor);
	}

	/**
	 * Simulates the Person pressing up on the ControlBox. Sends a new request
	 * to the ElevatorController for processing.
	 * 
	 * @throws InvalidParameterException
	 *             - if the floor is not a valid Floor
	 */
	public void pressUp() throws InvalidParameterException {
		ElevatorController.getInstance().sendRequest(ElevatorController.UP,
				floor);
	}

	/**
	 * Simulates the Person pressing down on the ControlBox. Sends a new request
	 * to the ElevatorController for processing.
	 * 
	 * @throws InvalidParameterException
	 *             - if the floorNum is not a valid Floor
	 */
	public void pressDown() throws InvalidParameterException {
		ElevatorController.getInstance().sendRequest(ElevatorController.DOWN,
				floor);
	}

	/**
	 * Sets the floor of the ControlBox, used in initialization
	 * 
	 * @param floorIn
	 *            - The floor to set the ControlBox's floor to
	 * @throws InvalidParameterException
	 *             - If floorIn is not valid
	 */
	private void setFloor(int floorIn) throws InvalidParameterException {
		if (floorIn <= 0)
			throw new InvalidParameterException(
					"Elevator Control Box set on an illegal floor number");

		floor = floorIn;
	}

	/**
	 * Get the floor of Floor that the ControlBox is on
	 * 
	 * @return The floor of the Floor that the ControlBox is on.
	 */
	public int getFloor() {
		return floor;
	}
}
