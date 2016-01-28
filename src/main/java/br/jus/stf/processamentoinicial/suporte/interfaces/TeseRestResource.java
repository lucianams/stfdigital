package br.jus.stf.processamentoinicial.suporte.interfaces;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.jus.stf.jurisprudencia.controletese.domain.model.TeseRepository;
import br.jus.stf.jurisprudencia.controletese.domain.model.TipoTese;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TeseDto;
import br.jus.stf.processamentoinicial.suporte.interfaces.dto.TeseDtoAssembler;

/**
 * Api REST de consulta de teses.
 * 
 * @author Anderson.Araújo
 * 
 * @since 28.01.2016
 * 
 */
@RestController
@RequestMapping("/api/teses")
public class TeseRestResource {
	
	@Autowired
	private TeseRepository teseRepository;
	
	@Autowired
	private TeseDtoAssembler teseDtoAssembler;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<TeseDto> listar(@RequestParam("tipo") String tipo, @RequestParam("numero") Long numero) {
		TipoTese tipoTese = TipoTese.valueOf(tipo.toUpperCase());
		return teseRepository.findTeseByTipo(tipoTese, numero).stream().map(tese -> teseDtoAssembler.toDto(tese)).collect(Collectors.toList());
	}
}
