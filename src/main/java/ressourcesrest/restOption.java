package ressourcesrest;

import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
public class restOption {

    static OptionBusiness optB = new OptionBusiness();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllOptions(@QueryParam("domaine") String d) {

        List<Option> l;

        if (d == null || d.isEmpty()) {
            l = optB.getListeOptions();
        } else {
            l = optB.getOptionsByDomaine(d);
        }

        if (l.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.OK).entity(l).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response AjouterOption(Option option) {

        if (optB.addOption(option)) {
            return Response.status(Response.Status.CREATED).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int id) {
        if (optB.deleteOption(id)) {
        return Response.status(Response.Status.NO_CONTENT).build();}
        else {
        return Response.status(Response.Status.NOT_FOUND).entity("erreur de suppression").build();}
    }
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int id, Option option) {
        if (optB.updateOption(id, option)) {
            return Response.status(Response.Status.CREATED).build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int id) {

        Option option = optB.getOptionByCode(id);

        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(200).entity(option).build();
    }

}

