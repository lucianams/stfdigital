package br.jus.stf;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.jus.stf.autuacao.application.DocumentoUnitTests;
import br.jus.stf.autuacao.application.PeticaoApplicationServiceUnitTests;
import br.jus.stf.autuacao.application.PeticaoIntegrationTests;
import br.jus.stf.autuacao.domain.ClasseProcessualServiceUnitTests;
import br.jus.stf.autuacao.domain.MinistroServiceUnitTests;
import br.jus.stf.autuacao.interfaces.AutuacaoOriginariosIntegrationTests;
import br.jus.stf.plataforma.action.AcoesIntegrationTests;

@RunWith(Suite.class)
@SuiteClasses({ AutuacaoOriginariosIntegrationTests.class, AcoesIntegrationTests.class,
	DocumentoUnitTests.class, PeticaoApplicationServiceUnitTests.class,
	PeticaoIntegrationTests.class, ClasseProcessualServiceUnitTests.class,
	MinistroServiceUnitTests.class})
public class TestsSuite {

}