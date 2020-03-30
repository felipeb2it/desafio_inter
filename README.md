# desafio_inter

Atualização 30/03/2020 - 07:55

O projeto foi desenovido utilizando Java EE 8 e servidor de aplicações Wildfly 18.

O único requisito que ficou pendente foi o Swagger, o restante terminei seguindo de forma bem objetiva os requisitos solicitados. Fiz os testes unitários com JUnit para testar os cálculos e também todos os endpoints rest através do Postman, o arquivo com os testes esta na raiz do projeto.

Para testar com o maven basta executar com a goal test. Antes de adicionar o projeto ao Wildfly e iniciar o servidor é necessario executar o maven com a goal package para que o data-source seja adicionado ao Wildfly. Qualquer dúvida estou a disposição.
