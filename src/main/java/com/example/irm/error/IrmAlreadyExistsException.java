package com.example.irm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IrmAlreadyExistsException extends IrmException
{
	private static final long serialVersionUID = 2L;

	public IrmAlreadyExistsException()
	{
        super();
    }

    public IrmAlreadyExistsException(String message)
    {
        super(message);
    }

    public IrmAlreadyExistsException(Throwable cause)
    {
        super(cause);
    }
    
    public IrmAlreadyExistsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
