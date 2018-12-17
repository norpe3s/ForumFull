package telran.ashkelon2018.forum.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.dto.UserProfileDto;
import telran.ashkelon2018.forum.dto.userRegDto;
import telran.ashkelon2018.forum.service.AccountSevice;

@RestController
@RequestMapping("/account") //передняя часть point запросов 
public class AccountManagmentController {
	
	@Autowired
	AccountSevice accountSevice;
	
	@PostMapping 
	public UserProfileDto register(@RequestBody userRegDto userregdto, @RequestHeader("Authorization") String token) {
		return accountSevice.addUser(userregdto, token);
	}
	
	@GetMapping
	UserProfileDto loginUser(@RequestHeader("Authorization") String token) {
		return accountSevice.login(token);
	}
	
	@PutMapping
	public UserProfileDto update(@RequestBody userRegDto userregdto, @RequestHeader("Authorization") String token) {
		return accountSevice.editUser(userregdto, token);
	}
	
	@DeleteMapping("/{login}")
	public UserProfileDto remove(@PathVariable String login, @RequestHeader("Authorization") String token) {
		return accountSevice.removeUser(login, token);
	}
	
	@PutMapping("/{login}/{role}")
	Set<String> addRole(@PathVariable String login, @PathVariable String role, @RequestHeader("Authorization")  String token){
		return accountSevice.addRole(login, role, token);
	}
	
	@DeleteMapping("/{login}/{role}")
	Set<String> removeRole(@PathVariable String login, @PathVariable String role, @RequestHeader("Authorization")  String token){
		return accountSevice.removeRole(login, role, token);
	}
	
	@PutMapping("/password")
	void changePassword( @RequestHeader("X-Autorization") String password, @RequestHeader("Authorization") String token) {
		accountSevice.changePassword(password, token);
	}

}
