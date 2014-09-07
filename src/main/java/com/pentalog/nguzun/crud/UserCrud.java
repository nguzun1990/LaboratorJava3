package com.pentalog.nguzun.crud;

import com.pentalog.nguzun.vo.User;

public class UserCrud extends BaseCrud<User>{
		
	private static UserCrud instance;

    private UserCrud() {
    }

    public static UserCrud getInstance() {
		if (instance == null) {
			instance = new UserCrud();
		}
		return instance;
	}
}
