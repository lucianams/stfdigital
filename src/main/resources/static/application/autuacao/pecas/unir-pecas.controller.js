/**
 * @author Lucas.Rodrigues
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';

	angular.autuacao.controller('UnePecasController', function($scope, $stateParams) {
		
		$scope.commands = [];
		
		var UnePecasCommand = function(tarefaId, usuarioId) {
			return {
				tarefaId : tarefaId, 
				usuarioId : usuarioId
			};
		};
		
		angular.forEach($stateParams.resources, function(tarefa) {
			$scope.commands.push(new UnePecasCommand(tarefa.id, null));
		});
		
		$scope.validar = function() {
			if (angular.isObject($scope.usuario) ) {
				angular.forEach($scope.commands, function(command) {
					command.usuarioId = $scope.usuario.id;
				});
				return true;
			} else {
				messages.error("Selecione um usuário!");
				return false;
			} 
		};
		
	});
	
})();

