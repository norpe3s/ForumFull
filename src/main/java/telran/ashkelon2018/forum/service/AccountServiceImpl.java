package telran.ashkelon2018.forum.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.configuration.AccountConfiguration;
import telran.ashkelon2018.forum.configuration.AccountUserCredentials;
import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.UserAccount;
import telran.ashkelon2018.forum.dto.UserProfileDto;
import telran.ashkelon2018.forum.dto.userRegDto;
import telran.ashkelon2018.forum.exceptions.ForbiddenException;
import telran.ashkelon2018.forum.exceptions.LoginNotFoundException;
import telran.ashkelon2018.forum.exceptions.RolesException;
import telran.ashkelon2018.forum.exceptions.UserConfliktException;
import telran.ashkelon2018.forum.exceptions.UserNotFoundException;

@Service
public class AccountServiceImpl implements AccountSevice {

	@Autowired // 2
	UserAccountRepository userAccountRepository;

	@Autowired // 2
	AccountConfiguration accountConfiguration;

	@Override
	public UserProfileDto addUser(userRegDto userregdto, String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		if (userAccountRepository.existsById(credentials.getLogin())) {
			throw new UserConfliktException();
		}
		String hashPasword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = UserAccount.builder().login(credentials.getLogin()).password(hashPasword)
				.firstName(userregdto.getFirstName()).lastName(userregdto.getLastName())
				.role(accountConfiguration.getUSER())
				.role(userAccountRepository.count() == 0 ? accountConfiguration.getADMIN()
						: accountConfiguration.getUSER())
				.expdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod())).build();
		userAccountRepository.save(userAccount);
		return convertToUSerProfileDto(userAccount);
	}

	private UserProfileDto convertToUSerProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder().firstName(userAccount.getFirstName()).lastName(userAccount.getLastName())
				.login(userAccount.getLogin()).roles(userAccount.getRoles()).build();

	}

	@Override
	public UserProfileDto editUser(userRegDto userregdto, String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userAccountRepository.findById(credentials.getLogin()).get();
		if (userregdto.getLastName() != null) {
			userAccount.setLastName(userregdto.getLastName());
		}
		if (userregdto.getFirstName() != null) {
			userAccount.setFirstName(userregdto.getFirstName());
		}
		userAccountRepository.save(userAccount);
		return convertToUSerProfileDto(userAccount);
	}

	@Override
	public UserProfileDto removeUser(String login, String token) {

		AccountUserCredentials credentialsToken = null;
		try {
			credentialsToken = accountConfiguration.tokenDecode(token);
		} catch (Exception e) {
			throw new ForbiddenException(
					"Forbidden." + (accountConfiguration.getLogicviewError() ? "// Error 4X030010" : ""));
		}		
		
		UserAccount userAccountLogin = userAccountRepository.findById(login).orElseThrow(() -> new LoginNotFoundException(
				"Login not found" + (accountConfiguration.getLogicviewError() ? "// Error 4X040008" : "")));
		
		UserAccount userAccountToken = userAccountRepository.findById(credentialsToken.getLogin())
				.orElseThrow(() -> new UserNotFoundException(
						"User not found." + (accountConfiguration.getLogicviewError() ? "// Error 4X010009" : "")));
		
		Set<String> rolesLogin = userAccountLogin.getRoles();
		Set<String> rolesToken = userAccountToken.getRoles();
		String loginToken = userAccountToken.getLogin();
		
		boolean rolesRules = accountConfiguration.findRoles(rolesLogin, rolesToken, login, loginToken, "removeUser");

		if (rolesRules) {
			userAccountRepository.delete(userAccountLogin);
		} else {
			throw new ForbiddenException(
					"Forbiden." + (accountConfiguration.getLogicviewError() ? "// Error 4X030009" : ""));
		}		
		return convertToUSerProfileDto(userAccountLogin);
	}

	@Override
	public Set<String> addRole(String login, String role, String token) {

		if (!(role.equals(accountConfiguration.getUSER()) || role.equals(accountConfiguration.getMODER())
				|| role.equals(accountConfiguration.getADMIN()))) {
			throw new RolesException("Msymath Roles: " + role + ". VERIFY TYPE: " + accountConfiguration.getUSER()
					+ " / " + accountConfiguration.getMODER() + " / " + accountConfiguration.getADMIN()
					+ (accountConfiguration.getLogicviewError() ? " // Error 4X140022" : ""));
		}

		AccountUserCredentials credentials = null;
		try {
			credentials = accountConfiguration.tokenDecode(token);
		} catch (Exception e) {
			throw new ForbiddenException(
					"Forbidden." + (accountConfiguration.getLogicviewError() ? "// Error 4X030014" : ""));
		}

		UserAccount userAccountToken = userAccountRepository.findById(credentials.getLogin())
				.orElseThrow(() -> new UserNotFoundException(
						"User not found." + (accountConfiguration.getLogicviewError() ? "// Error 4X010015" : "")));
		
		UserAccount userAccountLogin = userAccountRepository.findById(login)
				.orElseThrow(() -> new LoginNotFoundException(
						"Login not found" + (accountConfiguration.getLogicviewError() ? "// Error 4X040017" : "")));
		
		Set<String> rolesLogin = userAccountLogin.getRoles();
		Set<String> rolesToken = userAccountToken.getRoles();
		String loginToken = userAccountToken.getLogin();
		
		boolean rolesRules = accountConfiguration.findRoles(rolesLogin, rolesToken, login, loginToken, "addRole");

		if (rolesRules) {
			userAccountRepository.delete(userAccountLogin);
			userAccountLogin.addRole(role);
			userAccountRepository.save(userAccountLogin);

		} else {
			throw new ForbiddenException(
					"Forbiden." + (accountConfiguration.getLogicviewError() ? "// Error 4X030016" : ""));
		}

		return userAccountLogin.getRoles();
	}

	@Override
	public Set<String> removeRole(String login, String role, String token) {

		if (!(role.equals(accountConfiguration.getUSER()) || role.equals(accountConfiguration.getMODER())
				|| role.equals(accountConfiguration.getADMIN()))) {
			throw new RolesException("Msymath Roles: " + role + ". VERIFY TYPE: " + accountConfiguration.getUSER()
					+ " / " + accountConfiguration.getMODER() + " / " + accountConfiguration.getADMIN()
					+ (accountConfiguration.getLogicviewError() ? " // Error 4X140023" : ""));
		}

		AccountUserCredentials credentials = null;
		try {
			credentials = accountConfiguration.tokenDecode(token);
		} catch (Exception e) {
			throw new ForbiddenException(
					"Forbidden." + (accountConfiguration.getLogicviewError() ? "// Error 4X030018" : ""));
		}

		UserAccount userAccountToken = userAccountRepository.findById(credentials.getLogin())
				.orElseThrow(() -> new UserNotFoundException(
						"User not found." + (accountConfiguration.getLogicviewError() ? "// Error 4X010019" : "")));
		
		UserAccount userAccountLogin = userAccountRepository.findById(login)
				.orElseThrow(() -> new LoginNotFoundException(
						"Login not found" + (accountConfiguration.getLogicviewError() ? "// Error 4X040021" : "")));

		Set<String> rolesLogin = userAccountLogin.getRoles();
		Set<String> rolesToken = userAccountToken.getRoles();
		String loginToken = userAccountToken.getLogin();
		
		boolean rolesRules = accountConfiguration.findRoles(rolesLogin, rolesToken, login, loginToken, "removeRole");

		if (rolesRules) {
			userAccountRepository.delete(userAccountLogin);
			userAccountLogin.removeRole(role);
			userAccountRepository.save(userAccountLogin);

		} else {
			throw new ForbiddenException(
					"Forbiden." + (accountConfiguration.getLogicviewError() ? "// Error 4X030020" : ""));
		}
		return userAccountLogin.getRoles();
	}

	@Override
	public void changePassword(String password, String token) {

		AccountUserCredentials credentials = null;
		try {
			credentials = accountConfiguration.tokenDecode(token);
		} catch (Exception e) {
			throw new ForbiddenException(
					"Forbidden." + (accountConfiguration.getLogicviewError() ? "// Error 4X030011" : ""));
		}

		UserAccount userAccount = userAccountRepository.findById(credentials.getLogin())
				.orElseThrow(() -> new UserNotFoundException(
						"User not found." + (accountConfiguration.getLogicviewError() ? "// Error 4X010012" : "")));
		
			String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			userAccount.setPassword(hashPassword);
			userAccount.setExpdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()));
			userAccountRepository.save(userAccount);
	}

	@Override
	public UserProfileDto login(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userAccountRepository.findById(credentials.getLogin()).get();
		return convertToUSerProfileDto(userAccount);
	}

}
