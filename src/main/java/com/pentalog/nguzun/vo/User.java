/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentalog.nguzun.vo;

import javax.persistence.*;




/**
 *
 * @author Guzun
 */
@Entity
@Table(name = "user")
public class User extends BaseValueObject {

	@Column(name = "login")
    private String login;
	
	@Column(name = "password")
    private String password;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_group", nullable = true)
    private Group group;

    public User(){
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    
    /**
     * @return the Group
     */
    public Group getGroup() {
		return group;
	}

    /**
     * @param group the group to set
     */
	public void setGroup(Group group) {
		this.group = group;
	}
    
    public static class Builder extends BaseValueObject.Builder<Builder>{
    	private String login;
    	private String password;
    	private Group group;

        public Builder login(String login) {
        	this.login = login;
            return this;
        }

        public Builder password(String password) {
        	this.password = password;
            return this;
        }
        
        public Builder group(Group group) {
        	this.group = group;
            return this;
        }
        
        public User build() {
           return new User(this);
        }
    }
    
    private User(Builder builder) {
    	super(builder);
    	login = builder.login;
    	password = builder.password;
    	group = builder.group;
    }

    
}
