package br.jus.stf.plataforma.shared.certification.infra.itext;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.jus.stf.plataforma.shared.certification.domain.model.signature.HashType;

public class ITextPDFSignatureUtil {

	private ITextPDFSignatureUtil() {

	}

	public static int estimateSignatureSize(X509CRL[] crls, boolean hasOCSP, boolean hasTSA) throws CRLException {
		int estimatedSize = 8192;

		if (crls != null && crls.length > 0) {
			for (X509CRL crl : crls) {
				estimatedSize += crl.getEncoded().length + 10;
			}
		}

		if (hasOCSP) {
			estimatedSize += 4192;
		}

		if (hasTSA) {
			estimatedSize += 4192;
		}

		return estimatedSize;
	}

	public static Collection<byte[]> crlsToByteCollection(X509CRL[] crls) throws CRLException {
		if (crls.length == 0)
			return null;
		List<byte[]> crlCol = new ArrayList<>();
		for (X509CRL crl : crls) {
			crlCol.add(crl.getEncoded());
		}
		return crlCol;
	}

	public static byte[] applyHash(byte[] bytes, HashType hashType) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(hashType.dashedName());
			messageDigest.update(bytes);
			byte hash[] = messageDigest.digest();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Algoritmo de hash SHA-256 não encontrado.", e);
		}
	}

}
