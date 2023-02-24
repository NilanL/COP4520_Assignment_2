import java.util.*;

public class Problem1 
{
    Thread [] threads;
    MazeRunnable [] runnables;
    double execTime;
    int numberOfGuests;

    volatile int counter = 0;
    volatile boolean hasCupcake = true;
    volatile boolean allGuestsHaveGone = false;
    volatile boolean change = false;

    Problem1()
    {
        execTime = 0;
        numberOfGuests = 8;
        threads = new Thread [8];
        runnables = new MazeRunnable [8];
    }

    Problem1(int n)
    {
        execTime = 0;
        numberOfGuests = n;
        threads = new Thread [n];
        runnables = new MazeRunnable [n];
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

        public void wake()
        {
            synchronized(this)
            {       
                this.notify();
            }
        }

        @Override
        public void run()
        {
        
                while (true)
                { 
                    synchronized(this)
                    {       
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

                                break;
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
                    
                    try 
                    {
                        this.wait();
                    } 
                    catch (InterruptedException e) 
                    {
                        e.printStackTrace();
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
                if (threads[index] == null)
                    threads[index] = new Thread(leaderRunnable);
            }
            else
            {
                if (threads[index] == null)
                {
                    runnables[index] = new MazeRunnable(index + 1, false);

                    threads[index] = new Thread(runnables[index]);
                }
            }

            if (threads[index].getState().equals(Thread.State.NEW))
            {
                threads[index].start();
            }
            else
            {
                if (index == 0)
                {
                    leaderRunnable.wake();
                }
                else
                {
                    runnables[index].wake();
                }
            }
        }

        execTime = (System.nanoTime() - startTime) / 1000000.0;
    }
}
