package br.jus.stf.generico.domain.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.Validate;
import org.springframework.web.multipart.MultipartFile;

import br.jus.stf.shared.domain.stereotype.ValueObject;

/**
 * Documento ainda não persistido
 * 
 * @author Lucas.Rodrigues
 *
 */
public class DocumentoTemporario implements ValueObject<DocumentoTemporario> {

	private static final long serialVersionUID = -3725370010702512231L;
	
	private Long tamanho;
	private File arquivo;
	private String extension;
	
	public DocumentoTemporario(MultipartFile file, String extension) {
		Validate.notNull(file);
		Validate.notBlank(extension);
		
		this.extension = extension;
		arquivo = createTempFile(file);
		tamanho = arquivo.length();
	}
	
	private File createTempFile(MultipartFile file) {
		File tempFile = null;
		try {
			File.createTempFile(null, extension);
			file.transferTo(tempFile);
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
		return tempFile;
	}
	
	public String tempId() {
		return arquivo.getName();
	}
	
	public Long tamanho() {
		return tamanho;
	}
	
	public FileInputStream stream() {
		try {
			return new FileInputStream(arquivo);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public void delete() {
		arquivo.delete();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arquivo.getAbsolutePath() == null) ? 0 : arquivo.getAbsolutePath().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		
		DocumentoTemporario other = (DocumentoTemporario) obj;
		return sameValueAs(other);
	}
	
	@Override
	public boolean sameValueAs(DocumentoTemporario other) {
		return other != null && Objects.equals(this.arquivo.getAbsolutePath(), other.arquivo.getAbsolutePath());
	}

}