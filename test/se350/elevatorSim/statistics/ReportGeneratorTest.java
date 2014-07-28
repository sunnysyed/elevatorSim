package se350.elevatorSim.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import se350.elevatorSim.person.PersonResults;

public class ReportGeneratorTest {

	@BeforeClass
	public static void initialize() {
		ReportGenerator.initialize(15);

		PersonResults data1 = new PersonResults();
		data1.id = 1;
		data1.startingFloor = 1;
		data1.endingFloor = 10;
		data1.waitTime = 1;
		data1.rideTime = 12;

		PersonResults data2 = new PersonResults();
		data2.id = 2;
		data2.startingFloor = 1;
		data2.endingFloor = 5;
		data2.waitTime = 3;
		data2.rideTime = 7;

		PersonResults data3 = new PersonResults();
		data3.id = 3;
		data3.startingFloor = 7;
		data3.endingFloor = 3;
		data3.waitTime = 10;
		data3.rideTime = 8;

		PersonResults data4 = new PersonResults();
		data4.id = 4;
		data4.startingFloor = 1;
		data4.endingFloor = 5;
		data4.waitTime = 4;
		data4.rideTime = 9;

		ReportGenerator.getInstance().addReport(data1);
		ReportGenerator.getInstance().addReport(data2);
		ReportGenerator.getInstance().addReport(data3);
		ReportGenerator.getInstance().addReport(data4);
	}

	@Test
	public void testAverageWaitMoreThanOne() {
		assertEquals(2, ReportGenerator.getInstance()
				.getAverageFloorWaitTime(1));
	}

	@Test
	public void testAverageWaitSingle() {
		assertEquals(10,
				ReportGenerator.getInstance().getAverageFloorWaitTime(7));
	}

	@Test
	public void testAverageWaitNoData() {
		assertEquals(-1,
				ReportGenerator.getInstance().getAverageFloorWaitTime(2));
	}

	@Test
	public void testAverageRideTimeMoreThanOne() {
		assertEquals(8, ReportGenerator.getInstance().getRideTime(1, 5));
	}

	@Test
	public void testAverageRideTimeSingle() {
		assertEquals(8, ReportGenerator.getInstance().getRideTime(7, 3));
	}

	@Test
	public void testAverageRideTimeNoData() {
		assertEquals(-1, ReportGenerator.getInstance().getRideTime(8, 9));
	}

	@Test(expected = IllegalStateException.class)
	public void testBadIdEntered() {
		PersonResults baddata1 = new PersonResults();
		baddata1.id = 0;
		baddata1.startingFloor = 7;
		baddata1.endingFloor = 3;
		baddata1.waitTime = 10;
		baddata1.rideTime = 8;

		ReportGenerator.getInstance().addReport(baddata1);
	}

	@Test(expected = IllegalStateException.class)
	public void testBadFloorEntered() {
		PersonResults baddata1 = new PersonResults();
		baddata1.id = 5;
		baddata1.startingFloor = 20;
		baddata1.endingFloor = 3;
		baddata1.waitTime = 10;
		baddata1.rideTime = 8;

		ReportGenerator.getInstance().addReport(baddata1);
	}

}
