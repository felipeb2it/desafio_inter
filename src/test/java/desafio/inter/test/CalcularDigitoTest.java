package desafio.inter.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import desafio.inter.bean.CalcularDigitoUnico;


/**
 * Nestes testes eu faço 10.000 testes com números sequencias para testar
 * os cálculos do digito único.
 * @author felipe
 *
 */
public class CalcularDigitoTest {
	private CalcularDigitoUnico calcularDigitoUnico = new CalcularDigitoUnico();
	
	@Test
	void testarCalculoDigitoUnico() {
		for(int x = 1; x < 10000; x++) {
			assertTrue(calcularDigitoUnico.digitoUnico(x) < 10);
		}
	}
	
	@Test
	void testarCalculoDigitoUnicoNKP() {
		for(int x = 1; x < 10000; x++) {
			assertTrue(calcularDigitoUnico.digitoUnico(String.valueOf(x), 50) < 10);
		}
	}
	
}
