import java.util.*;

public class Main {
    public static void part1(Scanner scanner)
    {
        int input = 8;

        System.out.println("\nPart 1 (Cupcake problem)");
        System.out.println("-------------------------------------------------");
        System.out.println("How many guests?");

        boolean getInput = true;

        while (getInput)
        {
            try
            {
                getInput = false;
                input = scanner.nextInt();

                if (input < 0)
                {
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println(e.getMessage());
                System.out.println("Incorrect type, please input a positive integer number of guests");
                scanner.next();
                getInput = true;
            }
            catch (Exception e)
            {
                System.out.println("ERROR: " + e.getMessage());
                scanner.close();
                return;
            }
        }
        
        // Run operation
        Problem1 p1 = new Problem1(input);
        p1.runProblem1();

        System.out.println("-------------------------------------------------");
        System.out.println("EXECUTION TIME: " + p1.getExecutionTime() + "ms\n");
    }

    public static void part2(Scanner scanner)
    {
        int input = 8;

        System.out.println("\nPart 2 (Vase Problem)");
        System.out.println("-------------------------------------------------");
        System.out.println("How many guests?");

        boolean getInput = true;

        while (getInput)
        {
            try
            {
                getInput = false;
                input = scanner.nextInt();

                if (input < 0)
                {
                    throw new InputMismatchException();
                }
            }
            catch (InputMismatchException e)
            {
                System.out.println(e.getMessage());
                System.out.println("Incorrect type, please input a positive integer number of guests");
                scanner.next();
                getInput = true;
            }
            catch (Exception e)
            {
                System.out.println("ERROR: " + e.getMessage());
                scanner.close();
                return;
            }
        }

        // Run operation
        Problem2 p2 = new Problem2(input);
        p2.runProblem2();

        System.out.println("-------------------------------------------------");
        System.out.println("EXECUTION TIME: " + p2.getExecutionTime() + "ms\n");
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        part1(scanner);
        part2(scanner);

        scanner.close();
    }
}
