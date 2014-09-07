/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pentalog.nguzun.file.csv;

import com.pentalog.nguzun.dao.GroupDAO;
import com.pentalog.nguzun.dao.Exception.ExceptionDAO;
import com.pentalog.nguzun.factory.DaoFactory;
import com.pentalog.nguzun.record.UserRecord;
import com.pentalog.nguzun.vo.Group;
import com.pentalog.nguzun.vo.User;

/**
 *
 * @author Guzun
 */
public class UserCsvProcessor extends BaseCsvProcessor<User> {
	
	private static UserCsvProcessor instance;

	private GroupDAO groupDAO;
	
    private UserCsvProcessor() {
    	groupDAO  = DaoFactory.buildObject(GroupDAO.class);
    }

    public static UserCsvProcessor getInstance() {
		if (instance == null) {
			instance = new UserCsvProcessor();
		}
		return instance;
	}
    
    @Override
    public User createEntity(String[] record) {
        String name, login, password;
        int id, groupId;
        User user = null;
		if (record.length == UserRecord.EXPECTED_RECORD_LENGTH) {
			name = UserRecord.getName(record);
            login = UserRecord.getLogin(record);
            password = UserRecord.getPassword(record);
            id = UserRecord.getId(record);
            groupId = UserRecord.getGroupId(record);
            
            Group group = null;
			try {
				group = groupDAO.retrive(groupId);
			} catch (ExceptionDAO e) {
				log.error("createEntity: an error dao was occured: " + e.getMessage(), e);
			}
            user = new User.Builder()
            		.id(id)
            		.name(name)
            		.login(login)
            		.password(password)
            		.group(group)
            		.build();
		}
		
	    return user;
    }

    @Override
    public String createStringForEntity(User user, String cvsSplitBy) {
        StringBuilder strBuilder = new StringBuilder("");
        strBuilder.append(user.getId())
                .append(cvsSplitBy)
                .append(user.getName())
                .append(cvsSplitBy)
                .append(user.getLogin())
                .append(cvsSplitBy)
                .append(user.getPassword())
                .append(cvsSplitBy)
                .append(user.getGroup().getId())
                .append('\n');

        return strBuilder.toString();
    }
}
