package se350.elevatorSim.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import se350.elevatorSim.build.BuildingDTO;
import se350.elevatorSim.build.FloorManager;
import se350.elevatorSim.elevatorcontroller.ElevatorController;
import se350.elevatorSim.expections.InvalidParameterException;

public class PersonGeneratorTest {

	// Invalid floor stats
	@Test(expected = IllegalStateException.class)
	public void test1() throws InvalidParameterException {
		BuildingDTO stats = new BuildingDTO(1, 1, 15, 4, 12, 1000, 2200,
				new int[] { 7, 1, 1, 1 }, 0, new int[] { 100, 0, 0, 0, 0, 0, 0,
						0, 0, 0, 0, 0, 0, 20, 10 });
		PersonGenerator.initialize(stats);
	}

	@Test
	// valid test case where people are generated
	public void test2() throws InvalidParameterException {
		BuildingDTO stats = new BuildingDTO(1, 1, 14, 4, 10, 500, 500,
				new int[] { 1, 2, 3, 4, }, 50000, new int[] { 15, 5, 5, 5, 5,
						5, 10, 5, 5, 5, 5, 5, 5, 20 });
		PersonGenerator.initialize(stats);
		FloorManager.initialize(14);
		ElevatorController.initialize(stats);
		PersonGenerator pg = PersonGenerator.getInstance();
		assertTrue(pg.generateAndAddPerson() != 0);

	}
}
