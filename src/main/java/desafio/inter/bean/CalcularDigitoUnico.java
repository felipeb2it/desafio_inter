package desafio.inter.bean;

import javax.ejb.Stateless;

@Stateless
public class CalcularDigitoUnico {

	public long digitoUnico(double num) {
		long soma;
		do {
			soma = 0;
	        while(num>0) {
	            soma += (num % 10);
	            num /= 10;
	        }
	        num = soma;
		} while(soma > 9);
        return soma;
	}
	
	public long digitoUnico(String n, long k) {
		
		StringBuilder concatAll = new StringBuilder();
		for(int i = 0; i < k; i++) {
			concatAll.append(n);
		}
		
		return digitoUnico(Double.valueOf(concatAll.toString()));
	}

}
