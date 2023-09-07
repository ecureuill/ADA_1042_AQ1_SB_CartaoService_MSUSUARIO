# Microsserviço de Usuário

## Agregados

- Usuário {**Root**}

  id, nome, cpf, endereço
- Cartão de Crédito

  id, numero do cartão, data de exp, cvv
- Dependentes

  id, nome, cpf, cartão de crédito
  
## Regras

- Um usuário só pode ter um cartão de crédito (o cartão de crédito principal)
- Um usuário pode ter 0 ou mais dependentes
- Cada dependente só pode ter um cartão de crédito (o cartão de crédito adicional)

## Eventos

- UsuárioCriado: disparado quando um novo usuário é criado no sistema. 
- DependenteAdicionado: disparado quando um dependente é adicionado a um usuário.
- DependenteRemovido: Este evento pode ser disparado quando um dependente é removido de um usuário.

## Comandos

- Criar Usuário
- Adicionar Dependente
- Remover Dependente
- Consultar Dependentes
