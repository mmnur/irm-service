package com.example.irm.view;

public class DeviceUI
{ 
    private String entityId;
	private String displayName;
	private String ownerEID;
	private String publicKey;
	private String challenge;
	private String challengeResponse;
	private Boolean registerationCompleted;
	private String scopes;

	protected DeviceUI()
	{
	}
 
	public DeviceUI(String displayName, String ownerEID, String publicKey, String challenge, Boolean registerationCompleted, String scopes)
	{
		this.displayName = displayName;
		this.ownerEID = ownerEID;
		this.publicKey = publicKey;
		this.challenge = challenge;
		this.registerationCompleted = registerationCompleted;
		this.scopes = scopes;
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

	public String getChallengeResponse()
	{
        return challengeResponse;
    }
	
    public void setChallengeResponse(String challengeResponse)
    {
        this.challengeResponse = challengeResponse;
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