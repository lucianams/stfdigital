package br.jus.stf.plataforma.documentos.infra.configuration;

import java.io.IOException;
import java.net.UnknownHostException;

import br.jus.stf.plataforma.shared.persistence.LocalData;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory;

/**
 * Factory para criar um mongodb que persistirá em um diretório do home do
 * usuário.
 * 
 * @author Tomas.Godoi
 *
 */
public class PersistentMongodFactory extends MongodForTestsFactory {

	public PersistentMongodFactory() throws IOException {
		super();
	}

	@Override
	protected IMongodConfig newMongodConfig(IFeatureAwareVersion version) throws UnknownHostException, IOException {
		return new MongodConfigBuilder().version(version)
				.replication(new Storage(LocalData.instance().dataDirAbsolutePath() + "/mongodb", null, 0)).build();
	}

}