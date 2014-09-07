package com.pentalog.nguzun.crud;

import com.pentalog.nguzun.vo.Role;

public class RoleCrud extends BaseCrud<Role> {
		
	private static RoleCrud instance;

    private RoleCrud() {
    }

    public static RoleCrud getInstance() {
		if (instance == null) {
			instance = new RoleCrud();
		}
		return instance;
	}
}
