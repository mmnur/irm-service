package com.example.irm.view;

public class RelationshipUI 
{	
	private String entitySrc;
	private String entityTgt;
	private String relationTypeS2T;
	private String relationTypeT2S;
    
    protected RelationshipUI()
    {
	}
 
	public RelationshipUI(String entitySrc, String entityTgt, String relationTypeS2T, String relationTypeT2S)
	{
		this.setEntitySrc(entitySrc);
		this.setEntityTgt(entityTgt);
		this.setRelationTypeS2T(relationTypeS2T);
		this.setRelationTypeT2S(relationTypeT2S);
	}
	

	public String toString() {
		return String.format("Relationship[entitySrc=%s, entityTgt='%s', relationTypeS2T=%s, relationTypeT2S=%d]", entitySrc, entityTgt, relationTypeS2T, relationTypeT2S);
	}

	public String getEntitySrc() {
		return entitySrc;
	}

	public void setEntitySrc(String entitySrc) {
		this.entitySrc = entitySrc;
	}

	public String getEntityTgt() {
		return entityTgt;
	}

	public void setEntityTgt(String entityTgt) {
		this.entityTgt = entityTgt;
	}

	public String getRelationTypeS2T() {
		return relationTypeS2T;
	}

	public void setRelationTypeS2T(String relationTypeS2T) {
		this.relationTypeS2T = relationTypeS2T;
	}

	public String getRelationTypeT2S() {
		return relationTypeT2S;
	}

	public void setRelationTypeT2S(String relationTypeT2S) {
		this.relationTypeT2S = relationTypeT2S;
	}
}