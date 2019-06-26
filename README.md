

ToDo List

Autor: Jônatas Sousa de Faria André
Matrícula: 201676030
E-mail: jjsfandre@gmail.com

*** Estrutura de banco utilizada ***

Foram criadas as seguintes tabelas:

* Tarefa
* Status
* Tag
* TarefaTag

O relacionamento entre elas se dá da seguinte forma:


Cada Tarefa possui um Status. (-> 1)
Cada Status pode estar associado ou não à uma ou mais Tarefas. (-> 0..N)
Cada Tag pode estar associado ou não à uma ou mais Tarefas. (-> 0..N)
Cada Tarefa pode estar associada ou não à uma ou mais Tags. (-> 0..N)

O relacionamento N para N de Tag com Tarefa é dado pela TarefaTag


*** Modelo Java utilizado ***

Foram criadas três classes, uma para cada tabela principal do banco:

* Status
* Tag
* Tarefa 

Além dos atributos básicos de cada classe (seguindo a especificação) foi criado o atributo statusId na classe Tarefa para linkar esta classe
com a classe Status e identificar rapidamente em que status está a tarefa.



