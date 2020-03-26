package desafio.inter.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;

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
	
	public UsuarioDto readUsuario(Integer idUsuario, String key) {
		Usuario usuario = dao.find(Usuario.class, idUsuario);
		PrivateKey chave = cripto.chavePrivadaConverter(key.getBytes(StandardCharsets.UTF_8));
		String nome = cripto.decriptografa(usuario.getNome(), chave);
		String email = cripto.decriptografa(usuario.getEmail(), chave);
		if(nome == null || email == null) {
			return null;
		}
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setId(usuario.getId());
		usuarioDto.setNome(nome);
		usuarioDto.setEmail(email);
		return usuarioDto;
	}
	
	public UsuarioDto updateUsuario(Integer idUsuario, UsuarioDto usuarioDto, String key) {
		Usuario usuario = dao.find(Usuario.class, idUsuario);
		PrivateKey chave = cripto.chavePrivadaConverter(key.getBytes(StandardCharsets.UTF_8));
		String nome = cripto.decriptografa(usuario.getNome(), chave);
		String email = cripto.decriptografa(usuario.getEmail(), chave);
		if(nome == null || email == null) {
			return null;
		}
		usuario.setNome(cripto.criptografa(usuarioDto.getNome(), chave));
		usuario.setEmail(cripto.criptografa(usuarioDto.getEmail(), chave));
		dao.merge(usuario);
		return usuarioDto;
	}
	
	public ChavesDto createUsuario(UsuarioDto usuarioDto) {
		KeyPair key = cripto.geraChave();
		byte[] nome = cripto.criptografa(usuarioDto.getNome(), key.getPublic());
		byte[] email = cripto.criptografa(usuarioDto.getEmail(), key.getPublic());
		Usuario usuario = new Usuario();
		usuario.setNome(nome);
		usuario.setEmail(email);
		dao.save(usuario);
		ChavesDto chaves = new ChavesDto();
		chaves.setChavePrivada(key.getPrivate().getEncoded());
		chaves.setChavePublica(key.getPublic().getEncoded());
		return chaves;
	}
	
	public UsuarioDto deleteUsuario(Integer idUsuario, String key) {
		Usuario usuario = dao.find(Usuario.class, idUsuario);
		PrivateKey chave = cripto.chavePrivadaConverter(key.getBytes());
		String nome = cripto.decriptografa(usuario.getNome(), chave);
		if(nome != null) {
			dao.delete(Usuario.class, idUsuario);
			return new UsuarioDto();
		} else {
			return null;
		}
	}

}
