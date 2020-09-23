package hr.fer.zemris.java.hw16.rest;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.model.DataBase;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Retrieves all thumbnails for given tag
 * 
 * @author mfures
 *
 */
@Path("/thmbs")
public class Thumbnails {
	/**
	 * Retrieves all images for given tag if that tag is present
	 * 
	 * @param tag of images
	 * @return images as json
	 */
	@GET
	@Produces("application/json")
	public Response getThumbs(@QueryParam("tag") String tag) {
		List<String> images = DataBase.imagesForTag(tag);
		if (images == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(images);
		return Response.status(Status.OK).entity(jsonText).build();
	}

}
