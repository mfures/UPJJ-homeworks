package hr.fer.zemris.java.hw16.rest;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import hr.fer.zemris.java.hw16.model.DataBase;

/**
 * Handles get requests for tags
 * 
 * @author mfures
 *
 */
@Path("/alltags")
public class AllTags {

	/**
	 * Retrieves all tags as JSON
	 * 
	 * @return all tags
	 */
	@GET
	@Produces("application/json")
	public Response getTags() {
		Gson gson = new Gson();
		String jsonText = gson.toJson(DataBase.getTags());
		return Response.status(Status.OK).entity(jsonText).build();
	}
}
