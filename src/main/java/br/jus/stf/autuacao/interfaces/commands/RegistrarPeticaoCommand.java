package br.jus.stf.autuacao.interfaces.commands;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 20.07.2015
 */
@ApiModel(value = "Contém as informações para registro de uma nova petição digital")
public class RegistrarPeticaoCommand {

	@NotBlank
	@ApiModelProperty(value = "Classe processual sugerida pelo peticionador", required=true)
	private String classe;
	
	@NotEmpty
	@ApiModelProperty(value = "Duas listas: uma lista com partes do polo ativo e outra com partes do polo passivo", required=true)
	private Map<String, List<String>> partes;
	
	@NotEmpty
	@ApiModelProperty(value = "A lista de documentos que serão anexados pelo peticionador", required=true)
	private List<String> documentos;

	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public void setPartes(Map<String, List<String>> partes) {
		this.partes = partes;
	}
	
	public Map<String, List<String>> getPartes() {
		return partes;
	}
	
	public void setDocumentos(List<String> documentos) {
		this.documentos = documentos;
	}
	
	public List<String> getDocumentos() {
		return documentos;
	}
	
}