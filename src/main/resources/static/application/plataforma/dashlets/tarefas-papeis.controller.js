/**
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.plataforma.controller('TarefasPapeisDashletController', ['$scope', 'TarefaService', 'ActionService', '$state', 'messages', function($scope, TarefaService, ActionService, $state, messages) {
		
		TarefaService.listarPorMeusPapeis().success(function(tarefas) {
			$scope.tarefasDosPapeis = tarefas;
		});
		
		$scope.selecao = [];
		
		$scope.go = function(tarefa) {
			ActionService.get(tarefa.nome)
				.then(function(action) {
					var params = {
							action : action,
							resources : [tarefa.metadado.informacao],
							task : tarefa
						};
					$state.go(action.id, params);
				}, function() {
					messages.error("Tarefa '" + tarefa.nome + "' não permitida para o usuário!");
				});
		};
		
	}]);
	
})();