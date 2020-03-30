package desafio.inter.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.ejb.Stateless;
import javax.inject.Inject;

import desafio.inter.bean.CriptografiaRSA;
import desafio.inter.dto.ChavesDto;
import desafio.inter.dto.UsuarioDto;
import desafio.inter.model.Usuario;

@Stateless
public class UsuarioService extends GenericService {
	
	@Inject
	CriptografiaRSA cripto;
	
	@Inject
	ChaveService chaveService;
	
	public UsuarioDto readUsuario(Integer idUsuario, String key) {
		Usuario usuario = dao.find(Usuario.class, idUsuario);
		PrivateKey chave = cripto.chavePrivadaConverter(key.getBytes(StandardCharsets.UTF_8));
		String nome = cripto.decriptografa(usuario.getNome().getBytes(StandardCharsets.UTF_8), chave);
		String email = cripto.decriptografa(usuario.getEmail().getBytes(StandardCharsets.UTF_8), chave);
		if(nome == null || email == null) {
			return null;
		}
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(usuario.getId());
		usuarioDto.setNome(nome);
		usuarioDto.setEmail(email);
		return usuarioDto;
	}
	
	public UsuarioDto updateUsuario(UsuarioDto usuarioDto, String key) {
		Usuario usuario = dao.find(Usuario.class, usuarioDto.getId());
		PublicKey chavePublica = null;
		if(key != null) {
			PublicKey chaveRequest = cripto.chavePublicaConverter(key.getBytes(StandardCharsets.UTF_8));
			usuario.setNome(Base64.getEncoder().encodeToString(cripto.criptografa(usuarioDto.getNome(), chaveRequest)));
			usuario.setNome(Base64.getEncoder().encodeToString(cripto.criptografa(usuarioDto.getEmail(), chaveRequest)));
		} else {
			chavePublica = chaveService.getChaveUsuario(usuarioDto.getId());
		}
		if(chavePublica != null) {
			usuario.setNome(Base64.getEncoder().encodeToString(cripto.criptografa(usuarioDto.getNome(), chavePublica)));
			usuario.setEmail(Base64.getEncoder().encodeToString(cripto.criptografa(usuarioDto.getEmail(), chavePublica)));
		} else if (usuario.getNome() == null){
			return null;
		}
		dao.merge(usuario);
		
		return usuarioDto;
	}
	
	public ChavesDto createUsuario(UsuarioDto usuarioDto) {
		KeyPair key = cripto.geraChave();
		byte[] nome = cripto.criptografa(usuarioDto.getNome(), key.getPublic());
		byte[] email = cripto.criptografa(usuarioDto.getEmail(), key.getPublic());
		Usuario usuario = new Usuario();
		usuario.setNome(Base64.getEncoder().encodeToString(nome));
		usuario.setEmail(Base64.getEncoder().encodeToString(email));
		dao.save(usuario);
		ChavesDto chaves = new ChavesDto();
		chaves.setChavePrivada(key.getPrivate().getEncoded());
		chaves.setChavePublica(key.getPublic().getEncoded());
		return chaves;
	}
	
	public Boolean deleteUsuario(Integer idUsuario) {
		return dao.delete(Usuario.class, idUsuario); 
	}

}
