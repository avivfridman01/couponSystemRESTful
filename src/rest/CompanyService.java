package rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import businessDelegate.BusinessDelegate;
import coupon.beans.Coupon;
import coupon.beans.CouponType;
import entities.Description;
import entities.Income;
import facade.CompanyFacade;
import system.exception.CouponSystemException;

@Path("sec/serviceCompany")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompanyService {

	public CompanyService() {
	}

	@Context
	private HttpServletRequest request;

	BusinessDelegate businessDelegate = new BusinessDelegate();
	CompanyFacade companyFacade = null;
	ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void getFacade() {
		HttpSession session = request.getSession(false);
		companyFacade = (CompanyFacade) session.getAttribute("companyFacade");
	}

	@Path("/createCoupon")
	@POST
	public Response createCoupon(Coupon coupon) throws CouponSystemException {
		try {
			companyFacade.createCoupon(coupon);

			Income income = new Income(1, companyFacade.getLogedID(), new Date(), Description.COMPANY_CREATE_COUPON,
					100);
			businessDelegate.storeIncome(income);

			String json = objectMapper.writeValueAsString(coupon);
			return Response.ok(json).status(Status.CREATED).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/removeCoupon/{id}")
	@DELETE
	public Response removeCoupon(@PathParam("id") long id) throws CouponSystemException {
		try {
			Coupon coupon = companyFacade.getCoupon(id);
			companyFacade.removeCoupon(coupon);
			String json = objectMapper.writeValueAsString(coupon);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/updateCoupon")
	@PUT
	public Response updateCoupon(Coupon coupon) throws CouponSystemException {
		try {
			companyFacade.updateCoupon(coupon);

			Income income = new Income(1, companyFacade.getLogedID(), new Date(), Description.COMPANY_UPDATE_COUPON,
					10);
			businessDelegate.storeIncome(income);

			String json = objectMapper.writeValueAsString(coupon);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getCoupon/{id}")
	@GET
	public Response getCoupon(@PathParam("id") int id) throws CouponSystemException {
		try {
			Coupon coupon = companyFacade.getCoupon(id);
			String json = objectMapper.writeValueAsString(coupon);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getAllCoupons")
	@GET
	public Response getAllCoupons() throws CouponSystemException {
		try {
			Set<Coupon> set = companyFacade.getAllCoupons();
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getCouponsByType/{couponType}")
	@GET
	public Response getCouponsByType(@PathParam("couponType") CouponType couponType) throws CouponSystemException {
		try {
			Set<Coupon> set = companyFacade.getCouponsByType(couponType);
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getCouponsByPrice/{price}")
	@GET
	public Response getCouponsByPrice(@PathParam("price") double price) throws CouponSystemException {
		try {
			Set<Coupon> set = companyFacade.getCouponsByPrice(price);
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}

	}

	@Path("/getCouponsUntilDate/{endDate}")
	@GET
	public Response getCouponsUntilDate(@PathParam("endDate") String endDateString)
			throws CouponSystemException, ParseException {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date endDate = simpleDateFormat.parse(endDateString);
			Set<Coupon> set = companyFacade.getCouponsUntilDate(endDate);
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException | ParseException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/viewIncome")
	@GET
	public Response viewIncomeByCompany() {
		try {
			Income[] incomes = businessDelegate.viewIncomeByCompany(companyFacade.getLogedID());
			String json = objectMapper.writeValueAsString(incomes);
			return Response.ok(json).build();
		} catch (JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
}
