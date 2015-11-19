/**
 * Diretiva para o picklist.
 * Adaptado de https://github.com/IgorKvasn/angular-picklist
 * 
 * @author Tomas.Godoi
 * 
 * @since 1.0.0
 */ 
(function() {
	'use strict';
	
	function ListEntry(originalIndex, data) {
		this.originalIndex = originalIndex;
		this.data = data;
	}
	
	/**
	 * 
	 * Diretiva do picklist.
	 * 
	 */
	angular.plataforma.directive('picklist', [function() {
		return {
			restrict: 'E',
			transclude: true,
			replace: true,
			templateUrl: 'application/plataforma/support/picklist/picklist.tpl.html',
			scope: {
			leftListRowsModel: '=leftListRows',
				rightListRowsModel: '=rightListRows',
				
				displayFn: '&',
		
				listWidth: '@listWidth',//optional, empty by default
				listHeight: '@listHeight',//optional, empty by default
				showMoveAllButtons : '@' //optional, true by default
			},
			link: function (scope, element, attrs) {
		
				function initializeRowLists() {
					scope.leftListRows = scope.leftListRowsModel.map(function(element, index) {
						return new ListEntry(index, element);
					});
					scope.rightListRows = scope.rightListRowsModel.map(function(element, index) {
						return new ListEntry(index, element);
					});
				}
		
				scope.display = function(obj) {
					if (attrs.displayFn) {						
						return scope.displayFn({data: obj});
					} else {
						return obj;
					}
				};
		
				scope.listCss = {};
		
				scope.showAllButtons = scope.showMoveAllButtons || true;
		
				if (scope.listWidth){
					scope.listCss['min-width'] = scope.listWidth + 'px';
				}
		
				if (scope.listHeight){
					scope.listCss.height = scope.listHeight + 'px';
				}
		
				initializeRowLists();
		
				//indices of selected rows
				scope.leftSelected = [];
				scope.rightSelected = [];
		
				scope.leftFilter = '';
				scope.rightFilter = '';
		
				/**
				 * moves only selected rows from left to right
				 */
				scope.moveRightSelected = function () {
					//convert selected rows into raw data
					var selectedData = scope.leftSelected.map(function (row) {
						return row.data;
					});
		
					//add data to the right list
					scope.rightListRowsModel = scope.rightListRowsModel.concat(selectedData);
		
					//remove from left list
					scope.leftSelected.forEach(function (element) {
						scope.leftListRowsModel.splice(element.originalIndex, 1);
					});
		
					//reinitialize row models
					initializeRowLists();
		
					//clear selected lists
					scope.rightSelected = [];
					scope.leftSelected = [];
				};
		
				/**
				 * moves only selected rows from right to left
				 */
				scope.moveLeftSelected = function () {
					//convert selected rows into raw data
					var selectedData = scope.rightSelected.map(function (row) {
						return row.data;
					});
		
					//add data to the left list
					scope.leftListRowsModel = scope.leftListRowsModel.concat(selectedData);
		
					//remove from right list
					scope.rightSelected.forEach(function (element) {
						scope.rightListRowsModel.splice(element.originalIndex, 1);
					});
		
					//reinitialize row models
					initializeRowLists();
		
					//clear selected lists
					scope.rightSelected = [];
					scope.leftSelected = [];
				};
		
				scope.moveRightAll = function () {
					//add data to the right list
					scope.rightListRowsModel = scope.rightListRowsModel.concat(scope.leftListRowsModel);
		
					//remove data from left list
					scope.leftListRowsModel = [];
		
					//reinitialize row models
					initializeRowLists();
		
					//clear selected lists
					scope.rightSelected = [];
					scope.leftSelected = [];
				};
		
		
				scope.moveLeftAll = function () {
					//add data to the right list
					scope.leftListRowsModel = scope.leftListRowsModel.concat(scope.rightListRowsModel);
		
					//remove data from left list
					scope.rightListRowsModel = [];
		
					//reinitialize row models
					initializeRowLists();
		
					//clear selected lists
					scope.rightSelected = [];
					scope.leftSelected = [];
				};
		
			}
		};
	}]);
})();