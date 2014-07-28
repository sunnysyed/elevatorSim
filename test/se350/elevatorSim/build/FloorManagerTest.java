package se350.elevatorSim.build;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se350.elevatorSim.expections.InvalidParameterException;
import se350.elevatorSim.person.PersonGenerator;

public class FloorManagerTest {

	// Tests using FloorManager before initialization, should throw exception
	@Test(expected = IllegalStateException.class)
	public void testInitializeFail() throws IllegalStateException {
		FloorManager.getInstance();
	}

	// Tests initializing the FloorManager
	@Test
	public void testInitializeSuccess() {
		FloorManager.initialize(15);
	}

	// Tests the the FloorManager has the number of floors it was initialized
	// with
	@Test
	public void testNumberOfFloorsSuccess() {
		try {
			FloorManager.initialize(15);
		} catch (IllegalStateException e) {
			// pass
		}
		assertEquals(FloorManager.getInstance().getNumberOfFloors(), 15);
	}

	// Tests the the FloorManager has the number of floors it was initialized
	// with
	@Test(expected = InvalidParameterException.class)
	public void testAddToIllegalFloor() throws InvalidParameterException {
		try {
			FloorManager.initialize(15);
		} catch (IllegalStateException e) {
			// pass
		}
		FloorManager.getInstance().addPersonToFloor(16,
				PersonGenerator.build(1, 2, 1));
	}

}
