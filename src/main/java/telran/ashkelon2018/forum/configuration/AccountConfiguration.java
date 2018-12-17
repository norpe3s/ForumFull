package telran.ashkelon2018.forum.configuration;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import lombok.Getter;
import telran.ashkelon2018.forum.exceptions.MetodNotFoundExceptions;

@Configuration
@ManagedResource
public class AccountConfiguration {

	@Value("${exp.value}") // можно теперь в appl propertis добавить эти настройки
	int expPeriod;

	@ManagedAttribute // теперь можем в джконсоле смотреть этот параметр без перегрузки сервера
	public int getExpPeriod() {
		return expPeriod;
	}

	@ManagedAttribute // теперь можем в джконсоле менять этот параметр без перегрузки сервера
	public void setExpPeriod(int expPeriod) {
		this.expPeriod = expPeriod;
	}

	@Value("${logic.viewError}") // можно теперь в appl propertis добавить эти настройки
	boolean logicviewError;

	@ManagedAttribute
	public boolean getLogicviewError() {
		return logicviewError;
	}

	@ManagedAttribute
	public void setLogicviewError(boolean logicviewError) {
		this.logicviewError = logicviewError;
	}

	@Getter
	final String USER = "User";
	@Getter
	final String ADMIN = "Admin";
	@Getter
	final String MODER = "Moderator";

	public AccountUserCredentials tokenDecode(String token) {

		int index = token.indexOf(" ");
		token = token.substring(index + 1);
		byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
		token = new String(base64DecodeBytes);
		String[] auth = token.split(":");
		AccountUserCredentials credentials = new AccountUserCredentials((auth[0]), auth[1]);
		return credentials;
	}

	public boolean findRoles(Set<String> rolesToken, String login, String loginToken, String acessMetod) {
		
		HashMap<String, Set<String>> acessKey = new HashMap<>();
		
		switch (acessMetod) {
		case "removeUser":
			acessKey.put(USER, new HashSet<>(Arrays.asList()));
			acessKey.put(MODER, new HashSet<>(Arrays.asList(USER)));
			acessKey.put(ADMIN, new HashSet<>(Arrays.asList(USER, MODER)));
			break;

		case "addRole":			
			acessKey.put(USER, new HashSet<>(Arrays.asList()));
			acessKey.put(MODER, new HashSet<>(Arrays.asList()));
			acessKey.put(ADMIN, new HashSet<>(Arrays.asList(USER, MODER, ADMIN)));
			break;

		case "removeRole":			
			acessKey.put(USER, new HashSet<>(Arrays.asList()));
			acessKey.put(MODER, new HashSet<>(Arrays.asList()));
			acessKey.put(ADMIN, new HashSet<>(Arrays.asList(USER, MODER, ADMIN)));
			break;

		default:
			throw new MetodNotFoundExceptions("Forbidden." + (getLogicviewError()) != null ? "// Not Found Metod for accesKey / Error 4X030024" : "");
		}
		
		 boolean tokenAdmin = rolesToken.contains(ADMIN);
		 boolean tokenModer = rolesToken.contains(MODER);
		 boolean tokenUser = rolesToken.contains(USER);



		
		return false;
	}

	// public boolean findRoles(Set<String> rolesToken, HashMap<String,Set<String>>
	// searchRoles) {
	//
	// boolean searchAdmin = false;
	// boolean searchModer = false;
	// boolean searchUser = false;
	// boolean i=true, j=true, z=true;
	//
	// for (String string : searchRoles) {
	// if (i && string.equals(ADMIN)) {
	// searchAdmin = string.equals(ADMIN);
	// i = false;
	// }
	// if (j && string.equals(MODER)) {
	// searchModer = string.equals(MODER);
	// j = false;
	// }
	// if (z && string.equals(USER)) {
	// searchUser = string.equals(USER);
	// z = false;
	// }
	// }
	//
	// boolean tokenAdmin = rolesToken.contains(ADMIN);
	// boolean tokenModer = rolesToken.contains(MODER);
	// boolean tokenUser = rolesToken.contains(USER);
	//
	// return searchUser&&tokenUser || searchModer&&tokenModer ||
	// searchAdmin&&tokenAdmin;
	// }

}
