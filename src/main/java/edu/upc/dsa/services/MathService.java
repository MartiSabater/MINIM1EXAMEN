package edu.upc.dsa.services;

import edu.upc.dsa.MathManager;
import edu.upc.dsa.MathManagerImpl;
import edu.upc.dsa.models.Alumne;
import edu.upc.dsa.models.Institud;
import edu.upc.dsa.models.OperacioMatematica;
import edu.upc.dsa.services.dto.AlumneDTO;
import edu.upc.dsa.services.dto.InstitudDTO;
import edu.upc.dsa.services.dto.InstitudRankingDTO;
import edu.upc.dsa.services.dto.MessageDTO;
import edu.upc.dsa.services.dto.OperacioMatematicaDTO;
import edu.upc.dsa.services.dto.request.CreateAlumneRequest;
import edu.upc.dsa.services.dto.request.CreateInstitudRequest;
import edu.upc.dsa.services.dto.request.CreateOperacioRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Api(value = "/math", description = "Servei REST per gestionar alumnes, instituts i operacions RPN")
@Path("/math")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MathService {

    private final MathManager manager;
    final static Logger logger = Logger.getLogger(MathService.class);

    public MathService() {
        this.manager = MathManagerImpl.getInstance();
    }

    @POST
    @Path("/instituts")
    @ApiOperation(value = "Crear un institut")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Institut creat"),
            @ApiResponse(code = 400, message = "Peticio incorrecta")
    })
    public Response addInstitud(CreateInstitudRequest request) {
        if (request == null || isBlank(request.getNom())) {
            return badRequest("El camp 'nom' es obligatori");
        }

        Institud institud = this.manager.AddInstitud(request.getNom().trim());
        return Response.status(Response.Status.CREATED)
                .entity(new InstitudDTO(institud))
                .build();
    }

    @GET
    @Path("/instituts/{id}")
    @ApiOperation(value = "Obtenir un institut per id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Institut trobat"),
            @ApiResponse(code = 404, message = "Institut no trobat")
    })
    public Response getInstitud(@PathParam("id") String id) {
        Institud institud = this.manager.GetInstitud(id);
        if (institud == null) {
            return notFound("No existeix cap institut amb id=" + id);
        }

        return Response.ok(new InstitudDTO(institud)).build();
    }

    @POST
    @Path("/alumnes")
    @ApiOperation(value = "Crear un alumne")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Alumne creat"),
            @ApiResponse(code = 400, message = "Peticio incorrecta"),
            @ApiResponse(code = 404, message = "Institut no trobat")
    })
    public Response addAlumne(CreateAlumneRequest request) {
        if (request == null || isBlank(request.getNom()) || isBlank(request.getInstitutId())) {
            return badRequest("Els camps 'nom' i 'institutId' son obligatoris");
        }

        Institud institud = this.manager.GetInstitud(request.getInstitutId());
        if (institud == null) {
            return notFound("No existeix cap institut amb id=" + request.getInstitutId());
        }

        Alumne alumne = this.manager.AddAlumne(request.getNom().trim(), request.getInstitutId().trim());
        return Response.status(Response.Status.CREATED)
                .entity(new AlumneDTO(alumne))
                .build();
    }

    @GET
    @Path("/alumnes/{id}")
    @ApiOperation(value = "Obtenir un alumne per id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Alumne trobat"),
            @ApiResponse(code = 404, message = "Alumne no trobat")
    })
    public Response getAlumne(@PathParam("id") String id) {
        Alumne alumne = this.manager.GetAlumne(id);
        if (alumne == null) {
            return notFound("No existeix cap alumne amb id=" + id);
        }

        return Response.ok(new AlumneDTO(alumne)).build();
    }

    @POST
    @Path("/operacions")
    @ApiOperation(value = "Registrar una operacio matematica pendent")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Operacio creada"),
            @ApiResponse(code = 400, message = "Peticio incorrecta"),
            @ApiResponse(code = 404, message = "Alumne o institut no trobat")
    })
    public Response addOperacio(CreateOperacioRequest request) {
        if (request == null || isBlank(request.getId()) || isBlank(request.getExpresioRPN())
                || isBlank(request.getAlumneId()) || isBlank(request.getInstitutId())) {
            return badRequest("Els camps 'id', 'expresioRPN', 'alumneId' i 'institutId' son obligatoris");
        }

        Alumne alumne = this.manager.GetAlumne(request.getAlumneId().trim());
        if (alumne == null) {
            return notFound("No existeix cap alumne amb id=" + request.getAlumneId());
        }

        Institud institud = this.manager.GetInstitud(request.getInstitutId().trim());
        if (institud == null) {
            return notFound("No existeix cap institut amb id=" + request.getInstitutId());
        }

        if (!request.getInstitutId().trim().equals(alumne.getInstitutId())) {
            return badRequest("L'alumne no pertany a l'institut indicat");
        }

        OperacioMatematica operacio = this.manager.requerirOperacioMatematica(
                request.getId().trim(),
                request.getExpresioRPN().trim(),
                request.getAlumneId().trim(),
                request.getInstitutId().trim(),
                false
        );

        return Response.status(Response.Status.CREATED)
                .entity(new OperacioMatematicaDTO(operacio))
                .build();
    }

    @POST
    @Path("/operacions/processar")
    @ApiOperation(value = "Processar la seguent operacio pendent en ordre FIFO")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operacio processada"),
            @ApiResponse(code = 404, message = "No hi ha operacions pendents")
    })
    public Response processarOperacio() {
        OperacioMatematica operacio = this.manager.procesarOperacioMatematica();
        if (operacio == null) {
            return notFound("No hi ha operacions pendents per processar");
        }

        logger.info("Operacio processada amb id=" + operacio.getId());
        return Response.ok(new OperacioMatematicaDTO(operacio)).build();
    }

    @GET
    @Path("/operacions/alumnes/{alumneId}")
    @ApiOperation(value = "Llistar operacions d'un alumne")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Llista retornada"),
            @ApiResponse(code = 404, message = "Alumne no trobat")
    })
    public Response getOperacionsPerAlumne(@PathParam("alumneId") String alumneId) {
        Alumne alumne = this.manager.GetAlumne(alumneId);
        if (alumne == null) {
            return notFound("No existeix cap alumne amb id=" + alumneId);
        }

        List<OperacioMatematicaDTO> response = new LinkedList<>();
        for (OperacioMatematica operacio : this.manager.llistarOperacionsPerAlumne(alumneId)) {
            response.add(new OperacioMatematicaDTO(operacio));
        }

        return Response.ok(response).build();
    }

    @GET
    @Path("/operacions/instituts/{institutId}")
    @ApiOperation(value = "Llistar operacions d'un institut")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Llista retornada"),
            @ApiResponse(code = 404, message = "Institut no trobat")
    })
    public Response getOperacionsPerInstitud(@PathParam("institutId") String institutId) {
        Institud institud = this.manager.GetInstitud(institutId);
        if (institud == null) {
            return notFound("No existeix cap institut amb id=" + institutId);
        }

        List<OperacioMatematicaDTO> response = new LinkedList<>();
        for (OperacioMatematica operacio : this.manager.llistarOperacionsPerInstitud(institutId)) {
            response.add(new OperacioMatematicaDTO(operacio));
        }

        return Response.ok(response).build();
    }

    @GET
    @Path("/instituts/ranking")
    @ApiOperation(value = "Llistar instituts ordenats pel nombre d'operacions processades")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Ranking retornat")
    })
    public Response getRankingInstituds() {
        List<InstitudRankingDTO> response = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : this.manager.llistarInstituds()) {
            response.add(new InstitudRankingDTO(entry.getKey(), entry.getValue()));
        }

        return Response.ok(response).build();
    }

    private Response badRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new MessageDTO(message))
                .build();
    }

    private Response notFound(String message) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new MessageDTO(message))
                .build();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}