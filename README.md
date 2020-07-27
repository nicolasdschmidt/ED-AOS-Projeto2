1. Crie as seguintes tabelas MANUAL e DIRETAMENTE no BD, usando exclusivamente SQL, sem nenhuma outra programação associada. Para criá-las, use os seguintes comandos:

```sql
create table Alunos (
RA smallint primary key,
Nome varchar(40) not null
)

create table Matriculas (
RA smallint,
Cod int,
primary key (RA, Cod),
foreign key (RA) references Alunos(RA),
foreign key (Cod) references Disciplinas(Cod)
)

create table Disciplinas (
Cod int primary key,
Nome varchar(40) not null
)

create table Resultados (
RA smallint,
Cod int,
Nota float not null,
Frequencia float not null,
primary key (RA, Cod),
foreign key (RA) references Alunos(RA),
foreign key (Cod) references Disciplinas(Cod)
)
```

2. Insira, MANUAL e DIRETAMENTE NO bd, usando exclusivamente SQL, sem nenhuma outra programação associada, ALGUNS alunos na tabela Alunos, ALGUMAS disciplinas na tabela Disciplinas e ALGUMAS matrículas na tabela Matriculas.

3. Implemente em NodeJS um WEB-Service com o seguinte comportamento: recebendo, ao ser acionado, o RA de um aluno, o código de uma disciplina, a nota (de 0 a 10) do aluno na disciplina e a frequência (de 0,0 a 1,0) do aluno na disciplina, remove a matrícula daquele aluno naquela disciplina da tabela Matriculas e inclui a nota e a frequência daquele aluno naquela disciplina na tabela de Resultados, respondendo com uma indicação de sucesso em caso de sucesso; naturalmente o aluno deve existir, a disciplina deve existir e a matricula deve existir; uma indicação de erro deve ser indicada especificamente para cada caso de erro;

4. Implemente uma classe genérica que representa uma lista ligada desordenada simples com, pelo menos métodos para inserir um elemento no final da lista, para recuperar o primeiro elemento da lista e para remover o primeiro elemento da lista, além dos métodos obrigatórios; outros métodos que julgar úteis para a classe em questão poderão ser implementados sem problemas;

5. Implemente uma classe genérica chamada Fila, usando para isso a implementação de lista pedida no item 4 acima;

6. Implemente uma classe chamada Matricula com os atributos ra (para representar o RA de um aluno), cod (para representar o código de um aluno), nota (para representar a nota daquele aluno naquela disciplina) e freq (para representar a frequência daquele aluno naquela disciplina), bem como getters, setters, um construtor inicializa TODOS os atributos de uma só vez a partir de 4 parâmetros que lhe trarão os valores a serem utilizados na inicialização, além dos métodos obrigatórios que se fizerem necessários;

7. Implemente na linguagem que preferir um programa que solicita que o usuário digite quantas vezes desejar o RA de um aluno, o código de uma disciplina, a nota (de 0 a 10) do aluno na disciplina e a frequência (de 0,0 a 1,0) do aluno na disciplina, sempre armazenando tais dados em uma instância da classe Matricula e sempre armazenando a instância em questão em uma Fila<Matricula>; quando o usuário indicar que não deseja digitar mais nada, recupere, uma a uma, todas as instâncias de Matricula da fila e, com seus dados acione o WEB-Service solicitado no item 3 acima, mostrando na tela os dados de cada matricula recuperada da fila, bem como uma mensagem apropriada à resposta dada pelo WEB-Service quando acionado com os dados daquela matrícula
