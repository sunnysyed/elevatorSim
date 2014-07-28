package se350.elevatorSim.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import se350.elevatorSim.build.BuildingDTO;
import se350.elevatorSim.expections.InvalidParameterException;

/**
 * Utility class that has a function that gets all simulation data necessary for
 * execution from a file.
 * 
 * @author Shahzaib Syed
 * 
 */
public class DataInputUtility {

	/**
	 * Gets all simulation that is necessary for execution from a file.
	 * 
	 * @param inputFile
	 *            - the File to get the data from
	 * @return BuildingDTO that contains all necessary simulation data.
	 * @throws InvalidParameterException
	 *             if the Data in the file is incorrect/illegal
	 * @throws InvalidParameterException
	 *             if the filename is not a valid file.
	 */
	@SuppressWarnings("resource")
	public static BuildingDTO getBuildingInfoFromFile(String inputFile)
			throws InvalidParameterException, FileNotFoundException {
		File data = new File(inputFile);
		String dataString = "";
		BuildingDTO returnObject = null;

		Scanner s = new Scanner(data);

		while (s.hasNextLine()) {
			dataString = s.nextLine();
		}

		String[] args = dataString.split(",");
		int counter = 0;

		int duration = Integer.parseInt(args[counter++].trim());
		int scale = Integer.parseInt(args[counter++].trim());
		int floors = Integer.parseInt(args[counter++].trim());
		int elevators = Integer.parseInt(args[counter++].trim());
		int maxPersons = Integer.parseInt(args[counter++].trim());
		int msPerFloor = Integer.parseInt(args[counter++].trim());
		int msDoorOperation = Integer.parseInt(args[counter++].trim());

		int[] defaultFloors = new int[elevators];
		for (int i = 0; i < elevators; i++) {
			defaultFloors[i] = Integer.parseInt(args[counter++].trim());
		}

		int personsPerMinute = Integer.parseInt(args[counter++].trim());

		int[] startDestPct = new int[floors];

		for (int i = 0; i < floors; i++) {
			startDestPct[i] = Integer.parseInt(args[counter++].trim());
		}

		returnObject = new BuildingDTO(duration, scale, floors, elevators,
				maxPersons, msPerFloor, msDoorOperation, defaultFloors,
				personsPerMinute, startDestPct);

		return returnObject;
	}
}
