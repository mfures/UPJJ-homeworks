package hr.fer.zemris.java.hw16.rest;

import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import hr.fer.zemris.java.hw16.model.DataBase;
import hr.fer.zemris.java.hw16.model.Image;

/**
 * Handles get requests for image
 * 
 * @author mfures
 *
 */
@Path("/images")
public class Images {

	/**
	 * Retrieves info for image
	 * 
	 * @return info for image
	 */
	@GET
	@Produces("application/json")
	public Response getImageInfo(@QueryParam("name") String name) {
		Image image = DataBase.imageForName(name);
		if (image == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		Gson gson = new Gson();
		String jsonText = gson.toJson(image);
		return Response.status(Status.OK).entity(jsonText).build();
	}

}
