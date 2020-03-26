package desafio.inter.endpoint;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import desafio.inter.bean.GenericDaoJPA;
import desafio.inter.model.Resultado;
import desafio.inter.model.Usuario;
import desafio.inter.service.DigitoUnicoService;

@Path("/digito-unico")
public class DigitoUnicoEndpoint {

	@Inject
	GenericDaoJPA dao;
	
	@Inject
	DigitoUnicoService digitoUnicoService;
	
	@GET
	@Path("/{id-usuario}/{numero}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response calculoDigitoUnico(@PathParam("numero") Integer numero, @PathParam("id-usuario") Integer idUsuario) {
		Resultado result = digitoUnicoService.calculoDigitoUnico(idUsuario, numero);
		return Response.ok().entity(result).build();
	}

	@GET
	@Path("/{id-usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResultadoByUsuario(@HeaderParam("private-key") String privateKey, @PathParam("id-usuario") Integer idUsuario) {
		List<Resultado> resultados = digitoUnicoService.resultadoPorUsuario(idUsuario);
		return Response.ok().entity(resultados).build();
	}
    


}
