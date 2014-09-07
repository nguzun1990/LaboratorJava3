package com.pentalog.nguzun.vo;


/**
 *
 * @author Guzun
 */
public class Role extends BaseValueObject {

    private String description;

    public Role() {
    }

//    public Role(String name, String description) {
//        this.setName(name);
//        this.setDescription(description);
//    }
//
//    public Role(int id, String name, String description) {
//        this.setName(name);
//        this.setDescription(description);
//        this.setId(id);
//    }

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
