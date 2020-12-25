package com.example.irm.model;

import java.io.Serializable;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "Devices")
public class Device implements Serializable
{ 
	private static final long serialVersionUID = -2343243243242432343L;

    @Id
    @GeneratedValue(generator = "custom-generator", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "custom-generator", strategy = "com.example.irm.utils.DeviceEntityIdGenerator")
    protected String entityId;
    
	@Column(name = "DisplayName")
	private String displayName;
 
	@Column(name = "OwnerEID")
	private String ownerEID;

	@Column(name = "PublicKey", length = 500)
	//@Column(name = "PublicKey", columnDefinition="text")
	private String publicKey;

	@Column(name = "Challenge")
	private String challenge;

	@Column(name = "RegisterationCompleted")
	private Boolean registerationCompleted;

	@Column(name = "Scopes", length = 1000)
	private String scopes;

	protected Device()
	{
	}

	public Device(String displayName, String ownerEID, String publicKey, String scopes)
	{
		this.displayName = displayName;
		this.ownerEID = ownerEID;
		this.publicKey = publicKey;
		this.scopes = scopes;		
		this.challenge = "chlng-" + java.util.UUID.randomUUID().toString() + String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());
		this.registerationCompleted = false;
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
    
	public String getOwnerEID()
	{
        return ownerEID;
    }
	
    public void setOwnerEID(String ownerEID)
    {
        this.ownerEID = ownerEID;
    }
    
	public String getPublicKey()
	{
        return publicKey;
    }
	
    public void setPublicKey(String publicKey)
    {
        this.publicKey = publicKey;
    }    
    
	public String getChallenge()
	{
        return challenge;
    }
	
    public void setChallenge(String challenge)
    {
        this.challenge = challenge;
    }    

	public Boolean getRegisterationCompleted()
	{
        return registerationCompleted;
    }
	
    public void setRegisterationCompleted(Boolean registerationCompleted)
    {
        this.registerationCompleted = registerationCompleted;
    }
    
	public String getScopes()
	{
        return scopes;
    }
	
    public void setScopes(String scopes)
    {
        this.scopes = scopes;
    }
    
	@Override
	public String toString()
	{
		return String.format("Device[entityId=%s, displayName='%s', ownerEID=%s, publicKey=%s, scopes=%s, registerationCompleted=%s]",
				entityId, displayName, ownerEID, publicKey, scopes, String.valueOf(registerationCompleted));
	}
}