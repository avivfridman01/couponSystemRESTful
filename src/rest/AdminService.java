package rest;

import java.util.Set;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import businessDelegate.BusinessDelegate;
import coupon.beans.Company;
import coupon.beans.Customer;
import entities.Income;
import facade.AdminFacade;
import system.exception.CouponSystemException;

@Path("/sec/serviceAdmin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminService {

	public AdminService() {
	}

	@Context
	HttpServletRequest request;

	private BusinessDelegate businessDelegate = new BusinessDelegate();
	AdminFacade adminFacade = null;
	ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void getFacade() {
		HttpSession session = request.getSession(false);
		adminFacade = (AdminFacade) session.getAttribute("adminFacade");
	}

	@Path("/createCompany")
	@POST
	public Response createCompany(Company company) throws CouponSystemException {
		try {
			adminFacade.createCompany(company);
			String json = objectMapper.writeValueAsString(company);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/deleteCompany/{id}")
	@DELETE
	public Response removeCompany(@PathParam("id") int id) throws CouponSystemException {
		try {
			Company company = adminFacade.getCompany(id);
			adminFacade.removeCompany(company);
			String json = objectMapper.writeValueAsString(company);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/updateCompany")
	@PUT
	public Response updateCompany(Company company) throws CouponSystemException {
		try {
			adminFacade.updateCompany(company);
			String json = objectMapper.writeValueAsString(company);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getCompany/{id}")
	@GET
	public Response getCompany(@PathParam("id") int id) throws CouponSystemException {
		try {
			Company company = adminFacade.getCompany(id);
			String json = objectMapper.writeValueAsString(company);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getAllCompanies")
	@GET
	public Response getAllCompanies() throws CouponSystemException {
		try {
			Set<Company> set = adminFacade.getAllCompanies();
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/createCustomer")
	@POST
	public Response createCustomer(Customer customer) throws CouponSystemException {
		try {
			adminFacade.createCustomer(customer);
			String json = objectMapper.writeValueAsString(customer);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/deleteCustomer/{id}")
	@DELETE
	public Response removeCustomer(@PathParam("id") int id) throws CouponSystemException {
		try {
			Customer customer = adminFacade.getCustomer(id);
			adminFacade.removeCustomer(customer);
			String json = objectMapper.writeValueAsString(customer);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/updateCustomer")
	@PUT
	public Response updateCustomer(Customer customer) throws CouponSystemException {
		try {
			adminFacade.updateCustomer(customer);
			String json = objectMapper.writeValueAsString(customer);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getCustomer/{id}")
	@GET
	public Response getCustomer(@PathParam("id") int id) throws CouponSystemException {
		try {
			Customer customer = adminFacade.getCustomer(id);
			String json = objectMapper.writeValueAsString(customer);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getAllCustomers")
	@GET
	public Response getAllCustomers() throws CouponSystemException {
		try {
			Set<Customer> set = adminFacade.getAllCustomers();
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
	
	@Path("/viewAllIncome")
	@GET
	public Response viewAllIncome() {
		try {
			Income[] incomes = businessDelegate.viewAllIncome();
			String json = objectMapper.writeValueAsString(incomes);
			return Response.ok(json).build();
		} catch (JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
	@Path("/viewIncomeByCompany/{id}")
	@GET
	public Response viewIncomeByCompany(@PathParam("id")long id) {
		try {
			Income[] incomes = businessDelegate.viewIncomeByCompany(id);
			String json = objectMapper.writeValueAsString(incomes);
			return Response.ok(json).build();
		} catch (JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
	@Path("/viewIncomeByCustomer/{id}")
	@GET
	public Response viewIncomeByCustomer(@PathParam("id")long id) {
		try {
			Income[] incomes = businessDelegate.viewIncomeByCustomer(id);
			String json = objectMapper.writeValueAsString(incomes);
			return Response.ok(json).build();
		} catch (JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
}
