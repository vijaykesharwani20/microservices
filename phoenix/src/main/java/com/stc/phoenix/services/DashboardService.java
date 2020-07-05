package com.stc.phoenix.services;

import java.util.List;

import com.stc.phoenix.model.User;

public interface DashboardService {

	List<User> getAllUsers();
	User findById(int id);
	User save(User user);
	void deleteById(int id);
	User update(User user);
}
