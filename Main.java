import java.util.*;
//import java.io.*;

public class Main {
    double execTime;
    Thread [] threads;
    int numberOfGuests;

    int counter = 0;
    volatile boolean hasCupcake = true;
    volatile boolean allGuestsHaveGone = false;

    Main()
    {
        execTime = 0;
        numberOfGuests = 8;
        threads = new Thread [8];
    }

    Main(int n)
    {
        execTime = 0;
        numberOfGuests = n;
        threads = new Thread [n];
    }

    public class MazeRunnable implements Runnable
    {
        static int N;
        boolean isLeader;
    
        MazeRunnable(int n, boolean isLeader)
        {
            N = n;
            this.isLeader = isLeader;
        }
    
        @Override
        public void run()
        {
            synchronized(this)
            {
                
                System.out.println((isLeader ? "Leader" : "Guest ")  + " | threadID - " + Thread.currentThread().getId() + " | Counted people: " + counter + " | Has Cupcake: " + hasCupcake);
                
                // Labyrinth stuff happens here
                // But for the sake of performance let's skip that part

                if (isLeader)
                {
                    if (hasCupcake)
                    {
                        // Leave cupcake alone
                    }
                    else
                    {
                        // Ask for another cupcake
                        hasCupcake = true;

                        counter++;

                        // If leader has accounted for all guests
                        // other than himself/herself
                        if (counter == N - 1)
                        {
                            allGuestsHaveGone = true;
                        }
                    }
                }
                else
                {
                    if (hasCupcake)
                    {
                        // Eat cupcake
                        hasCupcake = false;
                    }
                    else
                    {
                        // Do not ask for another cupcake
                    }
                }
            }
        }
    }
    

    // Begins the prime accounting process
    public void runProblem1()
    {
        long startTime = System.nanoTime();
        Random rand = new Random((long)Math.random());
        MazeRunnable leaderRunnable = new MazeRunnable(numberOfGuests, true);
        MazeRunnable guestRunnable = new MazeRunnable(numberOfGuests, false);

        while (!allGuestsHaveGone)
        {
            // Randomly selected guest
            int index = rand.nextInt(threads.length);
            
            // index 0 is the designated leader
            if (index == 0)
            {
                threads[index] = new Thread(leaderRunnable);
            }
            else
            {
                threads[index] = new Thread(guestRunnable);
            }

            threads[index].start();

            try
            {
                threads[index].join();
            }
            catch (InterruptedException e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }

        execTime = (System.nanoTime() - startTime) / 1000000.0;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int input = 8;

        System.out.println("Part 1");
        System.out.println("-------------------------------------------------");
        System.out.println("How many guests?");

        boolean getInput = true;

        while (getInput)
        {
            try
            {
                getInput = false;
                input = scanner.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println(e.getMessage());
                System.out.println("Incorrect type, please input an integer number of guests");
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
        
        scanner.close();

        Main assignment2 = new Main(input);
        assignment2.runProblem1();
        
    }
}