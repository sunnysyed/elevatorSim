package se350.elevatorSim.elevatorcontroller;

import se350.elevatorSim.elevator.ElevatorDTO;

/**
 * Interface that defines an delegate for the ElevatorController class. Each
 * Implementation can have it's own algorithm for deciding which elevator to
 * respond to each call.
 * 
 * @author Brian Chiem
 * 
 */
public interface ElevatorDecisionDelegate {

	/**
	 * Gets the best elevator to send for a request.
	 * 
	 * @return The number of the elevator that should be sent.
	 */
	public int getBestElevator(int direction, int floor, ElevatorDTO[] data);

}
