import java.util.*;

public class Problem1 
{
    Thread [] threads;
    double execTime;
    int numberOfGuests;

    volatile int counter = 0;
    volatile boolean hasCupcake = true;
    volatile boolean allGuestsHaveGone = false;

    Problem1()
    {
        execTime = 0;
        numberOfGuests = 8;
        threads = new Thread [8];
    }

    Problem1(int n)
    {
        execTime = 0;
        numberOfGuests = n;
        threads = new Thread [n];
    }

    public double getExecutionTime()
    {
        return execTime;
    }

    public class MazeRunnable implements Runnable
    {
        int guestNumber;
        boolean isLeader;

        MazeRunnable(int n, boolean isLeader)
        {
            guestNumber = n;
            this.isLeader = isLeader;
        }

        @Override
        public void run()
        {
            synchronized(this)
            {
                
                //System.out.println((isLeader ? "Leader" : "Guest ")  + " | threadID - " + Thread.currentThread().getId() + " | Counted people: " + counter + " | Has Cupcake: " + hasCupcake);
                
                // Labyrinth stuff happens here
                // But for the sake of performance let's skip that part
                if (isLeader)
                {
                    if (hasCupcake)
                    {
                        System.out.println("Guest #" + guestNumber + " has gone through the maze! | LEADER | " + " found a cupcake  | " + counter + " guests counted");

                        // Leave cupcake alone
                    }
                    else
                    {
                        // Ask for another cupcake
                        hasCupcake = true;

                        counter++;

                        System.out.println("Guest #" + guestNumber + " has gone through the maze! | LEADER | " + " found no cupcake | " + counter + " guests counted");

                        // If leader has accounted for all guests
                        // other than himself/herself
                        if (counter == numberOfGuests - 1)
                        {
                            allGuestsHaveGone = true;
                            System.out.println("Guest #" + guestNumber + " says ALL GUESTS HAVE GONE");
                        }
                    }
                }
                else
                {
                    if (hasCupcake)
                    {
                        // Eat cupcake
                        hasCupcake = false;

                        System.out.println("Guest #" + guestNumber + " has gone through the maze! | normal | " + " found a cupcake");
                    }
                    else
                    {
                        // Do not ask for another cupcake
                        System.out.println("Guest #" + guestNumber + " has gone through the maze! | normal | " + " found no cupcake");
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
        MazeRunnable leaderRunnable = new MazeRunnable(1, true);

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
                threads[index] = new Thread(new MazeRunnable(index, false));
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
}
