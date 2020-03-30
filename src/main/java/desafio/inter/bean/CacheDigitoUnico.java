package desafio.inter.bean;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CacheDigitoUnico {
	
	private Map<Long, Long> cacheDigito;
	private Queue<Long> pilhaChaves;
	
	@PostConstruct
	private void init(){
		cacheDigito = new LinkedHashMap<>();
		pilhaChaves = new LinkedList<>();
	}
	
	public void adicionar(Long inteiroN, Long resultado) {
		if(cacheDigito.size() > 10) {
			cacheDigito.remove(pilhaChaves.poll());
		}
		pilhaChaves.offer(inteiroN);
		cacheDigito.put(inteiroN, resultado);
	}
	
	public Long verificarCache(Long inteiroN) {
		return cacheDigito.get(inteiroN);
	}
	
}
