package br.jus.stf.plataforma.acessos.application;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.jus.stf.plataforma.acessos.domain.model.Permissao;
import br.jus.stf.plataforma.acessos.domain.model.RecursoRepository;
import br.jus.stf.plataforma.acessos.domain.model.TipoRecurso;
import br.jus.stf.plataforma.acessos.domain.model.Usuario;
import br.jus.stf.plataforma.acessos.domain.model.UsuarioRepository;
import br.jus.stf.plataforma.acessos.interfaces.dto.UsuarioDto;

/**
 * @author Lucas.Rodrigues
 *
 */
@Service
@Transactional
public class AcessosApplicationService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private RecursoRepository recursoRepository;
	
	public Set<Permissao> carregarPermissoesUsuario(String login) {
		Usuario usuario = usuarioRepository.findOne(login);
		return usuario.permissoes();
	}
	
	public Set<Permissao> carregarPermissoesRecurso(String nome, String tipo) {
		return Optional.ofNullable(recursoRepository.findOne(nome, TipoRecurso.valueOf(tipo)))
			.map(recurso -> recurso.permissoesExigidas()).orElse(Collections.emptySet());
	}
	
	public Set<String> carregarPapeisUsuario(String login) {
		Usuario usuario = usuarioRepository.findOne(login);
		return usuario.papeis().stream()
				.map(papel -> papel.nome())
				.collect(Collectors.toSet());
	}
	
	/**
	 * Recupera as informações do usuário.
	 * 
	 * @param login Login do usuário.
	 * @return Informações do usuário.
	 */
	public Usuario recuperarInformacoesUsuario(String login){
		return usuarioRepository.findOne(login);
	}

}