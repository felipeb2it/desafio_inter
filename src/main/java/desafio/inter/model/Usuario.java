package desafio.inter.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USUARIO")
public class Usuario extends EntidadeBase<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private Integer id;
	private byte[] nome;
	private byte[] email;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<Resultado> resultados;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public byte[] getNome() {
		return nome;
	}
	public void setNome(byte[] nome) {
		this.nome = nome;
	}
	public byte[] getEmail() {
		return email;
	}
	public void setEmail(byte[] email) {
		this.email = email;
	}
	public List<Resultado> getResultados() {
		return resultados;
	}
	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}
	
}
