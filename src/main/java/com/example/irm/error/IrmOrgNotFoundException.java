package com.example.irm.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IrmOrgNotFoundException extends IrmException
{
	private static final long serialVersionUID = 4L;

	public IrmOrgNotFoundException()
	{
        super();
    }

    public IrmOrgNotFoundException(String message)
    {
        super(message);
    }

    public IrmOrgNotFoundException(Throwable cause)
    {
        super(cause);
    }
    
    public IrmOrgNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
