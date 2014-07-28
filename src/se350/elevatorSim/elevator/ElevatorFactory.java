package se350.elevatorSim.elevator;

import se350.elevatorSim.expections.InvalidParameterException;

/**
 * Factory that creates Elevator implementations that best fit the parameters.
 * 
 * @author Brian Chiem
 * 
 */
public class ElevatorFactory {

	/**
	 * Creates Elevator implementations that best fit the parameters.
	 * 
	 * @param elevatorNum
	 *            - Number/id of the elevator
	 * @return Best fit implementation of an Elevator
	 * @throws InvalidParameterException
	 *             if the elevatorNum < 1
	 */
	public static Elevator build(int elevatorNum, int defaultFloor,
			int msPerFloor, int msDoorOperation)
			throws InvalidParameterException {
		return new ElevatorImpl(elevatorNum, defaultFloor, msPerFloor,
				msDoorOperation);
	}

}
