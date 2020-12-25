package com.example.irm.error;

public class IrmNotFoundException extends Exception
{
	private static final long serialVersionUID = 2L;

	public IrmNotFoundException()
	{
        super();
    }

    public IrmNotFoundException(String message)
    {
        super(message);
    }

    public IrmNotFoundException(Throwable cause)
    {
        super(cause);
    }
    
    public IrmNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
