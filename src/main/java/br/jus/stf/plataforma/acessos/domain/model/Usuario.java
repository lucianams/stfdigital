package br.jus.stf.plataforma.acessos.domain.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.plataforma.identidades.domain.model.Pessoa;
import br.jus.stf.shared.UsuarioId;
import br.jus.stf.shared.stereotype.Entity;

@javax.persistence.Entity
@Table(name = "USUARIO", schema = "PLATAFORMA", uniqueConstraints = @UniqueConstraint(columnNames = {"SIG_USUARIO"}))
public class Usuario implements Entity<Usuario, UsuarioId>, Principal {
	
	@EmbeddedId
	private UsuarioId id;
	
	@ManyToOne
	@JoinColumn(name = "SEQ_PESSOA", nullable = false)
	private Pessoa pessoa;
	
	@Column(name = "SIG_USUARIO", nullable = false)
	private String login;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PERMISSAO_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PERMISSAO", nullable = false))
	private Set<Permissao> permissoes = new HashSet<Permissao>(0);
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "PAPEL_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_PAPEL", nullable = false))
	private Set<Papel> papeis = new HashSet<Papel>(0);
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinTable(name = "GRUPO_USUARIO", schema = "PLATAFORMA",
		joinColumns = @JoinColumn(name = "SEQ_USUARIO", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "SEQ_GRUPO", nullable = false))
	private Set<Grupo> grupos = new HashSet<Grupo>(0);
	
	Usuario() {
		
	}
	
	public Usuario(final UsuarioId id, final Pessoa pessoa, final String login) {
		Validate.notNull(id, "usuario.id.required");
		Validate.notNull(pessoa, "usuario.pessoa.required");
		Validate.notBlank(login, "usuario.login.required");
		
		this.id = id;
		this.pessoa = pessoa;
		this.login = login;
	}
	
	public Pessoa pessoa() {
		return pessoa;
	}
	
	public String login() {
		return login;
	}
	
	public Set<Papel> papeis() {
		return Collections.unmodifiableSet(papeis);
	}
	
	public void atribuirPapeis(final Set<Papel> papeis) {
		Validate.notEmpty(papeis, "usuario.papeis.required");
		
		this.papeis.addAll(papeis);
	}
	
	public void removerPapeis(final Set<Papel> papeis) {
		Validate.notEmpty(papeis, "usuario.papeis.required");
		
		this.papeis.removeAll(papeis);
	}
	
	public Set<Grupo> grupos() {
		return Collections.unmodifiableSet(grupos);
	}
	
	public void atribuirGrupos(final Set<Grupo> grupos) {
		Validate.notEmpty(grupos, "usuario.grupos.required");
		
		this.grupos.addAll(grupos);
	}
	
	public void removerGrupos(final Set<Grupo> grupos) {
		Validate.notEmpty(grupos, "usuario.grupos.required");
		
		this.grupos.removeAll(grupos);
	}
	
	@Override
	public Set<Permissao> permissoes() {
		Set<Permissao> permissoesCompletas = new HashSet<Permissao>();
		
		Optional.ofNullable(papeis).ifPresent(p -> p.forEach(papeis -> permissoesCompletas.addAll(papeis.permissoes())));
		Optional.ofNullable(grupos).ifPresent(g -> g.forEach(grupo -> permissoesCompletas.addAll(grupo.permissoes())));
		Optional.ofNullable(permissoes).ifPresent(p -> permissoesCompletas.addAll(p));
		
		return Collections.unmodifiableSet(permissoesCompletas);
	}
	
	@Override
	public void atribuirPermissoes(final Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "usuario.permissoes.required");
		
		this.permissoes.addAll(permissoes);
	}
	
	@Override
	public void removerPermissoes(Set<Permissao> permissoes) {
		Validate.notEmpty(permissoes, "usuario.permissoes.required");
		
		this.permissoes.removeAll(permissoes);
	}
	
	@Override
	public UsuarioId id() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).hashCode();
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
	
		Usuario other = (Usuario) obj;
		return sameIdentityAs(other);
	}

	@Override
	public boolean sameIdentityAs(Usuario other) {
		return other != null
				&& id.equals(other.id);
	}
	
}