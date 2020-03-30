package desafio.inter.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import desafio.inter.bean.CacheDigitoUnico;
import desafio.inter.bean.CalcularDigitoUnico;
import desafio.inter.model.Resultado;
import desafio.inter.model.Usuario;

@Stateless
public class DigitoUnicoService extends GenericService {

	@Inject
	CalcularDigitoUnico digito;
	
	@Inject
	CacheDigitoUnico cache;
	
	public Resultado calculoDigitoUnico(Integer idUsuario, long numero) {
		Long resultadoCalculo = cache.verificarCache(numero);
		if(resultadoCalculo == null) {
			resultadoCalculo = digito.digitoUnico(numero);
			cache.adicionar(numero, resultadoCalculo);
		}
		
		Resultado result = new Resultado();
		if(idUsuario != null) {
			Usuario usu = dao.find(Usuario.class, idUsuario);
			if(usu != null) {
				result.setUsuario(usu);
			}
		}
		result.setInteiroN(numero);
		result.setResultDigitoUnico(resultadoCalculo);
		dao.save(result);
		return result;
	}
	
	public List<Resultado> resultadoPorUsuario(Integer idUsuario) {
		Usuario usu = dao.find(Usuario.class, idUsuario);
		List<Resultado> resultados = dao.findByProperty(Resultado.class, "usuario", usu);
		return resultados;
	}
}
