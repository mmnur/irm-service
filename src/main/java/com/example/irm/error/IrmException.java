package com.example.irm.error;

public class IrmException extends Exception
{
	private static final long serialVersionUID = 1L;

	public IrmException()
	{
        super();
    }

    public IrmException(String message)
    {
        super(message);
    }

    public IrmException(Throwable cause)
    {
        super(cause);
    }
    
    public IrmException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
