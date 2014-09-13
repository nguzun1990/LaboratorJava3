package com.pentalog.nguzun.vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 *
 * @author Guzun
 */
@Entity
@Table(name = "groups")
public class Group extends BaseValueObject {

	@Column(name = "description")
    private String description;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	private Set<User> users = new HashSet<User>(0);
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_role", nullable = true)
    private Role role;


    public Group(){
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
     * @return the role
     */
    public Role getRole() {
		return role;
	}

    /**
     * @param idRole the role to set
     */
	public void setRole(Role role) {
		this.role = role;
	}
    
    public static class Builder extends BaseValueObject.Builder<Builder>{
    	private String description;
    	private Role role;
        
        public Builder description(String description) {
        	this.description = description;
            return this;
        }
        
        
        public Builder role(Role role) {
        	this.role = role;
            return this;
        }
        
        public Group build() {
           return new Group(this);
        }
    }
    
    private Group(Builder builder) {
    	super(builder);
    	description = builder.description;
    	role = builder.role;
    }


    public Set<User> getUsers() {
		return users;
	}


	public void setUsers(Set<User> users) {
		this.users = users;
	}

	
}
