package br.jus.stf.processamentoinicial.suporte.interfaces.dto;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel("Representa um meio de tramitação")
public class MeioTramitacaoDto {

	@ApiModelProperty("O nome do meio de tramitação")
	private String nome;

	@ApiModelProperty("A descrição do meio de tramitação")
	private String descricao;

	public MeioTramitacaoDto(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
