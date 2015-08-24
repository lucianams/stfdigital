/**
 * @author Lucas Mariano
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 06.07.2015
 */ 
(function() {
	'use strict';

	angular.element(document).ready(function() {
		// Para rodar a aplicação sem acesso ao backend, altere o valor abaixo de 'app' para 'appDev'
		angular.bootstrap(document, ['app']);
	});

	angular.module('app', ['ui.router', 'plataforma', 'autuacao', 'templates', 'properties', 'ui.select2', 'ngSanitize'])
	
	.config(function($stateProvider, $urlRouterProvider, $logProvider, $httpProvider, $locationProvider) {
		$httpProvider.interceptors.push('error-handler');
		$urlRouterProvider.otherwise('/dashboard');
		//$locationProvider.html5Mode(true);
		$logProvider.debugEnabled(true);
		$stateProvider.state('root', {
		});
	})
	
	.value('version', '0.1.0');
	
})();
