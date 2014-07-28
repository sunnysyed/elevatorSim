package se350.elevatorSim.build;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import se350.elevatorSim.elevator.Elevator;
import se350.elevatorSim.elevator.ElevatorDTO;
import se350.elevatorSim.elevatorcontroller.ElevatorController;
import se350.elevatorSim.expections.InvalidParameterException;
import se350.elevatorSim.person.PersonGenerator;
import se350.elevatorSim.statistics.ReportGenerator;
import se350.elevatorSim.utilities.DataInputUtility;

/**
 * Building the container for the entire Elevator simulation, creating and
 * holding all of the elevators and initializing the FloorManager.
 * 
 * @author Shahzaib Syed & James R Hagle
 * 
 */
public class Building {

	/**
	 * Building data compiled from data text file. Contains all the data
	 * Necessary to create a customizable simulation.
	 */
	private BuildingDTO buildingStats;

	/**
	 * Integer used for keeping track of the simulation time.
	 */
	private static int currentTime;

	/**
	 * Duration of the simulation in minutes, must be multiplied by 60 in order
	 * to get the length in seconds. Specified through input file.
	 */

	private int durationInMinutes;

	/**
	 * Setting the current time
	 */
	private static long firstTime;

	/**
	 * The ratio of time to make faster simulations possible. Ex. If
	 * timeScale==5, every five seconds of simulation time is one second of real
	 * time. In other words, there is a 5:1 ratio.
	 */
	private static int timeScale;

	/**
	 * Boolean that keeps track of if the simulation is still running. Starts
	 * true then becomes false after currentTime >= durationInMintues * 60
	 */
	private static boolean isRunning;

	/**
	 * Creates a Building instance which creates/holds all of the elevators,
	 * initializes the FloorManager, and then begins the simulation by starting
	 * a thread for each elevator.
	 * 
	 * @param inputFile
	 *            - The input file to get the data from.
	 * @throws InvalidParameterException
	 *             when floors < 2 or number of elevators < 1
	 * @throws FileNotFoundException
	 *             if the specified file is not valid
	 */
	public Building(String inputFile) throws IllegalStateException,
			InvalidParameterException, FileNotFoundException {
		isRunning = true;
		currentTime = 0;
		Date date = new Date();
		firstTime = date.getTime();
		buildingStats = DataInputUtility.getBuildingInfoFromFile(inputFile);

		durationInMinutes = buildingStats.getSimulationTime();
		timeScale = buildingStats.getTimeScaleFactor();
		ReportGenerator.initialize(buildingStats.getNumOfFloors());
		FloorManager.initialize(buildingStats.getNumOfFloors());
		PersonGenerator.initialize(buildingStats);
		ElevatorController.initialize(buildingStats); // Starts the elevator
		simulate();// threads

	}

	/**
	 * Constructor used ONLY for debugging purposes. DOES NOt begin the
	 * simulation, rather waits for input from test cases.
	 */
	public Building(String inputFile, boolean isTesting)
			throws IllegalStateException, InvalidParameterException,
			FileNotFoundException {
		isRunning = true;
		currentTime = 0;
		buildingStats = DataInputUtility.getBuildingInfoFromFile(inputFile);
		;

		if (buildingStats == null) {
			throw new IllegalStateException("input.txt not found");
		}

		durationInMinutes = buildingStats.getSimulationTime();
		timeScale = buildingStats.getTimeScaleFactor();
		ReportGenerator.initialize(buildingStats.getNumOfFloors());
		FloorManager.initialize(buildingStats.getNumOfFloors());
		PersonGenerator.initialize(buildingStats);
		ElevatorController.initialize(buildingStats); // Starts the elevator
														// threads
		if (!isTesting)
			simulate();
	}

	/**
	 * The main loop for the main thread that keeps the simulation running for
	 * (durationInMinutes*60) seconds. Also makes a call to possibly generate a
	 * person every second.
	 * 
	 * @throws InvalidParameterException
	 *             if data in the input file is invalid, as specified by the
	 *             message
	 */
	private void simulate() throws InvalidParameterException {
		try {

			while (currentTime < durationInMinutes * 60) {
				currentTime++;
				Thread.sleep(1000 / Building.getTimeScale());
				PersonGenerator.getInstance().generateAndAddPerson();
				ElevatorController.getInstance().checkIfPendingNowValid();
			}

			// Wait for a time on all the elevators
			boolean timeout = false;
			while (!timeout) {
				ElevatorController.getInstance().checkIfPendingNowValid();
				ElevatorDTO[] data = ElevatorController.getInstance()
						.getElevatorData();
				int count = 0;
				for (int i = 0; i < data.length; i++) {
					if (data[i].status == Elevator.Status.WAITING_DEFAULT) {
						count++;
						if (count == buildingStats.getNumOfElevators()) {
							timeout = true;
						}

					} else {
						timeout = false;
					}
				}
				Thread.sleep(1000 / Building.getTimeScale());
				currentTime++;
			}

			isRunning = false;

			Thread.sleep(2000); // Wait for other threads to finish

			System.out.println("Ending simulation. Generating report...");
			ReportGenerator.getInstance().printFinalReport();

			System.exit(0);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Keeps track of simulation time, useful for logging.
	 * 
	 * @return A String formatting of the current hour, minute and second in
	 *         hh:mm:ss:sss format
	 */
	public static String getTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("00:mm:ss.SSS");
		Date date = new Date();
		long nowTime = date.getTime();
		long CurrTime = nowTime - firstTime;
		String s = sdf.format(CurrTime).toString();
		return s;

	}

	/**
	 * Get the time scale of the simulation, the larger the number, the faster
	 * the simulation is compared to real time.
	 * 
	 * @return The number of simulated seconds that correspond to actual
	 *         seconds.
	 */
	public static int getTimeScale() {
		return timeScale;
	}

	/**
	 * Get whether the simulation is still running or over.
	 * 
	 * @return True if the simulation is running, false if it is over
	 */
	public static boolean isRunning() {
		return isRunning;
	}

	/**
	 * Get current time in the simulation, specified by simulated seconds
	 * 
	 * @return The number of simulated seconds gone by since it began
	 */
	public static int getCurrentTime() {
		return currentTime;
	}

}
