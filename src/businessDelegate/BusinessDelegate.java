package businessDelegate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import entities.Income;

public class BusinessDelegate {

	Client client = ClientBuilder.newClient();
	WebTarget webTarget = client.target("http://localhost:8888/Controller");

	public synchronized void storeIncome(Income income) {
		WebTarget storeIncome = webTarget.path("save");
		Invocation.Builder invocationBuilder = storeIncome.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(income, MediaType.APPLICATION_JSON));
	}

	public synchronized Income[] viewIncomeByCompany(long id) {
		WebTarget viewByCompany = webTarget.path("viewByCompany/" + id);
		Invocation.Builder invocationBuilder = viewByCompany.request(MediaType.APPLICATION_JSON);
		Income[] response = invocationBuilder.get(Income[].class);
		return response;
	}

	public synchronized Income[] viewIncomeByCustomer(long id) {
		WebTarget viewByCustomer = webTarget.path("viewByCustomer/" + id);
		Invocation.Builder invocationBuilder = viewByCustomer.request(MediaType.APPLICATION_JSON);
		Income[] response = invocationBuilder.get(Income[].class);
		return response;
	}

	public synchronized Income[] viewAllIncome() {
		WebTarget viewAll = webTarget.path("viewAll");
		Invocation.Builder invocationBuilder = viewAll.request(MediaType.APPLICATION_JSON);
		Income[] response = invocationBuilder.get(Income[].class);
		return response;
	}

}
