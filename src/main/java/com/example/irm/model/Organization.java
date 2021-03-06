package com.example.irm.model;

import java.io.Serializable;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Organizations")
public class Organization implements Serializable
{ 
	private static final long serialVersionUID = -2343243243242432341L;

    @Id
    @GeneratedValue(generator = "custom-generator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "custom-generator", strategy = "com.example.irm.utils.OrganizationEntityIdGenerator")
    protected String entityId;
    
	@Column(name = "DisplayName")
	private String displayName;
 
	@Column(name = "Username")
	private String username;

	@Column(name = "Password")
	private String password;

	@Column(name = "Email")
	private String email;

	@Column(name = "Type")
	private String type;

	protected Organization()
	{
	}
 
	public Organization(String displayName, String email, String username, String password, String type)
	{
		this.displayName = displayName;
		this.email = email;
		this.username = username;
		this.password = password;	
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