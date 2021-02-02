package com.example.irm.view;

public class OrganizationUI 
{	
	private String entityId;
	private String displayName;
    private String username;
    private String email;
    private String password;
    private String type;
    
	protected OrganizationUI()
    {
	}
 
	public OrganizationUI(String entityId, String displayName, String email, String username, String type)
	{
		this.entityId = entityId;
		this.displayName = displayName;
		this.email = email;
		this.username = username;
		this.type = type;
	}
	
	public String getEntityId()
	{
		return entityId;
	}
	
	public void setEntityId(String entityId)
	{
		this.entityId = entityId;
	}
	
	public String getDisplayName()
	{
        return displayName;
    }
	
    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }
	
	public String getUsername()
	{
        return username;
    }
	
    public void setUsername(String username)
    {
        this.username = username;
    }    
    
	public String getPassword()
	{
        return password;
    }
	
    public void setPassword(String password)
    {
        this.password = password;
    }    

	public String getEmail()
	{
        return email;
    }
	
    public void setEmail(String email)
    {
        this.email = email;
    }       

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}