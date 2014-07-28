package se350.elevatorSim.elevatorcontroller;

/**
 * A Factory class that builds DecisionDelegates for the Elevator Controller
 * 
 * @author Brian Chiem
 * 
 */
public class ElevatorDecisionDelegateFactory {

	/**
	 * Builds the required ElevatorDecisionDelegate
	 * 
	 * @return An ElevatorDecisionDelegate
	 */
	public static ElevatorDecisionDelegate build() {

		return new ElevatorDecisionDelegateImpl();

	}

}
