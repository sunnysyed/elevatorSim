package se350.elevatorSim.expections;

/**
 * This is a custom exception class that is to be thrown when a parameter to a
 * method is invalid
 * 
 * @author Shahzaib Syed
 * 
 */

@SuppressWarnings("serial")
public class InvalidParameterException extends Exception {

	public InvalidParameterException(String s) {
		super(s);
	}

}
