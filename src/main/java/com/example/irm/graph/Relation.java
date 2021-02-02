package com.example.irm.graph;

public class Relation
{
	private String relationType;
	private int distance;
	
	public Relation(String relationType, int distance) {
		this.setRelationType(relationType);
		this.setDistance(distance);
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
