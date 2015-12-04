package br.jus.stf.plataforma.documentos.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.plataforma.documentos.domain.model.DocumentoDownload;
import br.jus.stf.plataforma.documentos.interfaces.commands.SalvarDocumentosCommand;
import br.jus.stf.plataforma.documentos.interfaces.dto.DocumentoDto;
import br.jus.stf.plataforma.documentos.interfaces.facade.DocumentoServiceFacade;

import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Api REST para salvar e recuperar documentos
 * 
 * @author Lucas Rodrigues
 */
@RestController
@RequestMapping("/api/documentos")
public class DocumentoRestResource {
	
	@Autowired
	private DocumentoServiceFacade documentoServiceFacade;
	
	@Autowired
	private Validator validator;

	@ApiOperation("Salva os documentos temporários")
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public List<DocumentoDto> salvar(@RequestBody SalvarDocumentosCommand command) {
		Set<ConstraintViolation<SalvarDocumentosCommand>> result = validator.validate(command);
		if (!result.isEmpty()) {
			throw new IllegalArgumentException(result.toString());
		}
		return documentoServiceFacade.salvarDocumentos(command.getDocumentos());
	}	
	
	@ApiOperation("Recupera um documento do repositório")
	@RequestMapping(value = "/{documentoId}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> recuperar(@PathVariable("documentoId") Long documentoId) throws IOException {
		DocumentoDownload documento = documentoServiceFacade.pesquisaDocumento(documentoId);
		InputStreamResource is = new InputStreamResource(documento.stream());
		HttpHeaders headers = createResponseHeaders(documento.tamanho());
	    return new ResponseEntity<InputStreamResource>(is, headers, HttpStatus.OK);
	}
	
	@ApiOperation("Envia um documento para armazenamento temporário e retorna o indentificador")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String upload(@RequestParam("file") MultipartFile file) {
		return documentoServiceFacade.salvarDocumentoTemporario(file);
	}

	/**
	 * Define os headers para o pdf 
	 * 
	 * @param documentoId
	 * @param response
	 */
	private HttpHeaders createResponseHeaders(Long tamanho) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(tamanho);
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		return headers;
	}
	
}
