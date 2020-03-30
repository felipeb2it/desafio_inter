package desafio.inter.service;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import desafio.inter.bean.CriptografiaRSA;
import desafio.inter.model.Usuario;



/**
 * Neste serviço guardo o valor da chave pública gerada pelo cliente,
 * a partir do momento que é feito um update do usuário, sem o envio de chave via http, 
 * é usada esta chave guardada previamente no Map.
 * 
 * Pensei em guardar no banco mas como não entendi bem a utilidade deste Endpoint preferi
 * fazer o mais simples seguindo ao máximo o requisito
 * 
 * @author felipe
 *
 */
@ApplicationScoped
public class ChaveService extends GenericService {
	
	@Inject
	CriptografiaRSA cripto;
	
	Map<Integer, PublicKey> idUsuarioChave;
	
	@PostConstruct
	private void init(){
		idUsuarioChave = new HashMap<>();
	}
	
	public boolean chaveUsuario(Integer idUsuario, byte[] chave) {
		Usuario usu = dao.find(Usuario.class, idUsuario);
		if(usu != null) {
			PublicKey chavePublica = cripto.chavePublicaConverter(chave);
			idUsuarioChave.put(idUsuario, chavePublica);
			return true;
		}
		return false;
		
	}

	public PublicKey getChaveUsuario(Integer idUsuario) {
		return idUsuarioChave.get(idUsuario);
	}

}
