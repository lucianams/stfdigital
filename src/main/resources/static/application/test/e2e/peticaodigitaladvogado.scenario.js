/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 07.07.2015
 */
/*jshint undef:false */
(function() {
	'use strict';
	
	var LoginPage = require('./pages/login.page');
	
	var PrincipalPage = require('./pages/principal.page');
	
	var PeticionamentoPage = require('./pages/peticionamento.page');
	
	var RegistroPage = require('./pages/registro.page');
	
	var AutuacaoPage = require('./pages/autuacao.page');
	
	var DistribuicaoPage = require('./pages/distribuicao.page');
	
	var PreautuacaoPage = require('./pages/preautuacao.page');
	
	var OrganizaPecasPage = require('./pages/organizaPecas.page');
	
	var principalPage;
	
	var loginPage;
	
	var pos;
	
	var peticaoId;
	
	var processoId;
	
	var login = function(user) {
		browser.ignoreSynchronization = true;
		if (!loginPage) loginPage = new LoginPage();
		loginPage.login(user);
		expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
		browser.ignoreSynchronization = false;
	};
		
	describe('Autuação de Petições Digitais Originárias:', function() { 
		
		beforeEach(function() {
			console.info('\nrodando:', jasmine.getEnv().currentSpec.description);
		});
		
		it('Deveria logar como peticionador', function() {
			login('peticionador');
		});

		it('Deveria navegar para a página de envio de petições digitais', function() {
			// Ao instanciar a Home Page, o browser já deve navega para a home page ("/")
			principalPage = new PrincipalPage();
			
			// Verificando se a Home Page tem conteúdo...
			expect(browser.isElementPresent(principalPage.conteudo)).toBe(true);
			
			// Iniciando o Processo de Autuação...
			principalPage.iniciarProcesso('link_registrar-peticao-eletronica');
			
			// Verificando se, após iniciar o processo, o browser está na página de registro de petições físicas
			expect(browser.getCurrentUrl()).toMatch(/\/peticao/);
		});

		it('Deveria enviar uma nova petição digital', function() {
			
			peticionar('RE');
			
			principalPage = new PrincipalPage();
			
			principalPage.iniciarProcesso('link_registrar-peticao-eletronica');
			
			peticionar('AP');
			
			loginPage.logout();
		});
		
		it('Deveria logar como autuador', function() {
			login('autuador');
		});
		
		it('Deveria autuar como válida a petição recebida', function() {
			
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Autuar Processo Originário #' + peticaoId);
		    });
		    
			autuar();
			
			autuar();
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		});
		
		it('Deveria logar como distribuidor', function() {
			login('distribuidor');
		});

		it('Deveria distribuir a petição autuada', function() {
					    
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	peticaoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Distribuir Processo #' + peticaoId);
		    });
			
		    distribuir('COMUM');
		    
		    distribuir('PREVENCAO');
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		}); 
		
		it('Deveria logar como organizador de peças', function() {
			login('organizador-pecas');
		});

		it('Deveria organizar as peças do processo distribuído', function() {
					    
		    expect(principalPage.tarefas().count()).toBeGreaterThan(0);
		    
		    principalPage.tarefas().get(0).getText().then(function(text) {
		    	pos = text.search("#");
		    	pos = pos + 1;
		    	processoId = text.substr(pos, text.length);
		    	expect(principalPage.tarefas().get(0).getText()).toEqual('Organizar Peças #' + processoId);
		    });
		    
		    var organizaPecasPage = new OrganizaPecasPage();
		    
		    organizaPecasPage.finalizar();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			loginPage.logout();
		}); 
		
		it('Deveria logar como gestor-autuacao', function() {
			login('gestor-autuacao');
		});
		
		it('Deveria exibir os dashlets do papel gestor-autuacao', function(){	
			expect(principalPage.dashletGestaoAutuacao.isDisplayed()).toBe(true)
			loginPage.logout();
		});
		
		it('Deveria logar como cartoraria', function() {
			login('cartoraria');
		});
		
		it ('Deveria exibir a dashlet do papel cartorária', function() {
			expect(principalPage.dashletMinhasTarefas.isDisplayed()).toBe(true);
			loginPage.logout();
		});
		
		
		var peticionar = function(siglaClasse){
			
			var peticionamentoPage = new PeticionamentoPage();
			
			peticionamentoPage.classificarClasse(siglaClasse);
			
			peticionamentoPage.partePoloAtivo('João da Silva');
		    
			peticionamentoPage.partePoloPassivo('Maria da Silva');
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.removePecas();
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.selecionarTipoPeca('Ato coator');
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.selecionarTipoPeca('Documentos Comprobatórios', 1);
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.selecionarTipoPeca('Ato coator', 2);
			
			peticionamentoPage.uploadPecas();
			peticionamentoPage.waitUploadFinished(0);
			
			peticionamentoPage.selecionarTipoPeca('Documentos Comprobatórios', 3);
			
		    
			peticionamentoPage.registrar('registrar-peticao-eletronica');
			
			expect(browser.getCurrentUrl()).toMatch(/\/dashboard/);
			
			expect(principalPage.isMensagemSucessoApresentada()).toBeTruthy();
		}
		
		var autuar = function(){

			principalPage.executarTarefa();
		    
			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/autuacao/);
			
			var autuacaoPage = new AutuacaoPage();
			
			autuacaoPage.classificar('AP');
			
			autuacaoPage.partePoloAtivo('João da Silva');
		    
			autuacaoPage.partePoloPassivo('Maria da Silva');
			
			autuacaoPage.finalizar();
		}
		
		var distribuir = function(tipoDistribuicao){
			
		    principalPage.executarTarefa();

			expect(browser.getCurrentUrl()).toMatch(/\/peticao\/distribuicao/);

			var distribuicaoPage = new DistribuicaoPage();
			
			distribuicaoPage.selecionarTipoDistribuicao(tipoDistribuicao);
			
			if (tipoDistribuicao == 'COMUM') {
				
				distribuicaoPage.criarListaDeMinistrosImpedidos();
				
				//verifica se a lista de ministros impedidos possui ao menos um ministro
				expect(distribuicaoPage.listaMinistrosImpedidos().count()).toBeGreaterThan(0);
				
			} else if (tipoDistribuicao == 'PREVENCAO') {
				
				distribuicaoPage.selecionarPrimeiraParte();
				
				distribuicaoPage.selecionarPrimeiroProcessoDaParte();
				
				//verifica se a lista de processos preventos possui ao menos um processo
				expect(distribuicaoPage.listaProcessosPreventos().count()).toBeGreaterThan(0);
			}
			
			distribuicaoPage.criarJustificativa('Teste tipo ditribuicao ' + tipoDistribuicao);
			
			distribuicaoPage.finalizar();
		}
		
	});
})();