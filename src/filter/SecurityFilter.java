package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

@WebFilter("/rest/sec/*")
public class SecurityFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		if(session == null) {
			System.out.println("Filter - session not exist");
			String url = "http://localhost:4200";
			httpResponse.sendRedirect(httpResponse.encodeRedirectURL(url));
			return;
		}
		CompanyFacade companyFacade = (CompanyFacade) session.getAttribute("companyFacade");
		CustomerFacade customerFacade = (CustomerFacade) session.getAttribute("customerFacade");
		AdminFacade adminFacade = (AdminFacade) session.getAttribute("adminFacade");
		if (companyFacade == null && customerFacade == null && adminFacade == null) {
			System.out.println("Filter - facade is incorrect");
			String url = "http://localhost:4200";
			httpResponse.sendRedirect(httpResponse.encodeRedirectURL(url));
			return;
		}
		chain.doFilter(request, response);
		System.out.println("Filter - session exist with facade");
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
