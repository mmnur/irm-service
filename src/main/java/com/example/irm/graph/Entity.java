package com.example.irm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.irm.utils.EntityType;

public class Entity
{
    private String entityID;
    private EntityType entityType;
    private List<UUID> visited = new ArrayList<UUID>();
    private Map<Entity, String> adjacentEntities = new HashMap<>();

    public Entity(String eid, EntityType type)
    {
        this.setEntityID(eid);
        this.setEntityType(type);
    }

    public void addRelation(Entity relatedEntity, String relationType)
    {
        adjacentEntities.put(relatedEntity, relationType);
    }

    public String getEntityID()
    {
        return entityID;
    }

    public void setEntityID(String eid)
    {
        this.entityID = eid;
    }

    public boolean isVisited(UUID visitedFlag)
    {
        return this.visited.contains(visitedFlag);
    }

    public void markVisited(UUID visitedFlag)
    {
        this.visited.add(visitedFlag);
    }
    
    public Entity findEntity(String eid, UUID visitedFlag)
    {
    	this.markVisited(visitedFlag);
    	
    	if (this.getEntityID().compareToIgnoreCase(eid) == 0) {
			return this;
		}
    	
    	for (Map.Entry<Entity, String> entry : this.adjacentEntities.entrySet()) {
    		Entity e1 = entry.getKey();
    		if (!e1.isVisited(visitedFlag)) {
    			Entity e = e1.findEntity(eid, visitedFlag);
	    		if (e != null) {
	    			return e;
	    		}
    		}
    	}
    	
    	return null;
    }
    
    String getRelationship(Entity e)
    {
    	for (Map.Entry<Entity, String> entry : this.adjacentEntities.entrySet()) {
    		if (entry.getKey() == e) {
    			return entry.getValue();
    		}
    	}
    	
    	return null;
    }
    
    public void findRelationship(String eid2, List<List<Entity>> allPaths, List<Entity> currentPath, int maxDegreeOfRelationship, int currentDegreeOfRelationship, UUID visitedFlag)
    {
    	if (currentDegreeOfRelationship <= maxDegreeOfRelationship) {
			
    		if(this.getEntityID().equalsIgnoreCase(eid2)) {
    			allPaths.add(currentPath);
    			return;
    		}
    		
	    	for (Map.Entry<Entity, String> entry : this.adjacentEntities.entrySet()) {
	    		Entity adjacentEntity = entry.getKey();
	    		if (!currentPath.contains(adjacentEntity)) {
	    			List<Entity> currentPathExtension = copyList(currentPath);
	    			currentPathExtension.add(adjacentEntity);
	    			adjacentEntity.findRelationship(eid2, allPaths, currentPathExtension, maxDegreeOfRelationship, currentDegreeOfRelationship+1, visitedFlag);
	    		}
	    	}
    	}
    }
    
    public List<Entity> copyList(List<Entity> a)
    {
    	List<Entity> b = new ArrayList<Entity>();
    	
    	for (int i=0; i<a.size(); i++) {
    		b.add(a.get(i));
    	}
    	
    	return b;
    }
    
    public Map<Entity, String> getAdjacentEntities()
    {
        return adjacentEntities;
    }

    public void setAdjacentNodes(Map<Entity, String> adjacentEntities)
    {
        this.adjacentEntities = adjacentEntities;        
    }

	public EntityType getEntityType()
	{
		return entityType;
	}

	public void setEntityType(EntityType entityType)
	{
		this.entityType = entityType;
	}

}