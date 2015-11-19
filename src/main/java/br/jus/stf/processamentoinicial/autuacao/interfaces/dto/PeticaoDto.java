package br.jus.stf.processamentoinicial.autuacao.interfaces.dto;

import java.util.List;
import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@ApiModel(value = "Representa a petição enviada pelo peticionador")
public class PeticaoDto {
	
	@ApiModelProperty(value = "Id da petição")
	private Long id;
	
	@ApiModelProperty(value = "Número da petição")
	private Long numero;

	@ApiModelProperty(value = "Ano da petição")
	private Short ano;

	@ApiModelProperty(value = "Classe processual sugerida pelo peticionador")
	private String classe;
	
	@ApiModelProperty(value = "A lista de partes do polo ativo e a lista de partes do polo passivo")
	private Map<String, List<Long>> partes;
	
	@ApiModelProperty(value = "A lista de peças anexadas pelo peticionador")
	private List<PecaDto> pecas;
	
	@ApiModelProperty(value = "")
	private Long processoWorkflowId;
	
	PeticaoDto() {
		
	}
	
	public PeticaoDto(Long id, Long numero, Short ano, String classe, Map<String, List<Long>> partes, List<PecaDto> pecas, Long processoWorkflowId) {
		this.id = id;
		this.numero = numero;
		this.ano = ano;
		this.classe = classe;
		this.partes = partes;
		this.pecas = pecas;
		this.processoWorkflowId = processoWorkflowId;
	}
	
	public Long getId() {
		return id;
	}
	
	public Long getNumero() {
		return numero;
	}
	
	public Short ano() {
		return ano;
	}

	public String getClasse() {
		return classe;
	}
	
	public Map<String, List<Long>> getPartes() {
		return partes;
	}
	
	public List<PecaDto> getPecas() {
		return pecas;
	}
	
	public Long getProcessoWorkflowId() {
		return processoWorkflowId;
	}
	
}
