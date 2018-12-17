package telran.ashkelon2018.forum.service.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.configuration.AccountConfiguration;
import telran.ashkelon2018.forum.configuration.AccountUserCredentials;
import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.UserAccount;

@Service
@Order(1) // это первый фильтр
public class AuthenticationsFilter implements Filter {

	@Autowired
	UserAccountRepository repository;

	@Autowired
	AccountConfiguration configuration;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest reqs, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) reqs;
		HttpServletResponse response = (HttpServletResponse) resp;

		String path = request.getServletPath();
		String method = request.getMethod();

		if ((path.startsWith("/account") && !("POST".equals(method)))) {

			String token = request.getHeader("Authorization");
			if (token == null || token.equals("Basic Og==")) {
				response.sendError(401,
						"Unauthorized." + (configuration.getLogicviewError() ? " // Error 4X010001" : ""));
				return;
			}

			AccountUserCredentials userCredentials = null;
			try {
				userCredentials = configuration.tokenDecode(token);
			} catch (Exception e) {
				response.sendError(401,
						"Unauthorized." + (configuration.getLogicviewError() ? " // Error 4X010002" : ""));
				return;
			}

			UserAccount userAccount = repository.findById(userCredentials.getLogin()).orElse(null);
			if (userAccount == null) {
				response.sendError(403, "User not found / Error 4X030002");
				return;
			} else {
				if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
					response.sendError(403,
							"User not found." + (configuration.getLogicviewError() ? "// Error 4X030003" : ""));
					return;
				}
			}
		}

		if (!(path.startsWith("/forum/posts") || path.startsWith("/forum/titles") || ( path.startsWith("/account")&&"POST".equals(method) ))) {

			String token = request.getHeader("Authorization");
			if (token == null) {
				response.sendError(401,
						"Unauthorized." + (configuration.getLogicviewError() ? "// Error 4X010004" : ""));
				return;
			}

			AccountUserCredentials userCredentials = null;
			try {
				userCredentials = configuration.tokenDecode(token);
			} catch (Exception e) {
				response.sendError(401,
						"Unauthorized." + (configuration.getLogicviewError() ? "// Error 4X010005" : ""));
				return;
			}

			UserAccount userAccount = repository.findById(userCredentials.getLogin()).orElse(null);
			if (userAccount == null) {
				response.sendError(403,
						"User not found." + (configuration.getLogicviewError() ? "// Error 4X030006" : ""));
				return;
			} else {
				if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
					response.sendError(403,
							"User not found." + (configuration.getLogicviewError() ? "// Error 4X030007" : ""));
					return;
				}
			}
		}

		chain.doFilter(request, response);

	}

}
