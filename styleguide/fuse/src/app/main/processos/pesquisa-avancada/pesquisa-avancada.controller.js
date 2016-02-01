(function () {

    'use strict';

    var app = angular.module('app.processos.pesquisa-avancada');

    app.classy.controller({

        name: 'ProcessosPesquisaAvancadaController',

        inject: ['$mdDialog', '$scope', '$document', '$filter', '$mdToast', '$mdSidenav', 'traits', 'savedSearchs', 'results'],

        init: function() {
            this.translate = this.$filter('translate');

            this.traits = this.traits.data;
            this.savedSearchs = this.savedSearchs.data;
            this.searchResults = this.results.data;

            this.defaultSearch = {
                id: null,
                label: '',
                criterias: []
            };

            this.newSearch = angular.copy(this.defaultSearch);
            this.loadedSearch = angular.copy(this.defaultSearch);
            this.resultSearch = angular.copy(this.defaultSearch);
            
            this.selectedTab = false;
            this.searchComplete = false; 
            this.editEnabled = false; 

            this.resultsDtOptions = {
                dom       : '<"top"f>rt<"bottom"<"left"<"length"l>><"right"<"info"i><"pagination"p>>>',
                pagingType: 'simple',
                autoWidth : true,
                responsive: false,
                searching: false,
            };
        },

        methods: {
            canSearch: function() {
                var search = (this.selectedTab == 0 ? this.newSearch : this.loadedSearch);
                return ((search.criterias.length > 0) && (_.all(search.criterias, 'valid')));
            },

            doSearch: function() {
                // TODO: Do search
                if (this.selectedTab == 0) {
                    angular.copy(this.newSearch, this.resultSearch);
                    angular.copy(this.newSearch, this.loadedSearch);
                    angular.copy(this.defaultSearch, this.newSearch);
                } else {
                    angular.copy(this.loadedSearch, this.resultSearch);
                }
                
                this.selectedTab = 1;
                this.searchComplete = true;
                this.editEnabled = true;
            },

            saveSearch: function(event) {
                this.$mdDialog.show({
                    clickOutsideToClose: true,
                    controller: /** @ngInject */ function ($rootScope, $mdDialog, searchName) {
                        var vm = this;
                        vm.searchName = searchName;
                        
                        vm.cancel = function () {
                            $mdDialog.hide();
                        };
                        vm.confirm = function () {
                            $rootScope.$broadcast('save-search:confirm', vm.searchName);
                            $mdDialog.cancel();
                        };
                    },
                    resolve: {
                        searchName: function() {
                            return (this.resultSearch.id === null ? '' : this.resultSearch.label)
                        }.bind(this)
                    },
                    controllerAs: 'vm',
                    templateUrl: 'app/main/processos/pesquisa-avancada/modals/save-search-name/save-search-name.html',
                    parent: angular.element(document.body),
                    targetEvent: event
                });

                var removeListener = this.$scope.$on('save-search:confirm', function(event, label) {
                    if (this.resultSearch.id === null) {
                        var id = this.savedSearchs.length;
                        this.savedSearchs.push(angular.copy(this.resultSearch));
                        this.savedSearchs[id].id = id;
                        this.savedSearchs[id].label = label;
                    } else {
                        angular.copy(this.resultSearch, this.savedSearchs[this.resultSearch.id]);
                        this.savedSearchs[this.resultSearch.id].label = label;
                    }
                    
                    this.$mdToast.show(
                        this.$mdToast.simple()
                            .textContent(this.translate('PROCESSOS.PESQUISA-AVANCADA.PESQUISA-SALVA'))
                            .position('top right')
                            .hideDelay(3000)
                    );

                    removeListener();
                }.bind(this));
            },

            openSavedSearchs: function() {
                this.$mdSidenav('sidenav').open();
            },

            loadSearch: function(savedSearch) {
                angular.copy(savedSearch, this.loadedSearch);
                this.$mdSidenav('sidenav').close();
                this.selectedTab = 2;
                this.editEnabled = true;
            }
        }

    });

})();