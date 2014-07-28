package se350.elevatorSim.person;

import org.junit.Test;

import se350.elevatorSim.build.FloorManager;
import se350.elevatorSim.expections.InvalidParameterException;

public class PersonImplTest {

	@Test
	// valid test case
	public void test1() throws InvalidParameterException {
		FloorManager.initialize(16);
		PersonImpl pi = new PersonImpl(1, 2, 10);
		pi.setAddedToElevatorTime();

	}

	// invalid floor number
	@Test(expected = InvalidParameterException.class)
	public void test2() throws InvalidParameterException {
		PersonImpl pi = new PersonImpl(1, 2, 20);
		pi.setAddedToElevatorTime();
	}

}
