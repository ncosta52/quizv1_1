package com.tpv13.costa.nuno.quizv1;

/**
 * Created by Nuno on 01-12-2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDbHelper_game extends SQLiteOpenHelper
{

    //Nome e versao da base de dados
    private static final String DATABASE_NAME ="mydatabase_quiz.db";
    private static final int DATABASE_VERSION = 1;

    public MyDbHelper_game(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        try{

            // cria a tabela Niveis
            db.execSQL("CREATE TABLE Niveis("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Nome VARCHAR(200) NOT NULL, " +
                    "Descricao VARCHAR(200) NOT NULL, " +
                    "Pontuacao INTEGER NOT NULL)");
            dadosIniciais_Niveis(db);

            // cria a tabela Categorias
            db.execSQL("CREATE TABLE Categorias("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Descricao VARCHAR(200) NOT NULL)");
            dadosIniciais_Categorias(db);


            // cria a tabela Perguntas
            db.execSQL("CREATE TABLE Perguntas("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Niveis_Id INTEGER NOT NULL, " +
                    "Categorias_Id INTEGER NOT NULL, " +
                    "Pergunta VARCHAR(200) NOT NULL, " +
                    "Pontuacao INTEGER NOT NULL)");
            dadosIniciais_Perguntas(db);

            // cria a tabela Respostas
            db.execSQL("CREATE TABLE Respostas("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Perguntas_Id INTEGER NOT NULL, " +
                    "Descricao VARCHAR(200) NOT NULL, " +
                    "Correta VARCHAR(1) NOT NULL)");
            dadosIniciais_Respostas(db);


            // cria a tabela Utilizadores
            db.execSQL("CREATE TABLE Utilizadores("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Username VARCHAR(10) NOT NULL, " +
                    "Password VARCHAR(10) NOT NULL, " +
                    "Nivel INTEGER NOT NULL, "+
                    "Photo VARCHAR(20))");
            dadosIniciais_Utilizadores(db);

            // cria a tabela Users_Ranking_Nivel
            db.execSQL("CREATE TABLE Users_Ranking ("+
                    "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Utilizadores_Id INTEGER NOT NULL, " +
                    "Niveis_Id INTEGER NOT NULL, " +
                    "PontucaoTotal INTEGER NOT NULL, " +
                    "RespostasDadasTotal INTEGER NOT NULL, " +
                    "Tempo INTEGER NOT NULL)");


            //db.execSQL("INSERT INTO tblPessoas(Nome, Idade, Sexo, Descicao, Imagem) VALUES ('Rosa', '10', 'F', 'Ela mesmo', 'c:/img/Rosa.jpg');");
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
//        dropAll(db);
//        onCreate(db);

    }

    private void dadosIniciais_Niveis(SQLiteDatabase db){

        db.execSQL("INSERT INTO Niveis (Nome, Descricao, Pontuacao) VALUES ('Principiante', 'Perguntas mais acessíveis. Permite conhecer melhor até passar a um nível mais dificil.',1);");
        db.execSQL("INSERT INTO Niveis (Nome, Descricao, Pontuacao) VALUES ('Intermédio', 'Perguntas de um grau de dificuldade superior ao nível Principienate.',2);");
        db.execSQL("INSERT INTO Niveis (Nome, Descricao, Pontuacao) VALUES ('Master', 'Perguntas de um grau de dificuldade elevado.',3);");
    }

    private void dadosIniciais_Categorias(SQLiteDatabase db){
        db.execSQL("INSERT INTO Categorias (Descricao) VALUES ('Desporto');");
        db.execSQL("INSERT INTO Categorias (Descricao) VALUES ('Ciencia');");
        db.execSQL("INSERT INTO Categorias (Descricao) VALUES ('Cinema');");
        db.execSQL("INSERT INTO Categorias (Descricao) VALUES ('ESTG');");
    }

    private void dadosIniciais_Perguntas(SQLiteDatabase db){
        //Perguntas acerca da ESTG
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,4,'Em que cidade se situa a ESTG?',1 );");          //1
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,4,'Como é que conhecido o edíficio onde a escola funciona?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,4,'Qual a data do primeiro dia de aulas da ESTG',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,4,'Em que ano se inaugurou a ESTG?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,4,'Qual o primeiro curso a ser leccionada na ESTG',2 );");          //5
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,4,'Qual o primeiro ano da Licenciatura Bietápica em Engenharia Informática',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,4,'Antes de ter como sigla ESTG, que sigla tinha escola?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,4,'Quantos cursos(licenciaturas) existem na ESTG em 2016/2017?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,4,'Quem é o coordenador de curso LEI em 2016/2017?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,4,'Quantas escolas tem o IPP em 2016/2017?',2 );");         //10
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,4,'Em que ano foi criada a tuna ESTG(F)?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,4,'Na licenciatura como é calculado o ano em que frequenta?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,4,'Quantas disciplinas se pode inscrever o aluno em época especial?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,4,'O que significa SNMC nas pautas?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,4,'Que documentos são necessarios para requerer estatuto trabalhador-estudante?',3 );");      //15

        //Perguntas acerca de desporto
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,1,'Quais as cores do BENFICA?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,1,'Quantos atletas existem numa partida de tennis?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,1,'No ciclismo, que acessorio é indispensavel?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,1,'A que clube pertence o estadio da luz?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,1,'No tennis de mesa, quantos guarda redes tem cada equipa?',1 );");   //20
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,1,'Quem foi campeao europeu de futebol em 2016?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,1,'Que desporto necessita de mecanico?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,1,'Qual destes clubes é americano?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,1,'Quem foi considerado o melhor jogador de futsal em 2015?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,1,'Qual o equipamento essencial na natação?',2 );");  //25
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,1,'Em que ano foi fundada a Associacao Desportiva de Fafe?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,1,'Quantos jogadores alinham de num jogo de andebol?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,1,'Quais são os pilotos pontuados no final de uma corrida de formula 1?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,1,'Onde situa o estadio parc dos principes?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,1,'Qual o nome do jogador casado com a cantora Shakira?',3 );");  //30

        //Perguntas de ciencia
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,2,'Que tipo de rocha é o petroleo?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,2,'Que quimico é essencial para fazer foguetes?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,2,'Como se chama a passagem de um elemento do estado liquido para gazoso?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,2,'Que tecnologia transporta a internet para elementos sem fios?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,2,'Que elemento rochoso é efervescente?',1 );");    //35
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,2,'Em que ano o homem pisou pela primeira vez a lua?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,2,'O que pesa mais, um kilo de penas ou 1 kilo de chumbo?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,2,'A linguagem java é uma linguagem?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,2,'Qual o sistema operativo mais utilizado em 2016?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,2,'O que significa activity em android?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,2,'O que significa Asynctask em android?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,2,'Eficiencia informatica significa?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,2,'Para que serve uma thread(informatica)?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,2,'O que é um Widget em android ?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,2,'Qual a definição de BroadCoast?',3 );");

        //Perguntas de cinema
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,3,'Que ator participou no filme Top Gun?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,3,'Que categoria pertence o filme O REI LEAO?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,3,'Quem foi o criador da Disney?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,3,'Qual das seguintes personagens é amigo de Mickey?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (1,3,'Quantos eram os três porquinhos?',1 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,3,'Quem contracena com Brad Pitt no filme SR e SRA SMITH ?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,3,'Como se chamava o cavalo de Lucky Luck?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,3,'Qual o ator principal no filme Taken(Busca implacavel)?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,3,'Quantos episódios teve a saga SAW (Jogos Mortais)?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (2,3,'Qual das seguintes empresas é uma empresa cinematografica?',2 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,3,'Quem foi o criador do filme inteligencia artificial?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,3,'Qual o primeiro nome do espião que deu o nome ao filme SNOWDEN?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,3,'Em que ano foi lançado o primeiro filme de AMERICAN PIE?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,3,'O que retrata o filme JOBS?',3 );");
        db.execSQL("INSERT INTO Perguntas (Niveis_Id, Categorias_Id, Pergunta, Pontuacao) VALUES (3,3,'The social network retrata que aplicação informatica?',3 );");
    }

    private void dadosIniciais_Respostas(SQLiteDatabase db){
        //Respostas ESTG
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (1,'Felgueiras','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (1,'Porto','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (1,'Guimarães','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (1,'Braga','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (2,'Curral dos Caprinos','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (2,'Casa do Curral','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (2,'Quinta de Felgueiras','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (2,'Edificio Prof. João Gomes','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (3,'17 de Outubro de 1998','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (3,'17 de Novembro de 1998','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (3,'17 de Outubro de 1999','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (3,'17 de Novembro de 1999','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (4,'1998','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (4,'1999','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (4,'2000','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (4,'2001','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (5,'Licenciatura em Segurança e Qualidade no Trabalho','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (5,'Licenciatura Bietápica em Engenharia Informática ','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (5,'Licenciatura Bietápica em Solicitadoria','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (5,'Licenciatura Bietápica em Ciências','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (6,'1998','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (6,'1999','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (6,'2000','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (6,'2001','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (7,'IPPF','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (7,'ESTGF','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (7,'IPPESTGF','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (7,'IPPFELG','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (8,'4','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (8,'7','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (8,'9','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (8,'6','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (9,'Prof. Doutor Ricardo Santos','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (9,'Prof. Doutor Vítor Braga','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (9,'Prof. Doutor João Paulo Magalhães','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (9,'Prof. Doutora Carla Pereira','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (10,'9','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (10,'8','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (10,'11','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (10,'7','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (11,'1980','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (11,'2006','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (11,'2004','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (11,'2000','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (12,'1ºano < 60 etc; 59 etc < 2ºano < 120 etc; 119 etc < 3ºano','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (12,'1ºano < 40 etc; 39 etc < 2ºano < 100 etc; 99 etc < 3ºano','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (12,'1ºano < 40 etc; 40 etc < 2ºano < 100 etc; 100 etc < 3ºano','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (12,'1ºano < 60 etc; 60 etc < 2ºano < 120 etc; 120 etc < 3ºano','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (13,'Uma semestral','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (13,'Duas semestrais','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (13,'Três semestrais','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (13,'Sem limite desde que se tenha inscrito no ano corrente','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (14,'Super nota, melhor classificacao','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (14,'Sem nota minima, chumbou','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (14,'Sem nota minima numa componente','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (14,'Sem noçao minima na cadeira','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (15,'Declaração da entidade patronal','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (15,'Comprovativo de efetuação de descontos dos ultimos 6 meses','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (15,'Comprovativo de efetuação de descontos dos ultimos 2 meses','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (15,'Declaração da entidade patronal e comprovativo de efetuação de descontos dos ultimos 6 meses','N' );");

        //Respostas desporto
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (16,'Vermelho e Branco','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (16,'Branco','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (16,'Azul e Branco','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (16,'Rosa com flores amarelas','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (17,'2','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (17,'2 ou 4','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (17,'4','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (17,'11','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (18,'Fraldas','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (18,'Bolas','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (18,'Pistolas','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (18,'Bicicletas','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (19,'São Paulo','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (19,'Benfica','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (19,'Iluminarte','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (19,'AD Fafe','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (20,'8','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (20,'4','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (20,'2','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (20,'0','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (21,'Benfica','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (21,'Famalicão','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (21,'França','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (21,'Portugal','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (22,'Futebol','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (22,'Formula 1','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (22,'Basket','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (22,'Jogo de sueca','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (23,'Juventus','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (23,'Lakers','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (23,'Dinamo de zagreb','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (23,'Ajax','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (24,'Ricardinho','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (24,'Messi','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (24,'Cristiano Ronaldo','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (24,'Nadal','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (25,'Fato de banho','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (25,'Fato de mergulho','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (25,'Fato de treino','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (25,'Fato de gala','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (26,'1980','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (26,'1981','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (26,'1979','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (26,'1958','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (27,'6','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (27,'7','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (27,'8','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (27,'5','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (28,'Os 3 primeiros','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (28,'Os 10 primeiros','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (28,'Os 5 primeiros','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (28,'Os 8 primeiros','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (29,'Turim','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (29,'Lisboa','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (29,'Paris','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (29,'Monaco','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (30,'Nelson Piqué','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (30,'Gerard Depardieu','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (30,'Gerard Piqué','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (30,'Brad Pite','N' );");

        //Respostas Ciencias
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (31,'Rocha liquida','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (31,'Rocha Magmatica','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (31,'Rocha sedimentar','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (31,'Rocha metamorfica','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (32,'Gasolina','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (32,'Pólvora','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (32,'Cal','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (32,'Madeira','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (33,'Fricção','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (33,'Condensação','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (33,'Solidificação','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (33,'Evaporação','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (34,'Ethernet','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (34,'Wifi','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (34,'Memória Ram','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (34,'Disco rigido','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (35,'Granito','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (35,'Quartz','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (35,'Marmore','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (35,'Calcario','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (36,'1970','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (36,'1963','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (36,'1945','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (36,'1969','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (37,'As penas','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (37,'É o mesmo peso','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (37,'O chumbo','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (37,'Depende do numero de penas','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (38,'Orientada a eventos','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (38,'Orientada a objetos','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (38,'Orientada a norte','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (38,'Orientada aos informaticos','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (39,'Windows 7','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (39,'Windows 8.1','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (39,'Windows 10','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (39,'OSX','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (40,'Actividade ou accao de uma aplicação','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (40,'Despertador informatico','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (40,'Um jogo de desporto','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (40,'Uma base de dados','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (41,'Processa e syncroniza as widgets','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (41,'Syncroniza as activitys','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (41,'Syncroniza o relogio do sistema','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (41,'Processamento em background atualizando dados na interface grafica','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (42,'Aplicacao sem falhas','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (42,'Atingir determinado resultado com menores recursos','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (42,'Atingir determinado resultado passando por todas as funcoes existentes','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (42,'Aplicacao que cumpre os requisitos','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (43,'Termo inexistente em informatica','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (43,'Forma de um processo dividir a si mesmo em 2 ou mais tarefas que podem ser executadas concorrencialmente','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (43,'Gestão da memória partilhada por diversos utilizadores','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (43,'Fila de dados ordenada de uma base de dados, este termo apenas se aplica em oracle','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (44,'Sistema de informação partilhada em rede','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (44,'Base de dados SQL','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (44,'Pequenos aplicativos que fornecem funcionalidades especificas ao utilizador','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (44,'Aplicação exclusiva dos sistemas IOS','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (45,'Aplicações de baixo custo','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (45,'Aplicações de custo controlado','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (45,'Distribuição ou partilha de informação','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (45,'Forma de distribuir aplicações com controlo de custos','N' );");

        //Respostas Cinema
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (46,'Tom Cruise','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (46,'Mickey Mouse','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (46,'Clark Kent','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (46,'Nuno Costa','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (47,'Drama','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (47,'Infantil','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (47,'Documentario','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (47,'Terror','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (48,'Nuno Disney','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (48,'Gil Disney','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (48,'Mickey Disney','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (48,'Walt Disney','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (49,'Fernando Rocha','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (49,'Pluto','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (49,'Maria Leal','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (49,'Teresa Guilherme','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (50,'8','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (50,'0','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (50,'4','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (50,'3','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (51,'Manuela Moura Guedes','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (51,'Lucia Muniz','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (51,'Barbara Guimarães','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (51,'Angelina Jolie','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (52,'Horse velocity','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (52,'Jolly Jumper','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (52,'Salta e corre','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (52,'Johny relampago','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (53,'Brad Pitt','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (53,'Liam Neeson','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (53,'Sylvester Stallone','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (53,'Bruce Willis','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (54,'8','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (54,'6','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (54,'3','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (54,'12','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (55,'Warner Brothers','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (55,'Cinematuga','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (55,'Big Brothers','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (55,'SuperTV','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (56,'James Cameron','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (56,'Woody Allen','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (56,'Quentin Tarantino','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (56,'Steven Spielberg','S' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (57,'James','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (57,'Edward','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (57,'Oliver','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (57,'Joseph','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (58,'1984','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (58,'1999','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (58,'1991','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (58,'1992','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (59,'A dificuldade em arranjar trabalho','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (59,'A bem conhecida frase, JOBS OF THE BOYS','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (59,'A vida de Steve Jobs','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (59,'A rotina de um individuo que muda de emprego frequentemente','N' );");

        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (60,'Twitter','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (60,'Mensenger','N' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (60,'Facebook','S' );");
        db.execSQL("INSERT INTO Respostas ( Perguntas_Id, Descricao, Correta) VALUES (60,'Musically','N' );");

    }

    private void dadosIniciais_Utilizadores(SQLiteDatabase db){

        db.execSQL("INSERT INTO Utilizadores (Username, Password, Nivel, Photo) VALUES ('admin', 'admin',3,NULL);");
        db.execSQL("INSERT INTO Utilizadores (Username, Password, Nivel, Photo) VALUES ('gil2d', 'gil2d',1,NULL);");
        db.execSQL("INSERT INTO Utilizadores (Username, Password, Nivel, Photo) VALUES ('nuno', 'nuno',1,NULL);");
    }
}
