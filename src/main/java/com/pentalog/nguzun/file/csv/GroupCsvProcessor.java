package com.pentalog.nguzun.file.csv;

import com.pentalog.nguzun.record.GroupRecord;
import com.pentalog.nguzun.vo.Group;

/**
 *
 * @author Guzun
 */
// TODO parte cu instance nu e corect locul unde e folosit NotDone
public class GroupCsvProcessor extends BaseCsvProcessor<Group> {

	private static GroupCsvProcessor instance;

	private GroupCsvProcessor() {
	}

	public static GroupCsvProcessor getInstance() {
		if (instance == null) {
			instance = new GroupCsvProcessor();
		}
		return instance;
	}

	@Override
	public Group createEntity(String[] record) {

		String name, description;
		int id, roleId;
		Group group = null;
		if (record.length == GroupRecord.EXPECTED_RECORD_LENGTH) {
			name = GroupRecord.getName(record);
			description = GroupRecord.getDescription(record);
			id = GroupRecord.getId(record);
			roleId = GroupRecord.getRoleId(record);
			group = new Group.Builder().id(id).name(name)
					.description(description).idRole(roleId).build();
		}

		return group;
	}

	@Override
	public String createStringForEntity(Group group, String cvsSplitBy) {
		StringBuilder strBuilder = new StringBuilder("");
		strBuilder.append(group.getId()).append(cvsSplitBy)
				.append(group.getName()).append(cvsSplitBy)
				.append(group.getDescription()).append(cvsSplitBy)
				.append(group.getIdRole()).append('\n');

		return strBuilder.toString();
	}
}
