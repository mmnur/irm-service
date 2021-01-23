package com.example.irm.view;

public class IamServiceUI 
{	
    private String displayName;
	private String clientId;
	private String clientSecret;
    
    protected IamServiceUI()
    {
	}
 
	public IamServiceUI(String displayName, String clientId, String clientSecret)
	{
		this.displayName = displayName;
		this.clientId = clientId;
		this.clientSecret = clientSecret;				
	}
	
	public String getDisplayName()
	{
        return displayName;
    }
	
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
    
	public String getClientId()
	{
        return clientId;
    }
	
    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }    
    
	public String getClientSecret()
	{
        return clientSecret;
    }
	
    public void setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
    }    
    
	@Override
	public String toString()
	{
		return String.format("User[displayName='%s', clientId=%s, email=%d]", displayName, clientId, clientSecret);
	}
}