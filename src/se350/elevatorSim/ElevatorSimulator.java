package se350.elevatorSim;

import java.io.FileNotFoundException;

import se350.elevatorSim.build.Building;
import se350.elevatorSim.expections.InvalidParameterException;

/**
 * ElevatorSimulator contains the main method that is the driver for the current
 * simulation. It starts the simulation.
 * 
 * @author Shahzaib Syed
 * 
 */
public class ElevatorSimulator {

	public static void main(String[] args) throws InvalidParameterException,
			FileNotFoundException {
		@SuppressWarnings("unused")
		Building building = new Building("input.txt");
	}

}