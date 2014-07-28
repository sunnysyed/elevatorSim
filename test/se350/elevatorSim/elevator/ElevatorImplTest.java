package se350.elevatorSim.elevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

import se350.elevatorSim.build.Building;
import se350.elevatorSim.build.BuildingDTO;
import se350.elevatorSim.elevatorcontroller.ElevatorController;
import se350.elevatorSim.expections.InvalidParameterException;

public class ElevatorImplTest {

	static ElevatorController ec;

	@BeforeClass
	public static void setup() throws Exception {
		BuildingDTO stats = new BuildingDTO(1, 1, 15, 4, 12, 1000, 2200,
				new int[] { 7, 1, 1, 1 }, 0, new int[] { 100, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 0, 0 });

		Building b = new Building("input_testing.txt");

		ec = ElevatorController.getInstance();
	}

	// Tests adding a bad default floor using the constructor
	@Test(expected = InvalidParameterException.class)
	public void testElevatorDefaultFloorFail() throws InvalidParameterException {
		Elevator e = new ElevatorImpl(5, 30, 1000, 2200);
	}

	// Tests adding a bad default floor to the pending requests of an elevator
	@Test(expected = InvalidParameterException.class)
	public void testElevatorRefusesBadDataFailure()
			throws InvalidParameterException {
		ec.sendRequestToElevator(1, 20);
	}

	// Tests movement of the elevator, taking approximately one second per floor
	@Test
	public void testElevatorApprxOneSecondPerFloor() {
		try {

			Thread.sleep(100);
			ec.sendRequestToElevator(1, 12);
			Thread.sleep(13000);

			assertEquals(12, ec.getElevatorData()[0].currentFloor);

		} catch (InvalidParameterException | InterruptedException ex) {
			fail("Elevator should have gone to floor 12");
		}
	}

	// Tests ignoring of requests while moving in the opposite direction
	@Test
	public void testOppositeDirectionIgnored() {
		try {
			Thread.sleep(100);
			ec.sendRequestToElevator(2, 12);
			Thread.sleep(4000);
			ec.sendRequestToElevator(2, 2);

			assertEquals("[12]", ec.getElevatorData()[1].remainingDestinations);

		} catch (InvalidParameterException | InterruptedException ex) {
			fail("Elevator should have gone to floor 12");
		}
	}

	// Tests that the elevator times out and returns to default floor after some
	// time
	@Test
	public void testElevatorTimeout() {
		try {

			Thread.sleep(100);
			ec.sendRequestToElevator(3, 4);
			Thread.sleep(30000);

			assertEquals(1, ec.getElevatorData()[2].currentFloor);

		} catch (InvalidParameterException | InterruptedException ex) {
			fail("Elevator should have timed out, not had an invalid argument"
					+ "");
		}
	}

}
