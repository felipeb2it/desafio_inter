package desafio.inter.bean;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


@ApplicationPath("/api")
public class RestApplication extends Application {

	/**
	 * 
	 * Tentativa de configurar o Swagger no Wildfly 18, tentei de várias formas mas ocorre erro no Deploy,
	 * por isso deixei comentado para que possam avaliar.
	 * @author felipe
	 *
	 */
	
//	public RestApplication() {
//		BeanConfig conf = new BeanConfig();
//		conf.setTitle("Banco Inter");
//		conf.setDescription("API Banco Inter");
//		conf.setVersion("1.0.0");
//		conf.setHost("localhost:8080");
//		conf.setBasePath("/inter/api");
//		conf.setSchemes(new String[] { "http" });
//		conf.setResourcePackage(UsuarioEndpoint.class.getPackage().getName());
//		conf.setScan(true);
//	}
//
//	@Override
//	public Set<Class<?>> getClasses() {
//		Set<Class<?>> resources = new HashSet<>();
//		resources.add(DigitoUnicoEndpoint.class);
//		resources.add(UsuarioEndpoint.class);
//
//		resources.add(ApiListingResource.class);
//		resources.add(SwaggerSerializers.class);
//		return resources;
//	}
}
