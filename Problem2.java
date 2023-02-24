import java.util.*;

public class Problem2 
{
    Thread [] threads;
    double execTime;
    int numberOfGuests;
    VaseRunnable [] guestRunnables;

    LockBasedVaseQueue vaseQueue;
    int counter = 0;
    volatile boolean hasCupcake = true;
    volatile boolean allGuestsHaveGone = false;

    Problem2()
    {
        execTime = 0;
        numberOfGuests = 8;
        threads = new Thread [8];
        vaseQueue = new LockBasedVaseQueue(8);
        guestRunnables = new VaseRunnable[8];
    }

    Problem2(int n)
    {
        execTime = 0;
        numberOfGuests = n;
        threads = new Thread [n];
        vaseQueue = new LockBasedVaseQueue(n);
        guestRunnables = new VaseRunnable[n];
    }

    public double getExecutionTime()
    {
        return execTime;
    }

    public class VaseRunnable implements Runnable
    {
        int guestNumber;

        VaseRunnable(int n)
        {
            guestNumber = n;
        }

        public int getGuestNumber()
        {
            return guestNumber;
        }

        @Override
        public void run()
        {
            synchronized(this)
            {
                // View vase
                System.out.println("Guest #" + (guestNumber + 1) + " has viewed the vase");
            }
        }
    }


    // Begins the prime accounting process
    public void runProblem2()
    {
        long startTime = System.nanoTime();
        Random rand = new Random((long)Math.random());

        for (int i = 0; i < numberOfGuests; i++)
        {            
            try
            {
                guestRunnables[i] = new VaseRunnable(i);
                vaseQueue.enq(new Thread(guestRunnables[i]));
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }

        int prevGuestNumber = -1;
        while (!vaseQueue.isEmpty())
        {
            try
            {
                // 75% chance of last guest liking the vase sooo much
                // that they re-enter the queue
                if (prevGuestNumber != -1 && rand.nextInt(4) < 3)
                {
                    vaseQueue.enq(new Thread(guestRunnables[prevGuestNumber]));
                }

                // Last guest let's next guest know that
                // the vase room is available
                Thread guest = vaseQueue.deq();
                prevGuestNumber = vaseQueue.getLastGuestNumber();

                // View the vase
                guest.start();

                try
                {
                    guest.join();
                }
                catch (InterruptedException e)
                {
                    System.out.println(e.getMessage());
                    return;
                }
            }
            catch (EmptyQueueException e)
            {
                break;
            }
            catch (FullQueueException e)
            {
                return;
            }
        }

        execTime = (System.nanoTime() - startTime) / 1000000.0;
    }
}
