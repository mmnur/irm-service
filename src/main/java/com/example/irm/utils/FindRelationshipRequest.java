package com.example.irm.utils;

public class FindRelationshipRequest {
	String entityIdSrc;
	String entityIdTgt;
	int maxDegreeOfRelationship;

	public FindRelationshipRequest() {
	}

	public FindRelationshipRequest(String entityIdSrc, String entityIdTgt, int maxDegreeOfRelationship) {
		this.entityIdSrc = entityIdSrc;
		this.entityIdTgt = entityIdTgt;
		this.maxDegreeOfRelationship = maxDegreeOfRelationship;
	}

	public String getEntityIdSrc() {
		return entityIdSrc;
	}

	public String getEntityIdTgt() {
		return entityIdTgt;
	}

	public int getMaxDegreeOfRelationship() {
		return maxDegreeOfRelationship;
	}

	public void setEntityIdSrc(String entityIdSrc) {
		this.entityIdSrc = entityIdSrc;
	}

	public void setEntityIdTgt(String entityIdTgt) {
		this.entityIdTgt = entityIdTgt;
	}

	public void setMaxDegreeOfRelationship(int maxDegreeOfRelationship) {
		this.maxDegreeOfRelationship = maxDegreeOfRelationship;
	}
}
