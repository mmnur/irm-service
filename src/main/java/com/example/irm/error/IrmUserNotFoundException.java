package com.example.irm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IrmUserNotFoundException extends IrmException
{
	private static final long serialVersionUID = 3L;

	public IrmUserNotFoundException()
	{
        super();
    }

    public IrmUserNotFoundException(String message)
    {
        super(message);
    }

    public IrmUserNotFoundException(Throwable cause)
    {
        super(cause);
    }
    
    public IrmUserNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
