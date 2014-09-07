package com.pentalog.nguzun.vo;

import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 *
 * @author Guzun
 */

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseValueObject {

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "name")
	private String name;

	public BaseValueObject() {
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("rawtypes")
	public static class Builder<T extends Builder> {
		protected int id;
		protected String name;

		/**
		 * @param id
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public T id(int id) {
			this.id = id;
			return (T) this;
		}

		/**
		 * @param name
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public T name(String name) {
			this.name = name;
			return (T) this;
		}
	}

	@SuppressWarnings("rawtypes")
	protected BaseValueObject(Builder builder) {
		id = builder.id;
		name = builder.name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
