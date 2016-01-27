package br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.actions;

import org.springframework.beans.factory.annotation.Autowired;

import br.jus.stf.jurisprudencia.controletese.domain.model.AssuntoRepository;
import br.jus.stf.plataforma.shared.actions.annotation.ActionController;
import br.jus.stf.plataforma.shared.actions.annotation.ActionMapping;
import br.jus.stf.processamentoinicial.autuacao.domain.PessoaAdapter;
import br.jus.stf.processamentoinicial.autuacao.interfaces.commands.PreautuarPeticaoFisicaCommand;
import br.jus.stf.processamentoinicial.autuacao.interfaces.facade.PeticaoServiceFacade;
import br.jus.stf.processamentoinicial.recursaledistribuicao.application.ProcessoApplicationService;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AnalisarPressupostosFormaisCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.AutuarProcessoCriminalEleitoralCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.commands.DistribuirPeticaoCommand;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.dto.ProcessoDto;
import br.jus.stf.processamentoinicial.recursaledistribuicao.interfaces.facade.ProcessoServiceFacade;

@ActionController(groups = "processo")
public class ProcessoActionsResource {

	@Autowired
	private ProcessoServiceFacade processoServiceFacade;
	
	@Autowired
	private ProcessoApplicationService processoApplicationService;
	
	@Autowired
	private AssuntoRepository assuntoRepository;
	
	@Autowired
	private PessoaAdapter pessoaAdapter;
	
	@Autowired
	private PeticaoServiceFacade peticaoServiceFacade;
	
	@ActionMapping(id = "preautuar-recursal", name = "Preautuar Petição Física Recursal")
	public void preautuar(PreautuarPeticaoFisicaCommand command) {
		peticaoServiceFacade.preautuar(command.getPeticaoId(), command.getClasseId(), command.isValida(), command.getMotivo(), command.getPreferencias());
	}
	
	@ActionMapping(id = "autuar-recursal-criminal-eleitoral", name = "Autuar Petição Física Recursal Criminal Eleitoral")
	public void autuarRecursalCriminalEleitoral(AutuarProcessoCriminalEleitoralCommand command) {
		processoApplicationService.autuar(command.getProcessoId(), command.getAssuntos(), command.getPartesPoloAtivo(), command.getPartesPoloPassivo());
	}
	
	@ActionMapping(id = "analisar-pressupostos", name = "Analisar Pressupostos")
	public void analisarPressupostosFormais(AnalisarPressupostosFormaisCommand command) {
		processoApplicationService.analisarPressupostosFormais(command.getProcessoId(), command.getClassificacao().toUpperCase(), 
				command.getMotivos(), command.getObservacao(), false);
	}
	
	@ActionMapping(id = "distribuir-processo", name = "Distribuir Processo")
	public ProcessoDto distribuir(DistribuirPeticaoCommand command) {
		return processoServiceFacade.distribuir(command.getTipoDistribuicao(), command.getPeticaoId(), command.getJustificativa(),
				command.getMinistrosCandidatos(), command.getMinistrosImpedidos(), command.getProcessosPreventos());
	}
}
