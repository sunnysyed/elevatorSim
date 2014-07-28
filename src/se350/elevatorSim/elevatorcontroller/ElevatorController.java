package se350.elevatorSim.elevatorcontroller;

import java.util.ArrayList;

import se350.elevatorSim.build.BuildingDTO;
import se350.elevatorSim.build.FloorManager;
import se350.elevatorSim.elevator.Elevator;
import se350.elevatorSim.elevator.ElevatorDTO;
import se350.elevatorSim.elevator.ElevatorFactory;
import se350.elevatorSim.expections.InvalidParameterException;

/**
 * Class responsible for making decisions of which elevators to send to which
 * floors. Gets calls from ElevatorCallboxes and uses ElevatorDecisionDelegates
 * to decide where to send which elevators.
 * 
 * @author Shahzaib Syed
 * 
 */
public class ElevatorController {

	/**
	 * Elevator State - is not moving
	 */
	public static final int NONE = 0;

	/**
	 * Elevator State - moving up
	 */
	public static final int UP = 1;

	/**
	 * Elevator State - moving down
	 */
	public static final int DOWN = 2;

	/**
	 * Makes the class a singleton
	 */
	private static ElevatorController controller;

	/**
	 * Collection of all the Elevator implementations in the building.
	 */
	private static ArrayList<Elevator> elevators;

	/**
	 * Contains floors requests going up that have not been resolved yet.
	 */
	private static ArrayList<Integer> pendingUpRequests;

	/**
	 * Contains floors requests going down that have not been resolved yet.
	 */
	static ArrayList<Integer> pendingDownRequests;

	/**
	 * Decides the best elevator to respond to a request.
	 */
	private static ElevatorDecisionDelegate decisionDelegate;

	/**
	 * Private constructor allowing for a singleton.
	 */
	private ElevatorController() {
	}

	/**
	 * Initializes the Elevator controller to be used. MUST be called before any
	 * calls to getInstance or any other public method.
	 * 
	 * @param buildingStats
	 *            - The data block that holds neccessary information about the
	 *            elevators
	 * @throws InvalidParameterException
	 *             - If data passed into the new elevators is invalid
	 */
	public static synchronized void initialize(BuildingDTO buildingStats)
			throws InvalidParameterException {
		controller = new ElevatorController();
		elevators = new ArrayList<Elevator>();
		pendingUpRequests = new ArrayList<Integer>();
		pendingDownRequests = new ArrayList<Integer>();
		decisionDelegate = ElevatorDecisionDelegateFactory.build();

		for (int i = 1; i <= buildingStats.getNumOfElevators(); i++) {
			elevators.add(ElevatorFactory.build(i,
					buildingStats.getDefaultFloor(i),
					buildingStats.getMsPerFloor(),
					buildingStats.getMsDoorOperation()));
		}

		startElevators();
	}

	/**
	 * Used for testing building creation.
	 */
	public static void destroy() {
		controller = null;
	}

	/**
	 * Returns the single instance of ElevatorController in singleton fashion
	 * 
	 * @return The single instance of ElevatorController
	 */
	public static ElevatorController getInstance() {
		if (controller == null)
			throw new IllegalStateException(
					"Must initialize ElevatorController before using it");

		return controller;
	}

	/**
	 * Private method that starts all of the Elevator threads
	 */
	private static void startElevators() {
		for (Elevator e : elevators) {
			Thread t = new Thread(e);
			t.start();
		}
	}

	/**
	 * Public method that is called by ElevatorControlBox that creates a new
	 * pending request in the Elevator Controller.
	 * 
	 * @param direction
	 *            - Up or down, depending on what the Person needs
	 * @param floor
	 *            - The floor that needs to be added to requests.
	 */
	public int sendRequest(int direction, int floor)
			throws InvalidParameterException {
		int bestElevator = decisionDelegate.getBestElevator(direction, floor,
				getElevatorData());

		if (bestElevator != 0) {
			sendRequestToElevator(bestElevator, floor);
			return 1;
		} else {

			switch (direction) {
			case ElevatorController.UP:
				if (!pendingUpRequests.contains(floor))
					pendingUpRequests.add(floor);
				break;
			case ElevatorController.DOWN:
				if (!pendingDownRequests.contains(floor))
					pendingDownRequests.add(floor);
				break;
			}

		}
		return 0;
	}

	/**
	 * Utility method that allows the simulation "driver" to send requests to
	 * the elevators.
	 * 
	 * @param elevatorId
	 *            - Which elevator to send
	 * @param floor
	 *            - The floor to send the elevator to
	 * @throws InvalidParameterException
	 *             if the elevatorId is greater than the number of elevators or
	 *             less than 1
	 * @throws InvalidParameterException
	 *             if the floor is greater or less than the number of floors
	 */
	public void sendRequestToElevator(int elevatorId, int floor)
			throws InvalidParameterException {
		if (elevatorId < 1 || elevatorId > elevators.size())
			throw new InvalidParameterException(
					"Must call send request to valid elevator, elevatorId"
							+ elevatorId + " outside of range");

		if (floor < 1 || floor > FloorManager.getInstance().getNumberOfFloors())
			throw new InvalidParameterException(
					"Elevator being sent out of floor range.");

		Elevator e = elevators.get(elevatorId - 1);
		synchronized (e) {
			e.notify();
			e.addDestination(floor);
		}
	}

	/**
	 * Checks the pending requests every second to see if they are now able to
	 * be executed.
	 * 
	 * @throws InvalidParameterException
	 *             if the requests to the elevator are not valid floors.
	 */
	public void checkIfPendingNowValid() throws InvalidParameterException {
		ArrayList<Integer> toRemove = new ArrayList<Integer>();

		for (int i = 0; i < pendingUpRequests.size(); i++) {
			if (sendRequest(ElevatorController.UP, pendingUpRequests.get(i)) == 1) {
				toRemove.add(pendingUpRequests.get(i));
			}
		}

		pendingUpRequests.removeAll(toRemove);

		toRemove = new ArrayList<Integer>();

		for (int i = 0; i < pendingDownRequests.size(); i++) {
			if (sendRequest(ElevatorController.DOWN, pendingDownRequests.get(i)) == 1) {
				toRemove.add(pendingDownRequests.get(i));
			}
		}

		pendingDownRequests.removeAll(toRemove);
	}

	/**
	 * Private method that gets generates an array of ElevatorDTO's that contain
	 * the information necessary for the delegates to choose which elevator is
	 * the best option for a request
	 * 
	 * @return An array of information about each Elevator instance
	 */
	public ElevatorDTO[] getElevatorData() {
		ElevatorDTO[] data = new ElevatorDTO[elevators.size()];

		for (int i = 0; i < elevators.size(); i++) {
			ElevatorDTO dto = new ElevatorDTO();
			dto.currentFloor = elevators.get(i).getCurrentFloor();
			dto.status = elevators.get(i).getStatus();
			dto.remainingDestinations = elevators.get(i)
					.getRemainingDestinations();
			data[i] = dto;
		}

		return data;
	}
}
