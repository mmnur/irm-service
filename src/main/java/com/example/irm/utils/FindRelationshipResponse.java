package com.example.irm.utils;

import java.util.List;
import com.example.irm.view.RelationshipUI;

public class FindRelationshipResponse {

	private List<List<RelationshipUI>> relatonships;

	public FindRelationshipResponse() {
	}

	public FindRelationshipResponse(List<List<RelationshipUI>> relatonships) {
		this.relatonships = relatonships;
	}

	public List<List<RelationshipUI>> getAllRelatonships() {
		return relatonships;
	}

	public void setAllRelatonships(List<List<RelationshipUI>> relatonships) {
		this.relatonships = relatonships;
	}
}
