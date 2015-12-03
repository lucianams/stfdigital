package br.jus.stf.plataforma.shared.certification.infra;

import org.springframework.stereotype.Component;

import br.jus.stf.plataforma.shared.certification.domain.DocumentValidatorFactory;
import br.jus.stf.plataforma.shared.certification.domain.model.pki.Pki;
import br.jus.stf.plataforma.shared.certification.domain.model.validation.DocumentValidator;
import br.jus.stf.plataforma.shared.certification.infra.itext.ITextPdfSignatureValidator;

@Component
public class DocumentValidatorFactoryImpl implements DocumentValidatorFactory {

	@Override
	public DocumentValidator createDocumentSignatureValidator(Pki[] pkis) {
		return new ITextPdfSignatureValidator(pkis);
	}

}
