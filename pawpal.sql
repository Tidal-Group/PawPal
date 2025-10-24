create database pawpal;
use pawpal;

create table persona (
	id_persona int auto_increment primary key,
    nome varchar(50),
    cognome varchar(50),
    codice_fiscale varchar(50) unique,
    telefono varchar(50)
);

create table user (
    id_utente int primary key,
    email varchar (50) unique,
    username varchar(50) unique,
    password varchar(50),
    ruolo enum('cliente', 'veterinario', 'amministratore'),
    foreign key(id_utente) references persona(id_persona)
);

create table cliente(
    id_cliente int primary key,
    foreign key(id_cliente) references user(id_utente)
);

create table veterinario (
    id_veterinario int primary key,
    specializzazione varchar(50),
    numero_iscrizione_albo varchar(50),
    descrizione text,
    indirizzo varchar(50),
    foreign key (id_veterinario) references user(id_utente)
);

create table amministratore (
    id_amministratore int primary key,
    foreign key(id_amministratore) references user(id_utente)
);

create table prestazione (
    id_prestazione int primary key auto_increment,
    descrizione text,
    prezzo decimal(10,2),
    durata_visita int default 30
);

create table veterinario_prestazione (
	fk_veterinario int,
    fk_prestazione int,
    primary key(fk_veterinario, fk_prestazione),
    foreign key(fk_veterinario) references veterinario(id_veterinario),
    foreign key(fk_prestazione) references prestazione(id_prestazione)
);

create table specie (
    id_specie int auto_increment primary key,
    nome_specie varchar(50) unique
);

create table veterinario_specie (
    fk_veterinario int,
    fk_specie int,
    primary key (fk_veterinario, fk_specie),
    foreign key (fk_veterinario) references veterinario(id_veterinario),
    foreign key (fk_specie) references specie(id_specie)
);

create table disponibilita (
    id_disponibilita int auto_increment primary key,
    fk_veterinario int not null,
    giorno_settimana enum('lunedì', 'martedì', 'mercoledì', 'giovedì', 'venerdì', 'sabato', 'domenica'),
    ora_inizio time not null,
    ora_fine time not null,
    foreign key (fk_veterinario) references veterinario(id_veterinario)
);

create table appuntamento (
    id_appuntamento int auto_increment primary key,
    fk_cliente int not null,
    fk_veterinario int not null,
    fk_prestazione int,
    data_ora datetime not null,
    note text,
    foreign key (fk_cliente) references cliente(id_cliente),
    foreign key (fk_veterinario) references veterinario(id_veterinario)
);

create table recensione (
    id_recensione int auto_increment primary key,
    fk_cliente int not null,
    fk_veterinario int not null,
    voto int check (voto between 1 and 5),
    commento text,
    data_recensione timestamp default current_timestamp,
    foreign key (fk_cliente) references cliente(id_cliente),
    foreign key (fk_veterinario) references veterinario(id_veterinario)
);