package br.jus.stf.plataforma.shared.actions.resources;

import java.util.Set;

import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;

/**
 * @author Lucas.Rodrigues
 *
 */
public class ActionDto {

	private String id;	
	private String description;
	private Set<String> groups;
	private ResourcesMode resourcesMode;
	private Boolean hasConditionHandlers;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the resourcesMode
	 */
	public ResourcesMode getResourcesMode() {
		return resourcesMode;
	}
	
	/**
	 * @param resourcesMode the resourcesMode to set
	 */
	public void setResourcesMode(ResourcesMode resourcesMode) {
		this.resourcesMode = resourcesMode;
	}
	
	/**
	 * @return the groups
	 */
	public Set<String> getGroups() {
		return groups;
	}
	
	/**
	 * @param groups the groups to set
	 */
	public void setGroups(Set<String> groups) {
		this.groups = groups;
	}
	
	/**
	 * @return the hasConditionHandlers
	 */
	public Boolean getHasConditionHandlers() {
		return hasConditionHandlers;
	}
	
	/**
	 * @param hasConditionHandlers the hasConditionHandlers to set
	 */
	public void setHasConditionHandlers(Boolean hasConditionHandlers) {
		this.hasConditionHandlers = hasConditionHandlers;
	}
	
}