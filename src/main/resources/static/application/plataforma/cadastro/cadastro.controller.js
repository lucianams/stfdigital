/**
 * Controlador da tela de cadastro
 * 
 * @author Gabriel Teles
 * 
 * @since 1.0.0
 * @since 03.12.2015
 */
(function($) {
	angular.plataforma.controller('CadastroController', function($state, $timeout, CadastroService) {
		
		this.detalhes = {
			login: "",
			nome: "",
			senha: "",
			senhaRepeticao: "",
			cpf: "",
			oab: "",
			email: "",
			telefone: "",
			tipoAdvogado: 0
		}
	
		$(document).ready(function () {
			// Identifica o formulário
			var form = $('#form-cadastro');

			// Adiciona o método de validação do CPF
			$.validator.addMethod('cpfValidator', function(value, element) {
				// Substitui pontos e traço
				var parsedValue = value.replace(/\.|-/g, '');
				
				// Se não terminou de digitar, não valida
				if (parsedValue.length != 11) {
					return true;
				}
				
				// Valida
				return this.validarCPF(parsedValue);
			}.bind(this));
			
			// Ativa a validação do formulário
			form.validate({
				rules: {
					senha: {
						required: true,
	                    minlength: 5,
					},
					senhaRepeticao: {
                        equalTo: "[name=senha]"
					},
					cpf: {
						cpfValidator: true
					}
				},
				messages: {
					cpf: {
						cpfValidator: 'CPF Inválido'
					}
				}
			});
			
		}.bind(this));
		
		/**
		 * Realiza o cadastro de um usuário
		 */
		this.cadastrarUsuario = function cadastrarUsuario() {
			// Verifica validade do formulário
			if (this.detalhes.login.length == 0) {
				return false;
			}

			if (this.detalhes.nome.length == 0) {
				return false;
			}

			if (this.detalhes.senha.length < 5) {
				return false;
			}

			if (this.detalhes.senhaRepeticao != this.detalhes.senha) {
				return false;
			}

			if (this.detalhes.cpf.length > 0 && !this.validarCPF(this.detalhes.cpf)) {
				return false;
			}
			
			// Cadastra o usuário
			CadastroService.cadastrar(this.detalhes)
				.then(function(response) {
					// Recebeu o ID do novo usuário
					if (response.id !== null) {
						this.exibirNotificacao("Usuário cadastrado com sucesso! Você será redirecionado em alguns segundos...", 'info');
						
						$timeout(function() {
							$state.go('login', {}, {reload: true});
						}, 5000);
					} else {
						this.exibirNotificacao("Ocorreu um erro. Por favor tente novamente mais tarde.", 'error');
					}
				}.bind(this), function(response) {
					this.exibirNotificacao("Ocorreu um erro. Por favor tente novamente mais tarde.", 'error');
				}.bind(this));
			
			return true;
		}
		
		/**
		 * Exibe uma notificação na tela
		 * 
		 * @param string mensagem
		 * @param string tipo 
		 */
		this.exibirNotificacao = function exibirNotificacao(mensagem, tipo) {
			$('body').pgNotification({
				message: mensagem,
				type: tipo,
				style: 'flip'
			}).show();
		}
		
		/**
		 * Valida um CPF
		 * 
		 * @todo Mover para um local compartilhado
		 * @param string numero O número do cpf, sem ponto ou traço
		 * @return boolean Validade do CPF 
		 */
		this.validarCPF = function validarCPF(numero) {
			var soma = 0; 
			var resto; 

			// Verifica tamanho
			if (numero.length != 11) {
				return false;
			}
			
			// Verifica CPF Padrão
			if (this._isCPFPadrao(numero)) {
				return false;
			}
			
			// Calcula primeiro dígito verificador
			for (i=1; i<=9; i++) { 
				soma = soma + parseInt(numero.substring(i-1, i)) * (11 - i); 
			}
			resto = (soma * 10) % 11; 
			
			if ((resto == 10) || (resto == 11)) { 
				resto = 0; 
			}
			
			// Verifica primeiro dígito
			if (resto != parseInt(numero.substring(9, 10))) {
				return false; 
			}
			
			// Calcula segundo dígito verificador
			soma = 0; 
			for (i = 1; i <= 10; i++) {
				soma = soma + parseInt(numero.substring(i-1, i)) * (12 - i);
			}
			resto = (soma * 10) % 11; 
			
			if ((resto == 10) || (resto == 11)) { 
				resto = 0; 
			}
			
			// Verifica segundo dígito
			if (resto != parseInt(numero.substring(10, 11))) { 
				return false; 
			}
			
			return true;
		}
		
		/**
		 * Verifica se o CPF é padrão
		 * 
		 * @todo Mover para um local compartilhado
	 	 * @param string numero O número do cpf, sem ponto ou traço
		 * @return boolean  
		 */
		this._isCPFPadrao = function(numero) {
			return (
					(numero == "00000000000") ||
					(numero == "11111111111") ||
					(numero == "22222222222") ||
					(numero == "33333333333") ||
					(numero == "44444444444") ||
					(numero == "55555555555") ||
					(numero == "66666666666") ||
					(numero == "77777777777") ||
					(numero == "88888888888") ||
					(numero == "99999999999")
			);
		}
	});
})(jQuery);