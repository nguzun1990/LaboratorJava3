package com.pentalog.nguzun.record;

public class RoleRecord extends BaseRecord {
	
	public static final int EXPECTED_RECORD_LENGTH = 3;
	private static final int ROLE_ID_INDEX = 0;
	private static final int ROLE_NAME_INDEX = 1;
	private static final int ROLE_DESCRIPION_INDEX = 2;

	public static int getId(String[] record) {
		int id = getInt(record[ROLE_ID_INDEX]);

		return id;
	}

	public static String getName(String[] record) {
		String name = getString(record[ROLE_NAME_INDEX]);

		return name;
	}

	public static String getDescription(String[] record) {
		String login = getString(record[ROLE_DESCRIPION_INDEX]);

		return login;
	}
}
