package se350.elevatorSim.elevatorcontroller;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import se350.elevatorSim.elevator.Elevator;
import se350.elevatorSim.elevator.ElevatorDTO;

public class ElevatorDecisionDelegateTest {

	static ElevatorDTO[] dtos;
	static ElevatorDecisionDelegate delegate;

	@BeforeClass
	public static void setup() {
		delegate = new ElevatorDecisionDelegateImpl();
		dtos = new ElevatorDTO[4];

		dtos[0] = new ElevatorDTO();
		dtos[0].currentFloor = 1;
		dtos[0].status = Elevator.Status.WAITING_DEFAULT;
		dtos[0].remainingDestinations = "[]";

		dtos[1] = new ElevatorDTO();
		dtos[1].currentFloor = 6;
		dtos[1].status = Elevator.Status.MOVING_DOWN;
		dtos[1].remainingDestinations = "[]";

		dtos[2] = new ElevatorDTO();
		dtos[2].currentFloor = 8;
		dtos[2].status = Elevator.Status.MOVING_UP;
		dtos[2].remainingDestinations = "[]";

		dtos[3] = new ElevatorDTO();
		dtos[3].currentFloor = 10;
		dtos[3].status = Elevator.Status.WAITING;
		dtos[3].remainingDestinations = "[]";
	}

	@Test
	public void testFloorOneRequestWithElevatorThere() {
		assertEquals(1,
				delegate.getBestElevator(ElevatorController.UP, 1, dtos));
	}

	@Test
	public void testAddDownToMovingDown() {
		assertEquals(2,
				delegate.getBestElevator(ElevatorController.DOWN, 5, dtos));
	}

	@Test
	public void testAddUpToMovingUp() {
		assertEquals(3,
				delegate.getBestElevator(ElevatorController.UP, 9, dtos));
	}

	@Test
	public void testInTheMiddle() {
		assertEquals(4,
				delegate.getBestElevator(ElevatorController.DOWN, 7, dtos));
	}

}
