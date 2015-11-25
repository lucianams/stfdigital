package br.jus.stf.plataforma.shared.certification.support.generators;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

import br.jus.stf.plataforma.shared.certification.support.pki.CustomPkiStore;
import br.jus.stf.plataforma.shared.certification.support.pki.NCustomPki;

public class GeneratorUtil {

	private GeneratorUtil() {

	}

	public static void storeOnDisk(NCustomPki customPki, String keystorePath) throws Exception {
		KeyStore pkcs12Store = KeyStore.getInstance("PKCS12");
		pkcs12Store.load(null, null);
		pkcs12Store.setKeyEntry("root", customPki.rootCA().keyPair().getPrivate(), "changeit".toCharArray(),
				new Certificate[] { customPki.rootCA().certificate() });
		int i = 1;
		for (CustomPkiStore store : customPki.intermediateCAs()) {
			pkcs12Store.setKeyEntry("ca" + i, store.keyPair().getPrivate(), "changeit".toCharArray(),
					new Certificate[] { store.certificate() });
			i++;
		}

		OutputStream outputStream = new FileOutputStream(keystorePath);
		pkcs12Store.store(outputStream, "changeit".toCharArray());
		outputStream.flush();
		outputStream.close();
	}

	public static void storeOnDisk(CustomPkiStore store, String keystorePath) throws Exception {
		KeyStore pkcs12Store = KeyStore.getInstance("PKCS12");
		pkcs12Store.load(null, null);
		pkcs12Store.setKeyEntry("user", store.keyPair().getPrivate(), "changeit".toCharArray(),
				new Certificate[] { store.certificate() });

		OutputStream outputStream = new FileOutputStream(keystorePath);
		pkcs12Store.store(outputStream, "changeit".toCharArray());
		outputStream.flush();
		outputStream.close();
	}

}
