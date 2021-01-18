package com.example.irm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends IrmException
{
	private static final long serialVersionUID = 2L;

	public AlreadyExistsException()
	{
        super();
    }

    public AlreadyExistsException(String message)
    {
        super(message);
    }

    public AlreadyExistsException(Throwable cause)
    {
        super(cause);
    }
    
    public AlreadyExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
