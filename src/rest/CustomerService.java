package rest;

import java.util.Date;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import businessDelegate.BusinessDelegate;
import coupon.beans.Coupon;
import coupon.beans.CouponType;
import coupon.dbdao.CouponDBDAO;
import entities.Description;
import entities.Income;
import facade.CustomerFacade;
import system.exception.CouponSystemException;

@Path("/sec/serviceCustomer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerService {

	@Context
	private HttpServletRequest request;

	private BusinessDelegate businessDelegate = new BusinessDelegate();
	CustomerFacade customerFacade = null;
	CouponDBDAO CouponDBDAO = new CouponDBDAO();
	ObjectMapper objectMapper = new ObjectMapper();

	@PostConstruct
	public void getFacade() {
		HttpSession session = request.getSession(false);
		customerFacade = (CustomerFacade) session.getAttribute("customerFacade");
	}

	public CustomerService() {
	}

	@Path("/purchaseCoupon/{id}")
	@GET
	public Response purchaseCoupon(@PathParam("id") int id) throws CouponSystemException {
		try {
			Coupon coupon = CouponDBDAO.getCoupon(id);
			customerFacade.purchaseCoupon(coupon);
			
			Income income = new Income(1, customerFacade.getLogedID(), new Date(), Description.CUSTOMER_PURCHASE_COUPON, coupon.getPrice());
			businessDelegate.storeIncome(income);
			
			String json = objectMapper.writeValueAsString(coupon);
			return Response.ok(json).build();
		} catch (JsonProcessingException | CouponSystemException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getAllPurchasedCoupons")
	@GET
	public Response getAllPurchasedCoupons() throws CouponSystemException {
		try {
			Set<Coupon> set = customerFacade.getAllPurchasedCoupons();
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getAllPurchasedCouponsByType/{couponType}")
	@GET
	public Response getAllPurchasedCouponsByType(@PathParam("couponType") CouponType couponType)
			throws CouponSystemException {
		try {
			Set<Coupon> set = customerFacade.getAllPurchasedCouponsByType(couponType);
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (JsonProcessingException | CouponSystemException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}

	@Path("/getAllPurchasedCouponsByPrice/{price}")
	@GET
	public Response getAllPurchasedCouponsByPrice(@PathParam("price") double price) throws CouponSystemException {
		try {
			Set<Coupon> set = customerFacade.getAllPurchasedCouponsByPrice(price);
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
	
	@Path("/getAllCouponsInStore")
	@GET
	public Response getAllCouponsInStore() throws CouponSystemException{
		try {
			Set<Coupon> set = customerFacade.getAllCouponsInStore();
			String json = objectMapper.writeValueAsString(set);
			return Response.ok(json).build();
		} catch (CouponSystemException | JsonProcessingException e) {
			String json = String.format("{\"message\":\"%s\"}", e.getMessage());
			return Response.serverError().entity(json).build();
		}
	}
}
