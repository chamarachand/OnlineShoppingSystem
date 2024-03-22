import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    public static int getIntInput(String prompt, int minValue) {
        Scanner input = new Scanner(System.in);

        boolean isValid = false;
        int userInput = 0;

        while(!isValid) {
            try {
                System.out.print(prompt);
                userInput = input.nextInt();

                if (userInput >= minValue) isValid = true;
                else System.out.println("This field cannot less than " + minValue);

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                input.nextLine();
            }
        }
        return userInput;
    }

    public static double getDoubleInput(String prompt, int minValue) {
        Scanner input = new Scanner(System.in);

        boolean isValid = false;
        double userInput = 0.0;

        while(!isValid) {
            try {
                System.out.print(prompt);
                userInput = input.nextDouble();

                if (userInput >= minValue) isValid = true;
                else System.out.println("This field cannot be less than " + minValue);

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                input.nextLine();
            }
        }
        return userInput;
    }

    public static String getStringInput(String prompt) {
        Scanner input = new Scanner(System.in);

        boolean isValid = false;
        String userInput = null;

        while (!isValid) {
            System.out.print(prompt);
            userInput = input.nextLine().trim();

            if (userInput.isEmpty())
                System.out.println("This field cannot be empty!");
            else
                isValid = true;
        }
        return userInput;
    }

    public static String getStringInput(String prompt, String...values) {
        ArrayList<String> allowedValues = new ArrayList<>(Arrays.asList(values));
        boolean isValid = false;
        String userInput = null;

        while (!isValid) {
            userInput = getStringInput(prompt).toUpperCase();
            if (allowedValues.contains(userInput)) isValid = true;
            else
                System.out.println("Incorrect Input!");
        }
        return userInput;
    }

    public static int getConsoleMenuInput(int lowerBound, int upperBound) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Enter option: ");

            try {
                int userInput = input.nextInt();
                if (userInput >= lowerBound && userInput <= upperBound)
                    return userInput;
                else
                    System.out.println("Please enter a number between " + lowerBound + " and " + upperBound);
            } catch (Exception e) {
                System.out.println("Incorrect Input!");
                input.nextLine();
            }
        }
    }
}
