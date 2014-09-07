package com.pentalog.nguzun.vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


/**
 *
 * @author Guzun
 */
public class Role extends BaseValueObject {

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<Group> groups = new HashSet<Group>(0);
	
	@Column(name = "description")
    private String description;

    public Role() {
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
    
    public static class Builder extends BaseValueObject.Builder<Builder>{
    	private String description;
        
        public Builder description(String description) {
        	this.description = description;
            return this;
        }
        
        
        public Role build() {
           return new Role(this);
        }
    }
    
    private Role(Builder builder) {
    	super(builder);
    	description = builder.description;
    }
}
