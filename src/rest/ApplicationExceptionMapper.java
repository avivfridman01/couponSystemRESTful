package rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import system.exception.CouponSystemException;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<CouponSystemException> {

	@Override
	public Response toResponse(CouponSystemException exception) {
		return Response.status(500).entity(exception.getMessage()).type(MediaType.TEXT_PLAIN).build();
	}

}
