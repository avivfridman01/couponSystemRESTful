package rest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import demo.LoginObj;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import main.CouponSystemSingleton;
import system.exception.CouponSystemException;


@Path("/LogginService")
public class LogginServices {

	@Context
	private HttpServletRequest request;

	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginObj login) throws CouponSystemException {
		try {
			request.getSession().invalidate();
			CouponSystemSingleton couponSystemSingleton = CouponSystemSingleton.getInstance();
			switch (login.clientType) {
			case COMPANY:
				CompanyFacade companyFacade = (CompanyFacade) couponSystemSingleton.login(login.name, login.password, login.clientType);
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(60*60*24);
				session.setAttribute("companyFacade", companyFacade);
				return Response.accepted().build();
			case CUSTOMER:
				CustomerFacade customerFacade = (CustomerFacade) couponSystemSingleton.login(login.name, login.password, login.clientType);
				HttpSession session2 = request.getSession();
				session2.setMaxInactiveInterval(60*60*24);
				session2.setAttribute("customerFacade", customerFacade);
				return Response.accepted().build();
			case ADMIN:
				AdminFacade adminFacade = (AdminFacade) couponSystemSingleton.login(login.name, login.password, login.clientType);
				HttpSession session3 = request.getSession();
				session3.setMaxInactiveInterval(60*60*24);
				session3.setAttribute("adminFacade", adminFacade);
				return Response.accepted().build();
			default:
				String json = "{\"message\":\"Type incorrect\"}";
				return Response.serverError().entity(json).build();
			}
		}catch(CouponSystemException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
}