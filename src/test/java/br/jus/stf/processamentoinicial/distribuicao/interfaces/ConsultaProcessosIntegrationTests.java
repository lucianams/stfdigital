package br.jus.stf.processamentoinicial.distribuicao.interfaces;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;

/**
 * Disponibiliza operações para validação dos cenários de consulta processual.
 * 
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 29.09.2015
 */
public class ConsultaProcessosIntegrationTests extends AbstractIntegrationTests {

	/**
	 * Teste a consulta de processos pelo identificador. O cenário abaixo valida todos os campos de retorno.
	 */
	@Test
	public void testConsultaProcessos() throws Exception {
		mockMvc.perform(get("/api/processos/1")).andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.classe", is("ACO")))
			.andExpect(jsonPath("$.numero", is(200)))
			.andExpect(jsonPath("$.relator", is(28)))
			.andExpect(jsonPath("$.partes.*", hasSize(2)))
			.andExpect(jsonPath("$.pecas", hasSize(2)))
			.andExpect(jsonPath("$.situacao", is("Distribuído")));
	}
	
	@Test
	public void recuperarListaStatusProcesso() throws Exception {
		//Recupera a lista de status do processo.
		this.mockMvc.perform(get("/api/processos/status")
			.header("login", "autuador")).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(6)));
	}
	
}
