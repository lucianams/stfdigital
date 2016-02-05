package br.jus.stf.processamentoinicial.autuacao.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.jus.stf.processamentoinicial.suporte.domain.model.Peca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPeca;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoPolo;
import br.jus.stf.processamentoinicial.suporte.domain.model.TipoProcesso;
import br.jus.stf.shared.ClasseId;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.PessoaId;
import br.jus.stf.shared.PeticaoId;

/**
 * Testes unitários da petição eletrônica.
 * 
 * @author Tomas.Godoi
 *
 */
public class PeticaoEletronicaUnitTests {
	
	private Set<PartePeticao> partes;
	private List<PecaPeticao> pecas;
	private Long idDocumentoAtual;
	
	private PecaPeticao peca;
	
	@Before
	public void setUp() {
		idDocumentoAtual = 0L;
		
		partes = new HashSet<PartePeticao>(0);
		partes.add(new PartePeticao(new PessoaId(1L), TipoPolo.POLO_ATIVO));
		partes.add(new PartePeticao(new PessoaId(2L), TipoPolo.POLO_PASSIVO));
		partes.add(new PartePeticao(new PessoaId(3L), TipoPolo.POLO_PASSIVO));
		
		pecas = new LinkedList<PecaPeticao>();
		
		peca = criarPecaPI();
		
		pecas.add(peca);
	}

	private PecaPeticao criarPecaPI() {
		return new PecaPeticao(new DocumentoId(proximoIdDocumento()), new TipoPeca(1L, "Petição inicial"), "Petição inicial");
	}
	
	private PecaPeticao criarPecaDAR() {
		return new PecaPeticao(new DocumentoId(proximoIdDocumento()), new TipoPeca(6L, "Decisão ou ato reclamado"), "Decisão ou ato reclamado");
	}

	@Test
	public void criaPeticaoEletronicaSemRepresentacaoValida() {
		PeticaoEletronica peticaoEletronica = criarPeticaoPadrao();

	    assertNotNull(peticaoEletronica);
	    assertEquals(peticaoEletronica.id(), new PeticaoId(1L));
	    assertEquals(peticaoEletronica.numero(), new Long(5));
	    assertEquals(peticaoEletronica.classeSugerida(), new ClasseId("HC"));
	    assertFalse(peticaoEletronica.hasRepresentacao());
	    assertNull(peticaoEletronica.orgaoRepresentado());
	    assertEquals(peticaoEletronica.partesPoloAtivo().size(), 1);
		assertEquals(peticaoEletronica.partesPoloPassivo().size(), 2);
		assertEquals(peticaoEletronica.pecas(), pecas);
		assertTrue(peticaoEletronica.isEletronica());
	}
	
	@Test
	public void criaPeticaoEletronicaComRepresentacaoValida() {
		PeticaoEletronica peticaoEletronica = new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, new Orgao(1L, "PGR"), TipoProcesso.ORIGINARIO);

	    assertNotNull(peticaoEletronica);
	    assertEquals(peticaoEletronica.id(), new PeticaoId(1L));
	    assertEquals(peticaoEletronica.numero(), new Long(5));
	    assertEquals(peticaoEletronica.classeSugerida(), new ClasseId("HC"));
	    assertTrue(peticaoEletronica.hasRepresentacao());
	    assertEquals(peticaoEletronica.orgaoRepresentado(), new Orgao(1L, "PGR"));
	    assertEquals(peticaoEletronica.partesPoloAtivo().size(), 1);
		assertEquals(peticaoEletronica.partesPoloPassivo().size(), 2);
		assertEquals(peticaoEletronica.pecas(), pecas);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaComRepresentacaoSemOrgao() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, null, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaSemClasseSugerida() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", null, partes, pecas, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaComPartesNulo() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), null, pecas, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarPeticaoEletronicaComPartesVazio() {
		partes.clear();
		
		criarPeticaoPadrao();
	}
	
	@Test(expected = NullPointerException.class)
	public void tentaCriarPeticaoEletronicaComPecasNulo() {
		new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, null, TipoProcesso.ORIGINARIO);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tentaCriarPeticaoEletronicaComPecasVazio() {
		pecas.clear();
		
		criarPeticaoPadrao();
	}
	
	@Test
	public void criarPeticaoEletronicaUmaPecaNumeradaCorretamente() {
		PeticaoEletronica peticao = criarPeticaoPadrao();
		Assert.assertEquals("Peça deveria ter sido ordenada.", new Long(1L), peticao.pecas().iterator().next().numeroOrdem());
	}

	private PeticaoEletronica criarPeticaoPadrao() {
		return new PeticaoEletronica(new PeticaoId(1L), 5L, "PETICIONADOR", new ClasseId("HC"), partes, pecas, TipoProcesso.ORIGINARIO);
	}
	
	@Test
	public void criarPeticaoEletronicaVariasPecasNumeradasCorretamente() {
		incluirPecaCustas();
		PeticaoEletronica peticao = criarPeticaoPadrao();
		Assert.assertEquals("Peça 1 deveria ter sido ordenada com valor 1.", new Long(1L), recuperarPecaPI(peticao).numeroOrdem());
		Assert.assertEquals("Peça 2 deveria ter sido ordenada com valor 2.", new Long(2L), pecaPorTipo(peticao.pecas(), 2L).numeroOrdem());
	}
	
	@Test
	public void removerPecasDaPeticaoRenumeradasCorretamente() {
		Peca pecaCustas = incluirPecaCustas();
		incluirPecaAtoCoator();
		PeticaoEletronica peticao = criarPeticaoPadrao();
		peticao.removerPeca(pecaCustas);
		Assert.assertEquals("Peça 1 deveria ter sido ordenada com valor 1.", new Long(1L), recuperarPecaPI(peticao).numeroOrdem());
		Assert.assertEquals("Peça 3 deveria ter sido reordenada com valor 2.", new Long(2L), recuperarPecaAC(peticao).numeroOrdem());
	}

	@Test
	public void substituirPecaDaPeticaoRenumerasCorretamente() {
		Peca pecaCustas = incluirPecaCustas();
		incluirPecaAtoCoator();
		PeticaoEletronica peticao = criarPeticaoPadrao();
		Peca pecaCustasNova = new PecaPeticao(new DocumentoId(proximoIdDocumento()), new TipoPeca(2L, "Custas"), "Custas Nova");
		peticao.substituirPeca(pecaCustas, pecaCustasNova);
		Assert.assertEquals("Total de peças deveria ter sido mantida em 3.", 3L, peticao.pecas().size());
		Assert.assertEquals("Peça 1 deveria ter sido ordenada com valor 1.", new Long(1L), recuperarPecaPI(peticao).numeroOrdem());
		Assert.assertEquals("Peça substituta da Peça 2 deveria ter sido reordenada com valor 2.", new Long(2L), pecaPorTipo(peticao.pecas(), 2L).numeroOrdem());
		Assert.assertEquals("Peça 3 deveria ter tido mantida sua ordenação com valor 3.", new Long(3L), recuperarPecaAC(peticao).numeroOrdem());
		Assert.assertEquals("Peça substituta da Peça 2 deveria ter realmente substituído.", "Custas Nova", pecaPorTipo(peticao.pecas(), 2L).descricao());
	}
	
	@Test
	public void reordenarDePeca3Para1() {
		incluirPecaCustas();
		Peca pecaAtoCoator = incluirPecaAtoCoator();
		PeticaoEletronica peticao = criarPeticaoPadrao();
		
		Assert.assertTrue("Peça deveria ter sido reordenada", peticao.reordenarPeca(pecaAtoCoator, 1L));
		
		Assert.assertEquals("Peça 1 deveria ter sido reordenada para 2.", new Long(2L), recuperarPecaPI(peticao).numeroOrdem());
		Assert.assertEquals("Peça 2 deveria ter sido reordenada para 3.", new Long(3L), pecaPorTipo(peticao.pecas(), 2L).numeroOrdem());
		Assert.assertEquals("Peça 3 deveria ter sido reordenada para 1.", new Long(1L), recuperarPecaAC(peticao).numeroOrdem());
	}
	
	@Test
	public void reordenarDePeca1Para3() {
		incluirPecaCustas();
		incluirPecaAtoCoator();
		PeticaoEletronica peticao = criarPeticaoPadrao();
		
		Assert.assertTrue("Peça deveria ter sido reordenada", peticao.reordenarPeca(peca, 3L));
		
		Assert.assertEquals("Peça 1 deveria ter sido reordenada para 3.", new Long(3L), recuperarPecaPI(peticao).numeroOrdem());
		Assert.assertEquals("Peça 2 deveria ter sido reordenada para 1.", new Long(1L), pecaPorTipo(peticao.pecas(), 2L).numeroOrdem());
		Assert.assertEquals("Peça 3 deveria ter sido reordenada para 2.", new Long(2L), recuperarPecaAC(peticao).numeroOrdem());
	}
	
	@Test
	public void dividirPeca() {
		Peca pecaASerDividida = incluirPecaCustas();
		incluirPecaAtoCoator();
		PeticaoEletronica peticao = criarPeticaoPadrao();
		
		Peca peca1DaDivisao = criarPecaAN();
		Peca peca2DaDivisao = criarPecaDR();
		
		peticao.dividirPeca(pecaASerDividida, Arrays.asList(peca1DaDivisao, peca2DaDivisao));
		
		Assert.assertTrue("Peça original deveria ter sido removida.", !peticao.pecas().contains(pecaASerDividida));
		Assert.assertEquals("Peça original 1 deveria ter ordenação 1.", new Long(1L), recuperarPecaPI(peticao).numeroOrdem());
		Assert.assertEquals("Peça 1 da divisão deveria ter ordenação 2", new Long(2L), recuperarPecaAN(peticao).numeroOrdem());
		Assert.assertEquals("Peça 2 da divisão deveria ter ordenação 3", new Long(3L), recuperarPecaDR(peticao).numeroOrdem());
		Assert.assertEquals("Peça original 3 deveria ter ordenação 4", new Long(4L), recuperarPecaAC(peticao).numeroOrdem());
	}

	private PecaPeticao criarPecaDR() {
		return criarPeca(new TipoPeca(4L, "Decisão rescindenda"), "Decisão rescindenda");
	}

	private PecaPeticao criarPecaAN() {
		return criarPeca(new TipoPeca(3L, "Cópia do ato normativo ou lei impugnada"), "Cópia do ato normativo ou lei impugnada");
	}
	
	@Test
	public void unirPecas() {
		Peca pecaCustas = incluirPecaCustas();
		incluirPecaAtoCoator();
		Peca pecaAN = incluirPecaAN();
		incluirPecaDR();
		PeticaoEletronica peticao = criarPeticaoPadrao();
		
		Peca pecaUnida = criarPecaDAR();
		
		List<Peca> pecasASeremUnidas = Arrays.asList(pecaCustas, pecaAN);
		
		peticao.unirPecas(pecasASeremUnidas, pecaUnida);
		
		Assert.assertTrue("Deveria não ter a peça custas.", !peticao.pecas().contains(pecaCustas));
		Assert.assertTrue("Deveria não ter a peça AN.", !peticao.pecas().contains(pecaAN));
		Assert.assertTrue("Deveria ter a peça unida", peticao.pecas().contains(pecaUnida));
		
		Assert.assertEquals("Peça Custas deveria ter ordenação 1.", new Long(1L), recuperarPecaPI(peticao).numeroOrdem());
		Assert.assertEquals("Peça unida (DAR) deveria ter ordenação 2", new Long(2L), recuperarPecaDAR(peticao).numeroOrdem());
		Assert.assertEquals("Peça Ato Coator deveria ter ordenação 3", new Long(3L), recuperarPecaAC(peticao).numeroOrdem());
		Assert.assertEquals("Peça DR deveria ter ordenação 4", new Long(4L), recuperarPecaDR(peticao).numeroOrdem());		
	}

	private Peca recuperarPecaDR(PeticaoEletronica peticao) {
		return pecaPorTipo(peticao.pecas(), 4L);
	}

	private Peca recuperarPecaAN(PeticaoEletronica peticao) {
		return pecaPorTipo(peticao.pecas(), 3L);
	}

	private Peca recuperarPecaAC(PeticaoEletronica peticao) {
		return pecaPorTipo(peticao.pecas(), 5L);
	}

	private Peca recuperarPecaPI(PeticaoEletronica peticao) {
		return pecaPorTipo(peticao.pecas(), 1L);
	}
	
	private Peca recuperarPecaDAR(PeticaoEletronica peticao) {
		return pecaPorTipo(peticao.pecas(), 6L);
	}

	private PecaPeticao incluirPecaDR() {
		PecaPeticao pecaDR = criarPecaDR();
		incluirPeca(pecaDR);
		return pecaDR;
	}

	private PecaPeticao incluirPecaAN() {
		PecaPeticao pecaAN = criarPecaAN();
		incluirPeca(pecaAN);
		return pecaAN;
	}
	
	private Peca incluirPecaCustas() {
		PecaPeticao pecaPeticao = criarPeca(new TipoPeca(2L, "Custas"), "Custas");
		incluirPeca(pecaPeticao);
		return pecaPeticao;
	}

	private void incluirPeca(PecaPeticao pecaPeticao) {
		pecas.add(pecaPeticao);
	}
	
	private Peca incluirPecaAtoCoator() {
		PecaPeticao pecaPeticao = criarPeca(new TipoPeca(5L, "Ato coator"), "Ato coator");
		incluirPeca(pecaPeticao);
		return pecaPeticao;
	}

	private PecaPeticao criarPeca(TipoPeca tipoPeca, String descricaoPeca) {
		PecaPeticao pecaPeticao = new PecaPeticao(new DocumentoId(proximoIdDocumento()), tipoPeca, descricaoPeca);
		return pecaPeticao;
	}
	
	private Peca pecaPorTipo(Collection<Peca> pecas, Long idTipo) {
		return pecas.stream().filter(p -> p.tipo().toLong().equals(idTipo)).findFirst().orElseThrow(() -> new IllegalArgumentException("Peça não encontrada."));
	}

	private Long proximoIdDocumento() {
		idDocumentoAtual++;
		return idDocumentoAtual;
	}
	
}
