package telran.ashkelon2018.forum.service;

import java.util.Set;

import telran.ashkelon2018.forum.dto.UserProfileDto;
import telran.ashkelon2018.forum.dto.userRegDto;

public interface AccountSevice {

	UserProfileDto addUser(userRegDto userregdto, String token);
	UserProfileDto editUser(userRegDto userregdto, String token);
	UserProfileDto removeUser(String login, String token);
	Set<String> addRole(String login, String role, String token);
	Set<String> removeRole(String login, String role, String token);
	void changePassword(String password, String token);
	UserProfileDto login(String token);
	
}
