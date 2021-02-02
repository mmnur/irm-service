package com.example.irm.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.example.irm.model.Relationship;

public class RelationshipGraph
{
	static private RelationshipGraph rGraph = new RelationshipGraph();;	
    private Set<Entity> entities = new HashSet<>();
    
    private RelationshipGraph() { }
    
    static public RelationshipGraph getGraph()
    {
    	return rGraph;
    }
    
    public void addEntity(Entity entity)
    {
        entities.add(entity);
    }

    public Entity findEntity(String eid)
    {
    	UUID visitedFlag = java.util.UUID.randomUUID();
    	for(Entity entity: entities) {
    		if (!entity.isVisited(visitedFlag)) {
	    		Entity e = entity.findEntity(eid, visitedFlag);
	    		if (e != null) {
	    			return e;
	    		}
    		}
    	}
    	
        return null;
    }
    
    public List<List<Relationship>> findRelationships(String eid1, String eid2, int maxDegreeOfRelationship)
    {
    	List<List<Relationship>> allRelationships = new ArrayList<List<Relationship>>();    	
    	
    	if (eid1.compareToIgnoreCase(eid2) == 0) {
        	List<Relationship> relations = new ArrayList<Relationship>();
    		allRelationships.add(relations);
    		
    		Relationship r = new Relationship(eid1, eid2, "Self", "Self");
    		relations.add(r);
    		
    		return allRelationships;
    	}
    	
    	Entity entity1 = this.findEntity(eid1);
    	List<List<Entity>> allPaths = new ArrayList<List<Entity>>();
    	List<Entity> currentPath = new ArrayList<Entity>();
    	currentPath.add(entity1);
    	entity1.findRelationship(eid2, allPaths, currentPath, maxDegreeOfRelationship, 0, java.util.UUID.randomUUID());
    	
    	for(int i=0; i<allPaths.size(); i++) {
    		currentPath = allPaths.get(i);
    		List<Relationship> relationships = new ArrayList<Relationship>();
    		for(int j=0; j<currentPath.size()-1; j++) {
    			Entity e1 = currentPath.get(j);
    			Entity e2 = currentPath.get(j+1);
    			Relationship relationship = new Relationship(e1.getEntityID(), e2.getEntityID(), e1.getRelationship(e2), e2.getRelationship(e1));
    			relationships.add(relationship);
    			
    		}
    		allRelationships.add(relationships);
    	}
    	
    	return allRelationships;
    }

    public Set<Entity> getEntities()
    {
        return entities;
    }

    public void setEntities(Set<Entity> entities)
    {
        this.entities = entities;
    }
}

