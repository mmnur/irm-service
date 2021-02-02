package com.example.irm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Clients")
public class Client implements Serializable
{ 
	private static final long serialVersionUID = -2343243275742432342L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;

	@Column(name = "DisplayName")
	private String displayName;
 
	@Column(name = "ClientId")
	private String clientId;

	@Column(name = "ClientSecret")
	private String clientSecret;

	protected Client()
	{
	}
	
	public Client(String displayName, String clientId, String clientSecret)
	{
		this.displayName = displayName;
		this.clientId = clientId;
		this.clientSecret = clientSecret;				
	}
	
	public String getId()
	{
		return id;
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
		return String.format("User[id=%s, displayName='%s', clientId=%s, email=%d]", id, displayName, clientId, clientSecret);
	}
}