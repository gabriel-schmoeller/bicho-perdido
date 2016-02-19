DROP TABLE cor;
DROP TABLE peculiaridade;
DROP TABLE peculiaridade_local;
DROP TABLE peculiaridade_tipo;
DROP TABLE media;
DROP TABLE anuncio_semelhanca;
DROP TABLE anuncio_distancia;
DROP TABLE anuncio;
DROP TABLE animal;
DROP TABLE raca;
DROP TABLE usuario_token;
DROP TABLE usuario_protetor;
DROP TABLE usuario;

CREATE TABLE usuario (
  id serial PRIMARY KEY,
  nome varchar(150) NOT NULL,
  telefone varchar(20) NOT NULL,
  email varchar(150) NOT NULL,
  senha varchar(150) NOT NULL
);

CREATE TABLE usuario_protetor (
  id serial PRIMARY KEY,
  usuario_id integer REFERENCES usuario (id) ON DELETE CASCADE,
  raio double precision,
  longitude double precision,
  latitude double precision
);

CREATE TABLE usuario_token (
  token varchar(150) PRIMARY KEY,
  usuario_id integer REFERENCES usuario (id) ON DELETE CASCADE,
  tempo_limite timestamp NOT NULL
);

CREATE TABLE raca (
  id serial PRIMARY KEY,
  especie char(1) NOT NULL,
  nome varchar(150) NOT NULL
);

CREATE TABLE animal (
  id serial PRIMARY KEY,
  nome varchar(150),
  genero char(1),
  especie char(1),
  raca_id integer REFERENCES raca (id) ON DELETE CASCADE,
  porte char(1),
  pelagem char(1) NOT NULL,
  outros varchar(500)
);

CREATE TABLE anuncio (
  id serial PRIMARY KEY,
  usuario_id integer REFERENCES usuario (id) ON DELETE CASCADE,
  natureza char(1) NOT NULL,
  animal_id integer REFERENCES animal (id) ON DELETE CASCADE,
  data_hora timestamp with time zone NOT NULL,
  data_hora_resolvido timestamp with time zone,
  local_longitude double precision NOT NULL,
  local_latitude double precision NOT NULL,
  local_endereco varchar(255) NOT NULL,
  detalhes varchar(500),
  status char(1) NOT NULL,
  compared boolean NOT NULL
);

CREATE TABLE anuncio_distancia (
  anuncio1_id integer REFERENCES anuncio (id) ON DELETE CASCADE,
  anuncio2_id integer REFERENCES anuncio (id) ON DELETE CASCADE,
  distancia double precision,
  PRIMARY KEY (anuncio1_id, anuncio2_id)
);

CREATE TABLE anuncio_semelhanca (
  anuncio1_id integer REFERENCES anuncio (id) ON DELETE CASCADE,
  anuncio2_id integer REFERENCES anuncio (id) ON DELETE CASCADE,
  grau double precision,
  PRIMARY KEY (anuncio1_id, anuncio2_id)
);

CREATE TABLE media (
  id serial PRIMARY KEY,
  nome varchar(150) NOT NULL,
  caminho varchar(500) NOT NULL,
  capa boolean NOT NULL,
  tipo char(1) NOT NULL,
  anuncio_id integer REFERENCES anuncio (id) ON DELETE CASCADE
);

CREATE TABLE peculiaridade_tipo (
  id serial PRIMARY KEY,
  nome varchar(60) NOT NULL
);

CREATE TABLE peculiaridade_local (
  id serial PRIMARY KEY,
  nome varchar(60) NOT NULL
);

CREATE TABLE peculiaridade (
  id serial PRIMARY KEY,
  tipo_id integer REFERENCES peculiaridade_tipo (id) ON DELETE CASCADE,
  local_id integer REFERENCES peculiaridade_local (id) ON DELETE CASCADE,
  animal_id  integer REFERENCES animal (id) ON DELETE CASCADE
);

CREATE TABLE cor (
  id serial PRIMARY KEY,
  animal_id integer REFERENCES animal (id) ON DELETE CASCADE,
  l integer NOT NULL,
  a integer NOT NULL,
  b integer NOT NULL
);

INSERT INTO peculiaridade_tipo (nome) VALUES ('Cicatriz');
INSERT INTO peculiaridade_tipo (nome) VALUES ('Deficiência');
INSERT INTO peculiaridade_tipo (nome) VALUES ('Falha');
INSERT INTO peculiaridade_tipo (nome) VALUES ('Mancha');

INSERT INTO peculiaridade_local (nome) VALUES ('Cabeça');
INSERT INTO peculiaridade_local (nome) VALUES ('Orelha esquerda');
INSERT INTO peculiaridade_local (nome) VALUES ('Orelha direita');
INSERT INTO peculiaridade_local (nome) VALUES ('Tronco (torso)');
INSERT INTO peculiaridade_local (nome) VALUES ('Tronco (dorso)');
INSERT INTO peculiaridade_local (nome) VALUES ('Pata traseira esquerda');
INSERT INTO peculiaridade_local (nome) VALUES ('Pata traseira direita');
INSERT INTO peculiaridade_local (nome) VALUES ('Pata frontal esquerda');
INSERT INTO peculiaridade_local (nome) VALUES ('Pata frontal direita');
INSERT INTO peculiaridade_local (nome) VALUES ('Cauda');

INSERT INTO raca (especie, nome) VALUES ('c', 'Vira-lata SRD (Sem Raça Definida)');INSERT INTO raca (especie, nome) VALUES ('c', 'Australian Cattle Dog');
INSERT INTO raca (especie, nome) VALUES ('c', 'Australian Shepherd');
INSERT INTO raca (especie, nome) VALUES ('c', 'Border Collie');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bouvier des Flandres');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bearded Collie');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão Lobo Tchecoslovaco');
INSERT INTO raca (especie, nome) VALUES ('c', 'Collie Pelo Curto');
INSERT INTO raca (especie, nome) VALUES ('c', 'Collie Pelo Longo');
INSERT INTO raca (especie, nome) VALUES ('c', 'Komondor');
INSERT INTO raca (especie, nome) VALUES ('c', 'Kuvasz');
INSERT INTO raca (especie, nome) VALUES ('c', 'Mudi');
INSERT INTO raca (especie, nome) VALUES ('c', 'Old English Sheepdog');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor Alemão');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor de Beauce');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor Belga');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor Branco Suíço');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor de Brie');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor Holandês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor Maremano Abruzês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor Polonês da Planície');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor de Shetland');
INSERT INTO raca (especie, nome) VALUES ('c', 'Puli');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pumi');
INSERT INTO raca (especie, nome) VALUES ('c', 'Schipperke');
INSERT INTO raca (especie, nome) VALUES ('c', 'Welsh Corgi Cardigan');
INSERT INTO raca (especie, nome) VALUES ('c', 'Welsh Corgi Pembroke');
INSERT INTO raca (especie, nome) VALUES ('c', 'Affenpinscher');
INSERT INTO raca (especie, nome) VALUES ('c', 'Boiadeiro Bernês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Boiadeiro de Entlebuch');
INSERT INTO raca (especie, nome) VALUES ('c', 'Boxer');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bulldog');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bullmastiff');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão de Castro Laboreiro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cane Corso Italiano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão Fila de São Miguel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão da Montanha dos Pireneus');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão das Montanhas do Atlas');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão da Serra da Estrela');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cimarrón Uruguayo');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dobermann');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dogo Argentino');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dogo Canário');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dogue Alemão');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dogue de Bordeaux');
INSERT INTO raca (especie, nome) VALUES ('c', 'Fila Brasileiro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Landseer');
INSERT INTO raca (especie, nome) VALUES ('c', 'Leonberger');
INSERT INTO raca (especie, nome) VALUES ('c', 'Mastim Espanhol');
INSERT INTO raca (especie, nome) VALUES ('c', 'Mastim dos Pireneus');
INSERT INTO raca (especie, nome) VALUES ('c', 'Mastiff');
INSERT INTO raca (especie, nome) VALUES ('c', 'Mastino Napoletano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor da Ásia Central');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor da Anatólia');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor do Caucaso');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pinscher Miniatura');
INSERT INTO raca (especie, nome) VALUES ('c', 'Rafeiro do Alentejo');
INSERT INTO raca (especie, nome) VALUES ('c', 'Rottweiler');
INSERT INTO raca (especie, nome) VALUES ('c', 'São Bernardo');
INSERT INTO raca (especie, nome) VALUES ('c', 'Schnauzer');
INSERT INTO raca (especie, nome) VALUES ('c', 'Schnauzer Gigante');
INSERT INTO raca (especie, nome) VALUES ('c', 'Schnauzer Miniatura');
INSERT INTO raca (especie, nome) VALUES ('c', 'Shar Pei');
INSERT INTO raca (especie, nome) VALUES ('c', 'Smoushond Holandês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terrier Preto da Rússia');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terra Nova');
INSERT INTO raca (especie, nome) VALUES ('c', 'Tosa');
INSERT INTO raca (especie, nome) VALUES ('c', 'Airedale Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'American Staffordshire Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bedlington Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Border Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bull Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bull Terrier Miniatura');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cairn Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cesky Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dandie Dinmont Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Fox Terrier Pelo Liso (Smooth)');
INSERT INTO raca (especie, nome) VALUES ('c', 'Fox Terrier Pelo Duro (Wire)');
INSERT INTO raca (especie, nome) VALUES ('c', 'Jack Russell Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Irish Soft Coated Wheaten Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Kerry Blue Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Lakeland Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Manchester Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Norfolk Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Norwich Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Parson Russell Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Staffordshire Bull Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Sealyham Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Silky Terrier Australiano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Skye Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terrier Alemão de Caça - JAGD');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terrier Brasileiro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terrier Escocês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terrier Irlandês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terrier Irlandês do Glen do Imaal');
INSERT INTO raca (especie, nome) VALUES ('c', 'Welsh Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'West Highland White Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Yorkshire Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dachshund');
INSERT INTO raca (especie, nome) VALUES ('c', 'Akita');
INSERT INTO raca (especie, nome) VALUES ('c', 'Akita Americano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Basenji');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão da Groenlândia');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão do Canaã');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cirneco do Etna');
INSERT INTO raca (especie, nome) VALUES ('c', 'Chow Chow');
INSERT INTO raca (especie, nome) VALUES ('c', 'Elkhound Norueguês Cinza');
INSERT INTO raca (especie, nome) VALUES ('c', 'Hokkaido');
INSERT INTO raca (especie, nome) VALUES ('c', 'Husky Siberiano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Malamute do Alaska');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pastor Finlandês da Lapônia');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pelado Mexicano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pelado Peruano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pharaoh Hound');
INSERT INTO raca (especie, nome) VALUES ('c', 'Podengo Canário');
INSERT INTO raca (especie, nome) VALUES ('c', 'Podengo Ibicenco');
INSERT INTO raca (especie, nome) VALUES ('c', 'Podengo Português');
INSERT INTO raca (especie, nome) VALUES ('c', 'Samoieda');
INSERT INTO raca (especie, nome) VALUES ('c', 'Shiba');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spitz Alemão');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spitz Japonês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Thai Ridgeback');
INSERT INTO raca (especie, nome) VALUES ('c', 'Volpino Italiano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Basset Artesiano Normando');
INSERT INTO raca (especie, nome) VALUES ('c', 'Basset Hound');
INSERT INTO raca (especie, nome) VALUES ('c', 'Basset Fulvo da Bretanha');
INSERT INTO raca (especie, nome) VALUES ('c', 'Beagle');
INSERT INTO raca (especie, nome) VALUES ('c', 'Beagle Harrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Billy');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão de Santo Humberto');
INSERT INTO raca (especie, nome) VALUES ('c', 'Coonhound Preto e Castanho');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dálmata');
INSERT INTO raca (especie, nome) VALUES ('c', 'Foxhound Americano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Foxhound Inglês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Gascão de Saintongeois');
INSERT INTO raca (especie, nome) VALUES ('c', 'Grande Azul da Gasconha');
INSERT INTO raca (especie, nome) VALUES ('c', 'Griffon Nivernais');
INSERT INTO raca (especie, nome) VALUES ('c', 'Petit Basset Griffon Vendéen');
INSERT INTO raca (especie, nome) VALUES ('c', 'Rhodesian Ridgback');
INSERT INTO raca (especie, nome) VALUES ('c', 'Braco Alemão Pelo Curto');
INSERT INTO raca (especie, nome) VALUES ('c', 'Braco Alemão Pelo Duro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Braco de Auvergne');
INSERT INTO raca (especie, nome) VALUES ('c', 'Braco de Bourbonnais');
INSERT INTO raca (especie, nome) VALUES ('c', 'Braco Húngaro de Pelo Curto');
INSERT INTO raca (especie, nome) VALUES ('c', 'Braco Húngaro de Pelo Duro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Braco Italiano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cesky Fousek');
INSERT INTO raca (especie, nome) VALUES ('c', 'Grande Musterlander');
INSERT INTO raca (especie, nome) VALUES ('c', 'Griffon de Aponte de Pêlo Duro Korthals');
INSERT INTO raca (especie, nome) VALUES ('c', 'Perdigueiro Português');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pointer Inglês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Setter Gordon');
INSERT INTO raca (especie, nome) VALUES ('c', 'Setter Inglês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Setter Irlandês Ruivo');
INSERT INTO raca (especie, nome) VALUES ('c', 'Setter Irlandês Vermelho e Branco');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel Bretão');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel da Picardia');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel Francês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spinone Italiano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Stabyhoun');
INSERT INTO raca (especie, nome) VALUES ('c', 'Weimaraner');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão d''água Frisado');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão d''água Português');
INSERT INTO raca (especie, nome) VALUES ('c', 'Chesapeake Bay Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Clumber Spaniel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cocker Spaniel Americano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cocker Spaniel Inglês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Flat Coated Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Golden Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Labrador Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Retriever Nova Escócia Duck Tolling');
INSERT INTO raca (especie, nome) VALUES ('c', 'Lagotto Romagnolo');
INSERT INTO raca (especie, nome) VALUES ('c', 'Springer Spaniel Inglês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel d''água Irlandês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel d''água Americano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Welsh Springer Spaniel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão d''água Frisado');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão d''água Português');
INSERT INTO raca (especie, nome) VALUES ('c', 'Chesapeake Bay Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Clumber Spaniel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cocker Spaniel Americano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cocker Spaniel Inglês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Flat Coated Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Golden Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Labrador Retriever');
INSERT INTO raca (especie, nome) VALUES ('c', 'Retriever Nova Escócia Duck Tolling');
INSERT INTO raca (especie, nome) VALUES ('c', 'Lagotto Romagnolo');
INSERT INTO raca (especie, nome) VALUES ('c', 'Springer Spaniel Inglês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel d''água Irlandês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel d''água Americano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Welsh Springer Spaniel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bichon Frisé');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bichon Havanês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bolonhês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Boston Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Buldogue Francês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cão de Crista Chinês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Cavalier King Charles Spaniel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Chihuahua');
INSERT INTO raca (especie, nome) VALUES ('c', 'Coton de Tuléar');
INSERT INTO raca (especie, nome) VALUES ('c', 'Griffon de Bruxelas');
INSERT INTO raca (especie, nome) VALUES ('c', 'King Charles Spaniel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Kromfohrländer');
INSERT INTO raca (especie, nome) VALUES ('c', 'Lhasa Apso');
INSERT INTO raca (especie, nome) VALUES ('c', 'Maltês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Petit Brabançon');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pequeno Cão Leão');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pequeno Spaniel Continental - Papillon / Phalene');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pequinês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Poodle');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pug');
INSERT INTO raca (especie, nome) VALUES ('c', 'Shih Tzu');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel Japonês');
INSERT INTO raca (especie, nome) VALUES ('c', 'Spaniel Tibetano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Terrier Tibetano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Afghan Hound');
INSERT INTO raca (especie, nome) VALUES ('c', 'Borzoi');
INSERT INTO raca (especie, nome) VALUES ('c', 'Deerhound');
INSERT INTO raca (especie, nome) VALUES ('c', 'Galgo Espanhol');
INSERT INTO raca (especie, nome) VALUES ('c', 'Greyhound');
INSERT INTO raca (especie, nome) VALUES ('c', 'Pequeno Lebrel Italiano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Saluki');
INSERT INTO raca (especie, nome) VALUES ('c', 'Whippet');
INSERT INTO raca (especie, nome) VALUES ('c', 'Wolfhound Irlandês');
INSERT INTO raca (especie, nome) VALUES ('c', 'American Bully');
INSERT INTO raca (especie, nome) VALUES ('c', 'American Pit Bull Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Biewer Terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Boerboel');
INSERT INTO raca (especie, nome) VALUES ('c', 'Bulldog Americano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Buldogue Campeiro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Buldogue Serrano');
INSERT INTO raca (especie, nome) VALUES ('c', 'Dogue Brasileiro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Olde EnglishBulldogge Brasileiro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Ovelheiro Gaúcho');
INSERT INTO raca (especie, nome) VALUES ('c', 'Rastreador Brasileiro');
INSERT INTO raca (especie, nome) VALUES ('c', 'Toy Fox terrier');
INSERT INTO raca (especie, nome) VALUES ('c', 'Veadeiro Pampeano');

INSERT INTO raca (especie, nome) VALUES ('g', 'Vira-lata SRD (Sem Raça Definida)');
INSERT INTO raca (especie, nome) VALUES ('g', 'Abissínio');
INSERT INTO raca (especie, nome) VALUES ('g', 'American Shorthair');
INSERT INTO raca (especie, nome) VALUES ('g', 'Angorá');
INSERT INTO raca (especie, nome) VALUES ('g', 'Azul Russo');
INSERT INTO raca (especie, nome) VALUES ('g', 'Bengala');
INSERT INTO raca (especie, nome) VALUES ('g', 'Burmese');
INSERT INTO raca (especie, nome) VALUES ('g', 'Chartreux');
INSERT INTO raca (especie, nome) VALUES ('g', 'Cornish Rex');
INSERT INTO raca (especie, nome) VALUES ('g', 'Devon Rex');
INSERT INTO raca (especie, nome) VALUES ('g', 'Exótico');
INSERT INTO raca (especie, nome) VALUES ('g', 'Himalaio');
INSERT INTO raca (especie, nome) VALUES ('g', 'Maine Coon');
INSERT INTO raca (especie, nome) VALUES ('g', 'Mau Egípcio');
INSERT INTO raca (especie, nome) VALUES ('g', 'Munchkin');
INSERT INTO raca (especie, nome) VALUES ('g', 'Norueguês da Floresta');
INSERT INTO raca (especie, nome) VALUES ('g', 'Oriental');
INSERT INTO raca (especie, nome) VALUES ('g', 'Pelo Curto Brasileiro');
INSERT INTO raca (especie, nome) VALUES ('g', 'Pelo Curto Europeu');
INSERT INTO raca (especie, nome) VALUES ('g', 'Pelo Curto Inglês');
INSERT INTO raca (especie, nome) VALUES ('g', 'Persa');
INSERT INTO raca (especie, nome) VALUES ('g', 'Ragdoll');
INSERT INTO raca (especie, nome) VALUES ('g', 'Sagrado da Birmânia');
INSERT INTO raca (especie, nome) VALUES ('g', 'Savannah');
INSERT INTO raca (especie, nome) VALUES ('g', 'Scottish Fold');
INSERT INTO raca (especie, nome) VALUES ('g', 'Siamês');
INSERT INTO raca (especie, nome) VALUES ('g', 'Sphynx');

-- INSERT INTO anuncio_distancia (anuncio1_id, anuncio2_id, distancia)
--   SELECT an2.id, an1.id, (point(an2.local_longitude, an2.local_latitude) <@> point(an1.local_longitude, an1.local_latitude)) * 1.609344
--   FROM anuncio as an1 INNER JOIN anuncio as an2 ON an2.id = 12000 WHERE an1.id < an2.id;
--
-- SELECT anu FROM AnuncioEntity AS anu INNER JOIN anu.animalEntity AS ani
--   WHERE anu.status = 'a'
--   AND (ani.nome = '' OR ani.nome = :nome)
--   AND (ani.genero = 'n'  OR ani.genero = :genero)
--   AND (anu.natureza = 'p' AND anu.dataHora < :dataHora)
--   AND (anu.natureza = 'e' AND anu.dataHora > :dataHora)

-- -- anuncio completo
-- SELECT
--   anu.id,
--   anu.usuario_id,
--   anu.natureza,
--   anu.animal_id,
--   anu.data_hora,
--   anu.data_hora_resolvido,
--   anu.local_longitude,
--   anu.local_latitude,
--   anu.local_endereco,
--   anu.detalhes,
--   anu.status,
--   ani.especie,
--   ani.genero,
--   ani.nome as ani_nome,
--   ani.outros,
--   ani.pelagem,
--   ani.porte,
--   ani.raca_id,
--   cor.id as cor_id,
--   cor.a,
--   cor.b,
--   cor.l,
--   pecu.id as pecu_id,
--   pecu.local_id,
--   pecu.tipo_id,
--   pecu_l.nome as pecu_l_nome,
--   pecu_t.nome as pecu_t_nome,
--   raca.especie,
--   raca.nome
-- FROM anuncio anu
-- INNER JOIN animal ani ON ani.id=anu.animal_id
-- LEFT JOIN cor ON ani.id=cor.animal_id
-- LEFT JOIN peculiaridade pecu ON ani.id=pecu.animal_id
-- LEFT JOIN peculiaridade_local pecu_l ON pecu.local_id=pecu_l.id
-- LEFT JOIN peculiaridade_tipo pecu_t ON pecu.tipo_id=pecu_t.id
-- LEFT JOIN raca ON ani.raca_id=raca.id
--
-- -- anuncio simplificado
-- SELECT
--   anu.id,
--   anu.natureza,
--   anu.data_hora,
--   anu.data_hora_resolvido,
--   anu.local_longitude,
--   anu.local_latitude,
--   anu.local_endereco,
--   anu.status,
--   ani.especie,
--   ani.genero,
--   ani.nome as ani_nome,
--   media.caminho
-- FROM anuncio anu
--   INNER JOIN animal ani ON ani.id=anu.animal_id
--   LEFT JOIN cor ON ani.id=cor.animal_id
--   LEFT JOIN peculiaridade pecu ON ani.id=pecu.animal_id
--   LEFT JOIN peculiaridade_local pecu_l ON pecu.local_id=pecu_l.id
--   LEFT JOIN peculiaridade_tipo pecu_t ON pecu.tipo_id=pecu_t.id
--   LEFT JOIN raca ON ani.raca_id=raca.id
--   LEFT JOIN media ON (media.anuncio_id=anu.id AND media.capa=true)