create table TUSER (
	CODUSU INT AUTO_INCREMENT,
	LOGIN VARCHAR(25) unique not null,
	SENHA VARCHAR(60),
	NOME VARCHAR(60) not null default '<Sem Nome>',
	primary key (CODUSU)
);

create table TANX (
	CODANX INT AUTO_INCREMENT,
	CODUSU INT not null,
	DESCRANX VARCHAR(40) not null,
	ARQANX LONGBLOB not null,
	TIPOANX VARCHAR(10),
	primary key (CODANX),
	foreign key (CODUSU) references TUSER(CODUSU)
);