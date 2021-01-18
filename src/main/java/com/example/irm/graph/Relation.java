package com.example.irm.graph;

import com.example.irm.utils.RelationType;

public class Relation
{
	private RelationType relationType;
	private int distance;
	
	public Relation(RelationType relationType, int distance) {
		this.setRelationType(relationType);
		this.setDistance(distance);
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
}
