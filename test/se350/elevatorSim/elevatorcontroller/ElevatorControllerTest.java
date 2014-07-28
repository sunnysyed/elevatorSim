package se350.elevatorSim.elevatorcontroller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import se350.elevatorSim.build.BuildingDTO;
import se350.elevatorSim.build.FloorManager;
import se350.elevatorSim.expections.InvalidParameterException;

public class ElevatorControllerTest {

	@BeforeClass
	public static void setup() {
		FloorManager.initialize(5);
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailInitialization() throws InvalidParameterException {
		ElevatorController.initialize(new BuildingDTO(1, 1, 2, 0, 12, 1000,
				2200, new int[] { 0 }, 0, new int[] { 100, 0 }));
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailInitializationBadDefaultFloor()
			throws InvalidParameterException {
		ElevatorController.initialize(new BuildingDTO(1, 1, 2, 1, 12, 1000,
				2200, new int[] { 0 }, 0, new int[] { 100, 0 }));
	}

	@Test(expected = InvalidParameterException.class)
	public void testFailInitializationBadMaxCapacity()
			throws InvalidParameterException {
		ElevatorController.initialize(new BuildingDTO(1, 1, 2, 1, 0, 1000,
				2200, new int[] { 1 }, 0, new int[] { 100, 0 }));
	}

	@Test
	public void testInitializationPass() {
		try {
			ElevatorController.initialize(new BuildingDTO(1, 1, 5, 1, 12, 1000,
					2200, new int[] { 1 }, 0, new int[] { 100, 0, 0, 0, 0 }));
		} catch (Exception e) {
			fail("Elevator Controller should have initialized");
		}
	}

	@Test
	public void testReturnsCorrectNumberOfElevatorDTOs()
			throws InvalidParameterException {
		ElevatorController.initialize(new BuildingDTO(1, 1, 5, 1, 12, 1000,
				2200, new int[] { 1 }, 0, new int[] { 100, 0, 0, 0, 0 }));
		assertEquals(1,
				ElevatorController.getInstance().getElevatorData().length);
	}

	@Test(expected = InvalidParameterException.class)
	public void testSendRequestToBadElevator() throws InvalidParameterException {
		ElevatorController.getInstance().sendRequestToElevator(0, 3);
	}

	@Test(expected = InvalidParameterException.class)
	public void testSendRequestToBadFloor() throws InvalidParameterException {
		ElevatorController.getInstance().sendRequestToElevator(1, 6);
	}

	@Test
	public void testSendRequestSuccess() throws InvalidParameterException {
		ElevatorController.getInstance().sendRequestToElevator(1, 3);
	}
}
