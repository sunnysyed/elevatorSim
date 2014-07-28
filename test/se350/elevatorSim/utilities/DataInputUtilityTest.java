package se350.elevatorSim.utilities;

import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import se350.elevatorSim.expections.InvalidParameterException;

public class DataInputUtilityTest {

	@Test(expected = FileNotFoundException.class)
	public void InvliadFileFail() throws InvalidParameterException,
			FileNotFoundException {
		DataInputUtility.getBuildingInfoFromFile("invalid_name.txt");
	}

	// Should throw exception because elevator number is zero
	@Test(expected = InvalidParameterException.class)
	public void ZeroElevatorCountTest() throws InvalidParameterException,
			FileNotFoundException {
		DataInputUtility.getBuildingInfoFromFile("input_error1.txt");

	}

	// Should throw exception because deafult floor is not a real floor
	@Test(expected = InvalidParameterException.class)
	public void InvlalidDefaultFloorTest() throws InvalidParameterException,
			FileNotFoundException {
		DataInputUtility.getBuildingInfoFromFile("input_error2.txt");

	}

	// Should throw exception because number of persons per minute must be
	// greater than zero
	@Test(expected = InvalidParameterException.class)
	public void InvalidPersosnPerMinuteTest() throws InvalidParameterException,
			FileNotFoundException {
		DataInputUtility.getBuildingInfoFromFile("input_error3.txt");

	}

	// Should throw exception because number of persons per minute must be
	// greater than zero
	@Test
	public void ValidDataTest() {
		try {
			DataInputUtility.getBuildingInfoFromFile("input.txt");
		} catch (InvalidParameterException e) {
			fail("Should have loaded correctly");
		} catch (FileNotFoundException e) {
			fail("Should have found the file");
		}
	}

}
