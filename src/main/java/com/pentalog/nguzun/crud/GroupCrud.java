package com.pentalog.nguzun.crud;

import com.pentalog.nguzun.vo.Group;

public class GroupCrud extends BaseCrud<Group>{
		
	private static GroupCrud instance;

    private GroupCrud() {
    }

    public static GroupCrud getInstance() {
		if (instance == null) {
			instance = new GroupCrud();
		}
		return instance;
	}
}
