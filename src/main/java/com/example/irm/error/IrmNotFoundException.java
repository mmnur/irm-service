package com.example.irm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IrmNotFoundException extends IrmException
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
