package desafio.inter.endpoint;

import java.nio.charset.StandardCharsets;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import desafio.inter.bean.GenericDaoJPA;
import desafio.inter.dto.ChavesDto;
import desafio.inter.dto.UsuarioDto;
import desafio.inter.service.ChaveService;
import desafio.inter.service.UsuarioService;

@Path("/usuarios")
public class UsuarioEndpoint {

	@Inject
	GenericDaoJPA dao;
	
	@Inject
	UsuarioService usuarioService;
	
	@Inject
	ChaveService chaveService;

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response readUsuario(@HeaderParam("private-key") String privateKey, @PathParam("id") Integer id) {
		UsuarioDto usu = usuarioService.readUsuario(id, privateKey);
		if(usu == null) {
			return Response.serverError().build();
		} else {
			return Response.ok().entity(usu).build();
		}
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(@HeaderParam("public-key") String publicKey, UsuarioDto user) {
		UsuarioDto usu = usuarioService.updateUsuario(user, publicKey);
		if(usu == null) {
			return Response.serverError().build();
		} else {
			return Response.ok().entity(usu).build();
		}
	}
	
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuario(UsuarioDto usuario) {
    	ChavesDto chaves = usuarioService.createUsuario(usuario);
        return Response.status(201).entity(chaves).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuario(@PathParam("id") Integer id) {
    	UsuarioDto usu = null;
    	Boolean apagou = null;
    	if(id != null) {
    		apagou = usuarioService.deleteUsuario(id);
    	} else {
    		return Response.status(404).build();
    	}
		if(apagou == null) {
			return Response.serverError().build();
		} else if(apagou == false) {
			return Response.status(404).build();
		} else {
			return Response.ok().entity(usu).build();
		}
    }
    
	@PUT
    @Path("/chave/usuario/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response definirChave(@HeaderParam("public-key") String publicKey, @PathParam("id") Integer id) {
		if(id == null || publicKey == null || publicKey.isEmpty()) {
			return Response.status(404).build();
		} else {
			boolean chaveSalva = chaveService.chaveUsuario(id, publicKey.getBytes(StandardCharsets.UTF_8));
			if(chaveSalva) {
				return Response.ok().build();
			} else {
				return Response.status(404).build();
			}
		}
    }

}
