package desafio.inter.dto;

public class ChavesDto {
	
	private byte[] chavePublica;
	private byte[] chavePrivada;
	
	public byte[] getChavePublica() {
		return chavePublica;
	}
	public void setChavePublica(byte[] chavePublica) {
		this.chavePublica = chavePublica;
	}
	public byte[] getChavePrivada() {
		return chavePrivada;
	}
	public void setChavePrivada(byte[] chavePrivada) {
		this.chavePrivada = chavePrivada;
	}
}
