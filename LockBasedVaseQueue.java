import java.util.concurrent.locks.*;

public class LockBasedVaseQueue
{
    int head, tail;
    Thread[] guests;
    int lastGuestNumber;
    Lock lock;

    public LockBasedVaseQueue(int capacity) {
        head = 0; tail = 0;
        lock = new ReentrantLock();
        guests = new Thread[capacity];
        lastGuestNumber = -1;
    }

    public void enq(Thread guest) throws FullQueueException 
    {
        lock.lock();
        try 
        {
            if (tail - head == guests.length)
                throw new FullQueueException(null, null);

            guests[tail % guests.length] = guest;
            tail++;
        } 
        finally 
        {
            lock.unlock();
        }
    }
    public Thread deq() throws EmptyQueueException 
    {
        lock.lock();
        try 
        {
            if (tail == head)
                throw new EmptyQueueException(null, null);
            lastGuestNumber = head % guests.length;
            Thread x = guests[lastGuestNumber];
            head++;

            return x;
        } 
        finally 
        {
            lock.unlock();
        }
    }

    public boolean isEmpty()
    {
        if (tail == head)
        {
            return true;
        }

        return false;
    }

    public int getLastGuestNumber()
    {
        return lastGuestNumber;
    }
}