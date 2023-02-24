public class EmptyQueueException extends RuntimeException 
{
  public EmptyQueueException(String errorMessage, Throwable e) 
  {
    super(errorMessage, e);
  }
}
