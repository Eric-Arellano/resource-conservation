package userInterfaces;

import java.util.InputMismatchException;
import java.util.Scanner;

class UserInputHelper {

	private final static Scanner scanner = new Scanner(System.in);

	// ================================================================================
	// Input utilities
	// ================================================================================

	static int getValidIntInput(int RANGE_LOWER_BOUND, int RANGE_UPPER_BOUND) {
		int inputtedValue;
		try {
			if (scanner.hasNextInt()) {
				inputtedValue = scanner.nextInt();
				if (isNotValidRange(inputtedValue, RANGE_LOWER_BOUND, RANGE_UPPER_BOUND)) {
					throw new NumberFormatException("Out of range.");
				}
			} else {
				scanner.next();
				throw new InputMismatchException("Not int.");
			}
		} catch (NumberFormatException | InputMismatchException outOfRangeException) {
			System.out.println(returnOutOfBoundsMessage(RANGE_LOWER_BOUND, RANGE_UPPER_BOUND));
			inputtedValue = getValidIntInput(RANGE_LOWER_BOUND, RANGE_UPPER_BOUND);
		}
		return inputtedValue;
	}

	static double getValidDoubleInput(double RANGE_LOWER_BOUND) {
		double inputtedValue;
		try {
			if (scanner.hasNextDouble()) {
				inputtedValue = scanner.nextDouble();
				if (isNotValidRange(inputtedValue, RANGE_LOWER_BOUND)) {
					throw new NumberFormatException("Out of range.");
				}
			} else {
				scanner.next();
				throw new InputMismatchException("Not double.");
			}
		} catch (NumberFormatException | InputMismatchException outOfRangeException) {
			System.out.println(returnOutOfBoundsMessage(RANGE_LOWER_BOUND));
			inputtedValue = getValidDoubleInput(RANGE_LOWER_BOUND);
		}
		return inputtedValue;
	}


	// ================================================================================
	// Error handling support
	// ================================================================================

	private static String returnOutOfBoundsMessage(double lowerBound) {
		return "Oops! Please enter a number greater than " + lowerBound + ".";
	}

	private static String returnOutOfBoundsMessage(int lowerBound, int upperBound) {
		return "Oops! Please enter an integer between " + lowerBound + "-" + upperBound + ".";
	}

	private static boolean isNotValidRange(int input, int lowerBound, int upperBound) {
		return input < lowerBound || input > upperBound;
	}

	private static boolean isNotValidRange(double input, double lowerBound) {
		return input <= lowerBound;
	}
}
