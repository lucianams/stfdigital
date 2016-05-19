/**
 * 
 * 
 */
(function() {
	'use strict';
	
	angular.plataforma.factory('TextoService', function(ActionService) {
		return {
			gerarTextoComTags: function(command) {
				return ActionService.execute('gerar-texto-devolucao', [command]).then(function(response) {
					return response.data;
				});
			},
		};
	});
})();