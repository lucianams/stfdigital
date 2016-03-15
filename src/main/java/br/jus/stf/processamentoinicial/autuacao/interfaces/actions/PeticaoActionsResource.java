package br.jus.stf.processamentoinicial.autuacao.interfaces.actions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.plataforma.shared.actions.support.ResourcesMode;
import br.jus.stf.processamentoinicial.autuacao.domain.model.PeticaoStatus;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AssinarDevolucaoPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.AutuarPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.FinalizarTextoDevolucaoPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.GerarTextoDevolucaoPeticaoCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TextoDto;

@ActionController(groups = "peticao")
public class PeticaoActionsResource {
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;
	
	@ActionMapping(id = "preautuar", name = "Preautuar Petição Física", resourcesMode = ResourcesMode.One)
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo(), command.getPreferencias());	
	}
	
	@ActionMapping(id = "preautuar-recursal", name = "Preautuar Petição Física Recursal", resourcesMode = ResourcesMode.One)
	public void preautuarRecursal(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo(), command.getPreferencias());
	}
	
	@ActionMapping(id = "autuar", name = "Autuar Petição", resourcesMode = ResourcesMode.One)
	public void autuar(AutuarPeticaoCommand command) {
		peticaoServiceFacade.autuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo()); 
	}
	
	@ActionMapping(id = "gerar-texto-devolucao", name = "Gerar Texto de Devolução de Petição", resourcesMode = ResourcesMode.One)
	public TextoDto gerarTextoDevolucao(GerarTextoDevolucaoPeticaoCommand command) {
		return peticaoServiceFacade.gerarTextoDevolucao(command.getPeticaoId(), command.getModeloId(), command.getSubstituicoes()); 
	}
	
	@ActionMapping(id = "finalizar-texto-devolucao", name = "Finalizar Texto da Devolução", resourcesMode = ResourcesMode.OneOrMany)
	public void finalizarTextoDevolucao(FinalizarTextoDevolucaoPeticaoCommand command) {
		peticaoServiceFacade.finalizarTextoDevolucao(command.getPeticaoId(), command.getModeloId(), command.getTextoId(), command.getNumeroDocumento());
	}
	
	@ActionMapping(id = "devolver-peticao", name = "Devolver Petição", resourcesMode = ResourcesMode.OneOrMany)
	@FiltrarPeticaoPorStatus(PeticaoStatus.ASSINAR_DEVOLUCAO)
	public void devolverPeticao(List<AssinarDevolucaoPeticaoCommand> commands) {
		commands.forEach(c -> peticaoServiceFacade.devolver(c.getPeticaoId(), c.getDocumentoId()));
	}
}
