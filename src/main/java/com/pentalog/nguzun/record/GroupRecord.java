package com.pentalog.nguzun.record;

public class GroupRecord extends BaseRecord {
	
	public static final int EXPECTED_RECORD_LENGTH = 4;
	private static final int GROUP_ID_INDEX = 0;
	private static final int GROUP_NAME_INDEX = 1;
	private static final int GROUP_DESCRIPION_INDEX = 2;
	private static final int GROUP_ROLE_INDEX = 3;

	public static int getId(String[] record) {
		int id = getInt(record[GROUP_ID_INDEX]);

		return id;
	}

	public static String getName(String[] record) {
		String name = getString(record[GROUP_NAME_INDEX]);

		return name;
	}

	public static String getDescription(String[] record) {
		String login = getString(record[GROUP_DESCRIPION_INDEX]);

		return login;
	}
	
	public static int getRoleId(String[] record) {
		int roleId = getInt(record[GROUP_ROLE_INDEX]);

		return roleId;
	}

}
