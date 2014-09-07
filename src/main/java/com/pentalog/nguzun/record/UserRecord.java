package com.pentalog.nguzun.record;


public class UserRecord extends BaseRecord {
	
	public static final int EXPECTED_RECORD_LENGTH = 5;
	private static final int USER_ID_INDEX = 0;
	private static final int USER_NAME_INDEX = 1;
	private static final int USER_LOGIN_INDEX = 2;
	private static final int USER_PASSWORD_INDEX = 3;
	private static final int USER_GROUP_INDEX = 4;

	public static int getId(String[] record) {
		int id = getInt(record[USER_ID_INDEX]);

		return id;
	}

	public static String getName(String[] record) {
		String name = getString(record[USER_NAME_INDEX]);

		return name;
	}

	public static String getLogin(String[] record) {
		String login = getString(record[USER_LOGIN_INDEX]);

		return login;
	}

	public static String getPassword(String[] record) {
		String password = getString(record[USER_PASSWORD_INDEX]);

		return password;
	}
	
	public static int getGroupId(String[] record) {
		int groupId = getInt(record[USER_GROUP_INDEX]);

		return groupId;
	}

}
