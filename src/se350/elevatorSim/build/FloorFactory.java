package se350.elevatorSim.build;

import se350.elevatorSim.expections.InvalidParameterException;

/**
 * A Factory class that builds appropriate Floors for the desired situations.
 * 
 * @author James Hagle
 * 
 */
public class FloorFactory {

	/**
	 * Builds the required Floor implementations
	 * 
	 * @param floor
	 *            - The floor of the building to be built
	 * @return A Floor implementation that best meets the requirements
	 */
	public static Floor build(int floor) throws InvalidParameterException {
		return new FloorImpl(floor);
	}

}
