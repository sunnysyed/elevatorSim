package se350.elevatorSim.build;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Test;

import se350.elevatorSim.elevatorcontroller.ElevatorController;
import se350.elevatorSim.expections.InvalidParameterException;

public class BuildingTest {

	@After
	public void destroyFloorManager() {
		FloorManager.destroy();
	}

	// Tests making a building with valid data, should pass
	@Test
	public void testBuildingCompletionPass() {
		try {
			Building b = new Building("input_testing.txt", true);
		} catch (InvalidParameterException e) {
			fail("Building should have been completed");
		} catch (FileNotFoundException e) {
			fail("Building should have been completed");
		}
	}

	// Tests sending request to a bad elevator id, should throw exception
	@Test(expected = InvalidParameterException.class)
	public void testSendingRequestElevatorArgumentFail()
			throws InvalidParameterException, FileNotFoundException {
		Building b = new Building("input_testing.txt", true);
		ElevatorController.getInstance().sendRequestToElevator(-1, 5);
	}

	// Tests sending request to non-existant floor, should throw exception
	@Test(expected = InvalidParameterException.class)
	public void testSendingRequestFloorArgumentFail()
			throws InvalidParameterException, FileNotFoundException {
		Building b = new Building("input_testing.txt", true);
		ElevatorController.getInstance().sendRequestToElevator(3, 0);
	}

	// Tests sending request with valid data, should pass
	@Test
	public void testSendingRequestPass() {
		try {
			Building b = new Building("input_testing.txt", true);
			ElevatorController.getInstance().sendRequestToElevator(3, 10);
		} catch (InvalidParameterException e) {
			fail("InvalidParameterException should not have been thrown");
		} catch (FileNotFoundException e) {
			fail("File should have been found");
		}
	}

}
