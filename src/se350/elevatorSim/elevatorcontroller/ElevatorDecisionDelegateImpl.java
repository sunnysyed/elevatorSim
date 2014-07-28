package se350.elevatorSim.elevatorcontroller;

import se350.elevatorSim.build.Building;
import se350.elevatorSim.elevator.Elevator;
import se350.elevatorSim.elevator.ElevatorDTO;

/**
 * Implementation of the ElevatorDecisionDelegate interface that improves upon
 * the ElevatorDecisionDelegate's algorithm. The ElevatorController delegates
 * the selection
 * 
 * @author Brian Chiem and Shahzaib Syed
 * 
 */
public class ElevatorDecisionDelegateImpl implements ElevatorDecisionDelegate {

	/**
	 * Chooses the best elevator to send a destination to based on the direction
	 * of the request, the floor that needs to be stopped at, and the current
	 * position/direction of all of the elevators.
	 */
	public int getBestElevator(int direction, int floor, ElevatorDTO[] data) {

		// checks if a waiting elevator is on that floor then checks if the new
		// request is in the moving path of an elevator.
		for (int i = 1; i <= data.length; i++) {
			ElevatorDTO elevator = data[i - 1];

			if ((elevator.status == Elevator.Status.WAITING || elevator.status == Elevator.Status.WAITING_DEFAULT)
					&& elevator.currentFloor == floor) {
				System.out.println(String.format(
						"%s Sending Elevator %d to Floor %d",
						Building.getTimeString(), i, floor));
				return i;
			}

			if ((elevator.status == Elevator.Status.MOVING_UP
					&& elevator.currentFloor < floor && direction == ElevatorController.UP)
					|| (elevator.status == Elevator.Status.MOVING_DOWN
							&& elevator.currentFloor > floor && direction == ElevatorController.DOWN)) {
				System.out.println(String.format(
						"%s Sending Elevator %d to Floor %d",
						Building.getTimeString(), i, floor));
				return i;
			}
		}
		// end pass

		// Finds the closest waiting elevator
		int closestElevator = 0;
		int closestDistance = 16;

		for (int i = data.length; i > 0; i--) {
			ElevatorDTO elevator = data[i - 1];

			if (elevator.status == Elevator.Status.WAITING_DEFAULT
					|| elevator.status == Elevator.Status.WAITING) {
				int distance = getDistance(elevator.currentFloor, floor);

				if (distance <= closestDistance) {
					closestDistance = distance;
					closestElevator = i;
				}

			}
		}

		if (closestElevator != 0) {
			System.out.println(String.format(
					"%s Sending Elevator %d to Floor %d",
					Building.getTimeString(), closestElevator, floor));
			return closestElevator;
		}
		// end pass

		// No good fit
		System.out.println(String.format("%s No good fit for %d",
				Building.getTimeString(), floor));
		return 0;
	}

	/**
	 * Get the distance between two floors.
	 * 
	 * @param currentFloor
	 *            - The current floor of the elevator
	 * @param destFloor
	 *            - The floor to get the distance from
	 * @return The distance between the two floors.
	 */
	private int getDistance(int currentFloor, int destFloor) {
		return Math.abs(currentFloor - destFloor);
	}

}
