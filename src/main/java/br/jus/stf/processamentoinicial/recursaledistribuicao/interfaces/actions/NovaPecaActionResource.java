package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.OrganizarPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.SalvarPecasCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.facade.ProcessoServiceFacade;

/**
 * Controle de ações de peças processuais.
 * 
 * @author Anderson.Araujo
 * @since 26.02.2016
 *
 */
@ActionController(groups = "novapeca")
public class NovaPecaActionResource {
	
	@Autowired
	private ProcessoServiceFacade processoServiceFacade;
	
	@ActionMapping(id = "inserir-pecas", name = "Inserir Peças Processuais", resourcesMode = ResourcesMode.One)
	public void inserirPecas(SalvarPecasCommand command) {
		processoServiceFacade.inserirPecas(command.getProcessoId(), command.getPecas());
	}
	
	@ActionMapping(id = "organizar-pecas", name = "Organizar Peças", resourcesMode = ResourcesMode.None)
	public void organizarPecas(OrganizarPecasCommand command) {
		processoServiceFacade.organizarPecas(command.getProcessoId(), command.getPecasOrganizadas(), command.isConcluirTarefa());
	}
	
}