package br.jus.stf.plataforma.documentos.application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Range;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import br.jus.stf.plataforma.documentos.domain.model.ConteudoDocumento;
import br.jus.stf.plataforma.documentos.domain.model.Documento;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoRepository;
import br.jus.stf.plataforma.documentos.domain.model.DocumentoTemporario;
import br.jus.stf.plataforma.shared.tests.AbstractIntegrationTests;
import br.jus.stf.shared.DocumentoId;
import br.jus.stf.shared.DocumentoTemporarioId;

/**
 * Testes de integração.
 * 
 * @author Tomas.Godoi
 *
 */
public class DocumentoApplicationServiceIntegrationTest extends AbstractIntegrationTests {

	@Autowired
	private DocumentoApplicationService documentoApplicationService;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	private MockMultipartFile mockMultiFile;

	@Test
	public void testDividirDocumentoUmIntervalo() throws Exception {
		List<DocumentoId> documentos = dividirDocumentoComIntervalos(Arrays.asList(Range.between(1, 7)));
		
		Assert.assertEquals("Deveria ter dividido o documento em 1.", 1, documentos.size());
		
		Documento documentoPersistido = documentoRepository.findOne(documentos.get(0));
		
		Assert.assertEquals("Documento dividido deveria ter 7 páginas.", new Integer(7), documentoPersistido.quantidadePaginas());
		
		ConteudoDocumento conteudo = documentoRepository.download(documentoPersistido.id());
		
		String textoDaPagina = extrairTextoDaPagina(conteudo, 1);
		Assert.assertEquals("Deveria ser a página 1", "Página 1", textoDaPagina);
		
		textoDaPagina = extrairTextoDaPagina(conteudo, 7);
		Assert.assertEquals("Deveria ser a página 7", "Página 7", textoDaPagina);
	}

	@Test
	public void testDividirDocumentoTresIntervalos() throws Exception {
		List<DocumentoId> documentos = dividirDocumentoComIntervalos(Arrays.asList(Range.between(3, 7), Range.between(8, 11), Range.between(12, 14)));
		
		Assert.assertEquals("Deveria ter dividido o documento em 3.", 3, documentos.size());
		
		Documento documentoPersistido = documentoRepository.findOne(documentos.get(0));
		Assert.assertEquals("Documento dividido deveria ter 5 páginas.", new Integer(5), documentoPersistido.quantidadePaginas());		
		ConteudoDocumento conteudo = documentoRepository.download(documentoPersistido.id());
		String textoDaPagina = extrairTextoDaPagina(conteudo, 1);
		Assert.assertEquals("Deveria ser a página 3", "Página 3", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 5);
		Assert.assertEquals("Deveria ser a página 7", "Página 7", textoDaPagina);
		
		documentoPersistido = documentoRepository.findOne(documentos.get(1));
		Assert.assertEquals("Documento dividido deveria ter 4 páginas.", new Integer(4), documentoPersistido.quantidadePaginas());		
		conteudo = documentoRepository.download(documentoPersistido.id());
		textoDaPagina = extrairTextoDaPagina(conteudo, 1);
		Assert.assertEquals("Deveria ser a página 8", "Página 8", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 4);
		Assert.assertEquals("Deveria ser a página 11", "Página 11", textoDaPagina);
		
		documentoPersistido = documentoRepository.findOne(documentos.get(2));
		Assert.assertEquals("Documento dividido deveria ter 3 páginas.", new Integer(3), documentoPersistido.quantidadePaginas());
		conteudo = documentoRepository.download(documentoPersistido.id());
		textoDaPagina = extrairTextoDaPagina(conteudo, 1);
		Assert.assertEquals("Deveria ser a página 12", "Página 12", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 3);
		Assert.assertEquals("Deveria ser a página 14", "Página 14", textoDaPagina);
	}

	private List<DocumentoId> dividirDocumentoComIntervalos(List<Range<Integer>> intervalos) throws IOException {
		String idDocumentoTemporario = salvarDocumentoTemporarioParaTeste("pdf-14-pgs.pdf");
		Map<String, DocumentoId> documentosSalvos = documentoApplicationService.salvarDocumentos(Arrays.asList(new DocumentoTemporarioId(idDocumentoTemporario)));
		
		DocumentoId docParaDividir = documentosSalvos.get(idDocumentoTemporario);
		
		List<DocumentoId> documentos = documentoApplicationService.dividirDocumento(docParaDividir, intervalos);
		return documentos;
	}

	private String salvarDocumentoTemporarioParaTeste(String nomeArquivo) throws IOException {
		mockMultiFile = createMockMultiFile(nomeArquivo);
		DocumentoTemporario documentoTemporario = new DocumentoTemporario(mockMultiFile);
		
		return documentoApplicationService.salvarDocumentoTemporario(documentoTemporario);
	}

	private MockMultipartFile createMockMultiFile(String nomeArquivo) throws IOException {
		return new MockMultipartFile("file", nomeArquivo, "application/pdf",
				getClass().getResourceAsStream("/pdf/" + nomeArquivo));
	}
	
	private String extrairTextoDaPagina(ConteudoDocumento conteudo, Integer pagina) throws IOException {
		PdfReader reader = new PdfReader(conteudo.stream());
		return PdfTextExtractor.getTextFromPage(reader, pagina).trim();
	}
	
	@Test
	public void testUnirTresDocumentos() throws IOException {
		String idDocumentoTemporario1 = salvarDocumentoTemporarioParaTeste("pdf-14-pgs.pdf");
		String idDocumentoTemporario2 = salvarDocumentoTemporarioParaTeste("pdf-A-5-pgs.pdf");
		String idDocumentoTemporario3 = salvarDocumentoTemporarioParaTeste("pdf-B-3-pgs.pdf");
		Map<String, DocumentoId> documentosSalvos = documentoApplicationService.salvarDocumentos(Arrays.asList(
		        new DocumentoTemporarioId(idDocumentoTemporario1), new DocumentoTemporarioId(idDocumentoTemporario2),
		        new DocumentoTemporarioId(idDocumentoTemporario3)));
		
		DocumentoId documentoUnidoId = documentoApplicationService
		        .unirDocumentos(Arrays.asList(documentosSalvos.get(idDocumentoTemporario1),
		                documentosSalvos.get(idDocumentoTemporario2), documentosSalvos.get(idDocumentoTemporario3)));
		Documento documentoUnido = documentoRepository.findOne(documentoUnidoId);
		
		Assert.assertEquals("Deveria ter unido os documentos em um com 22 páginas.", new Integer(22), documentoUnido.quantidadePaginas());
		
		ConteudoDocumento conteudo = documentoRepository.download(documentoUnido.id());
		String textoDaPagina = extrairTextoDaPagina(conteudo, 1);
		Assert.assertEquals("Deveria ser a página 1", "Página 1", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 14);
		Assert.assertEquals("Deveria ser a página 14", "Página 14", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 15);
		Assert.assertEquals("Deveria ser a A-página 1", "A-Página 1", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 19);
		Assert.assertEquals("Deveria ser a A-página 5", "A-Página 5", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 20);
		Assert.assertEquals("Deveria ser a B-página 1", "B-Página 1", textoDaPagina);
		textoDaPagina = extrairTextoDaPagina(conteudo, 22);
		Assert.assertEquals("Deveria ser a B-página 3", "B-Página 3", textoDaPagina);
	}
	
}
