package com.pentalog.nguzun.file.csv;

import com.pentalog.nguzun.dao.RoleDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.record.GroupRecord;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.Role;

/**
 *
 * @author Guzun
 */
// TODO parte cu instance nu e corect locul unde e folosit NotDone
public class GroupCsvProcessor extends BaseCsvProcessor<Group> {

	private static GroupCsvProcessor instance;

	private RoleDAO roleDAO;
	
	private GroupCsvProcessor() {
		roleDAO  = DaoFactory.buildObject(RoleDAO.class);
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
			Role role = null;
			try {
				role = roleDAO.retrive(roleId);
			} catch (ExceptionDAO e) {
				log.error("GroupCsvProcessor createEntity: an error dao was occured: " + e.getMessage(), e);
			}
			group = new Group.Builder()
					.id(id)
					.name(name)
					.description(description)
					.role(role)
					.build();
		}

		return group;
	}

	@Override
	public String createStringForEntity(Group group, String cvsSplitBy) {
		StringBuilder strBuilder = new StringBuilder("");
		strBuilder.append(group.getId()).append(cvsSplitBy)
				.append(group.getName()).append(cvsSplitBy)
				.append(group.getDescription()).append(cvsSplitBy)
				.append(group.getRole().getId()).append('\n');

		return strBuilder.toString();
	}
}
