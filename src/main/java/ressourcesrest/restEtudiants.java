package ressourcesrest;

import entities.Etudiant;
import entities.Option;
import metiers.EtudiantBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("etudiants")
public class restEtudiants {
    static EtudiantBusiness  etudiantBusiness = new EtudiantBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterEtudiant(Etudiant etudiant) {

        if (etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Response.Status.CREATED).entity(etudiant).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        List<Etudiant> etudiants = etudiantBusiness.getAllEtudiants();
        return Response.status(200).entity(etudiants).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantById(@PathParam("id") String id) {

        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(id);

        if (etudiant == null) {
            return Response.status(404).build();
        } else {
            return Response.status(200).entity(etudiant).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response supprimerEtudiant(@PathParam("id") String id) {

        if (etudiantBusiness.deleteEtudiant(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {

        if (etudiantBusiness.updateEtudiant(id, etudiant)) {
            return Response.status(Response.Status.CREATED).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {

        Option option = new Option();
        option.setCodeOption(codeOption);

        List<Etudiant> etudiants = etudiantBusiness.getEtudiantsByOption(option);

        if (etudiants.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        else {
            return Response.status(200).entity(etudiants).build();
        }
    }
}
