package com.pentalog.nguzun.file.csv;


import com.pentalog.nguzun.record.RoleRecord;
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Guzun
 */
public class RoleCsvProcessor extends BaseCsvProcessor<Role> {

	private static RoleCsvProcessor instance;

    private RoleCsvProcessor() {
    }

    public static RoleCsvProcessor getInstance() {
		if (instance == null) {
			instance = new RoleCsvProcessor();
		}
		return instance;
	}
    
	@Override
	public Role createEntity(String[] record) {

		String name, description;
		int id;
		Role role = null;
		if (record.length == RoleRecord.EXPECTED_RECORD_LENGTH) {
			name = RoleRecord.getName(record);
			description = RoleRecord.getDescription(record);
			id = RoleRecord.getId(record);
			role = new Role.Builder().id(id).name(name)
					.description(description).build();
		}

		return role;
	}

	@Override
	public String createStringForEntity(Role role, String cvsSplitBy) {
		StringBuilder strBuilder = new StringBuilder("");
		strBuilder.append(role.getId()).append(cvsSplitBy)
				.append(role.getName()).append(cvsSplitBy)
				.append(role.getDescription()).append('\n');

		return strBuilder.toString();
	}
}
