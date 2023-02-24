public class FullQueueException extends RuntimeException 
{
  public FullQueueException(String errorMessage, Throwable e) 
  {
    super(errorMessage, e);
  }
}

