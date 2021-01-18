package com.example.irm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
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
