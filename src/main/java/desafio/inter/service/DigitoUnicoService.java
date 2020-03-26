package desafio.inter.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import desafio.inter.bean.CalcularDigitoUnico;
import desafio.inter.model.Resultado;
import desafio.inter.model.Usuario;

@Stateless
public class DigitoUnicoService extends GenericService {

	@Inject
	CalcularDigitoUnico digito;
	
	public Resultado calculoDigitoUnico(Integer idUsuario, long numero) {
		
		long digitoResult = digito.digitoUnico(numero);
		Resultado result = new Resultado();
		if(idUsuario != null) {
			Usuario usu = dao.find(Usuario.class, idUsuario);
			if(usu != null) {
				result.setUsuario(usu);
			}
		}
		result.setInteiroN(numero);
		result.setResultDigitoUnico(digitoResult);
		dao.save(result);
		return result;
	}
	
	public List<Resultado> resultadoPorUsuario(Integer idUsuario) {
		Usuario usu = dao.find(Usuario.class, idUsuario);
		List<Resultado> resultados = dao.findByProperty(Resultado.class, "Usuario", usu);
		return resultados;
	}
}
