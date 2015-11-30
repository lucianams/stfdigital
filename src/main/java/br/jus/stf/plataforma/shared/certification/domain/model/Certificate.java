package br.jus.stf.plataforma.shared.certification.domain.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.jus.stf.shared.stereotype.ValueObject;

@Entity
@Table(name = "CERTIFICADO_DIGITAL", schema = "PLATAFORMA")
public class Certificate implements ValueObject<Certificate> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEQ_CERTIFICADO_DIGITAL", nullable = false)
	private Long sequencial;

	@Column(name = "COD_SERIAL", length = 60)
	private String serial;

	@Column(name = "DSC_CERTIFICADO_DIGITAL", length = 255)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIP_CERTIFICADO_DIGITAL")
	private CertificateType certificateType;

	@Column(name = "DAT_VALIDADE_INICIAL")
	private Date validNotBefore;

	@Column(name = "DAT_VALIDADE_FINAL")
	private Date validNotAfter;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "TXT_CERTIFICADO_DIGITAL")
	private byte[] content;

	@Column(name = "SEQ_USUARIO")
	private Long user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SEQ_CERTIFICADO_EMISSOR", nullable = true)
	private Certificate issuer;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIP_PKI")
	private PkiType pki;

	Certificate() {

	}

	public Long sequencial() {
		return sequencial;
	}

	public String serial() {
		return serial;
	}

	public String description() {
		return description;
	}

	public CertificateType certificateType() {
		return certificateType;
	}

	public Date validNotBefore() {
		return validNotBefore;
	}

	public Date validNotAfter() {
		return validNotAfter;
	}

	public byte[] content() {
		return content;
	}

	public Long user() {
		return user;
	}

	public Certificate issuer() {
		return issuer;
	}

	public PkiType pki() {
		return pki;
	}

	@Override
	public boolean sameValueAs(Certificate other) {
		return other != null && new EqualsBuilder().append(sequencial, other.sequencial).isEquals();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Certificate tObj = (Certificate) obj;

		return sameValueAs(tObj);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(sequencial).toHashCode();
	}

}
