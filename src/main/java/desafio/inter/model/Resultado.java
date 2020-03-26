package desafio.inter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RESULTADO")
public class Resultado extends EntidadeBase<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RESULTADO")
	private Integer id;
	
	@Column(name = "INTEIRO_N")
	private long inteiroN;
	
	@Column(name = "MULTIPLICADOR_K")
	private long multiplicadorK;
	
	@Column(name = "RESULT_DIGITO_UNICO")
	private long resultDigitoUnico;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public long getInteiroN() {
		return inteiroN;
	}
	public void setInteiroN(long inteiroN) {
		this.inteiroN = inteiroN;
	}
	public long getMultiplicadorK() {
		return multiplicadorK;
	}
	public void setMultiplicadorK(long multiplicadorK) {
		this.multiplicadorK = multiplicadorK;
	}
	public long getResultDigitoUnico() {
		return resultDigitoUnico;
	}
	public void setResultDigitoUnico(long resultDigitoUnico) {
		this.resultDigitoUnico = resultDigitoUnico;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}
