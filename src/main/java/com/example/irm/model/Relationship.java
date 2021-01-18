package com.example.irm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.irm.utils.RelationType;

@Entity
@Table(name = "Relationships")
public class Relationship implements Serializable
{ 
	private static final long serialVersionUID = -2343243243242432346L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected String id;
	
	@Column(name = "EntitySource")
	private String entitySrc;
 
	@Column(name = "EntityTarget")
	private String entityTgt;

	@Column(name = "RelationTypeSource2Target")
	private RelationType relationTypeS2T;

	@Column(name = "RelationTypeTarget2Source")
	private RelationType relationTypeT2S;
	
	protected Relationship()
	{
	}
 
	public Relationship(String entitySrc, String entityTgt, String relationTypeS2T, String relationTypeT2S)
	{
		this(entitySrc, entityTgt, RelationType.valueOf(relationTypeS2T), RelationType.valueOf(relationTypeT2S));				
	}
	
	public Relationship(String entitySrc, String entityTgt, RelationType relationTypeS2T, RelationType relationTypeT2S)
	{
		this.entitySrc = entitySrc;
		this.entityTgt = entityTgt;
		this.relationTypeS2T = relationTypeS2T;
		this.relationTypeT2S = relationTypeT2S;				
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getEntitySrc()
	{
		return entitySrc;
	}

	public void setEntitySrc(String entitySrc)
	{
		this.entitySrc = entitySrc;
	}

	public String getEntityTgt()
	{
		return entityTgt;
	}

	public void setEntityTgt(String entityTgt)
	{
		this.entityTgt = entityTgt;
	}

	public RelationType getRelationTypeS2T()
	{
		return relationTypeS2T;
	}

	public void setRelationTypeS2T(String relationTypeS2T)
	{
		this.relationTypeS2T = RelationType.valueOf(relationTypeS2T);
	}

	public RelationType getRelationTypeT2S()
	{
		return relationTypeT2S;
	}

	public void setRelationTypeT2S(String relationTypeT2S)
	{
		this.relationTypeT2S = RelationType.valueOf(relationTypeT2S);
	}	
 
	@Override
	public String toString()
	{
		return String.format("Relationship[entitySrc=%s, entityTgt='%s', relationTypeS2T=%d, relationTypeT2S=%d]", entitySrc, entityTgt, relationTypeS2T, relationTypeT2S);
	}
}