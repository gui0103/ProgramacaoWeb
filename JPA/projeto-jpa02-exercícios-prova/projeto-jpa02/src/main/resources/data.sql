-- ao criamos um arquivo de nome "data.sql" na pasta "resources"
-- caso a configuração "spring.jpa.defer-datasource-initialization" estiver true,
-- todas as instruções aqui serão executadas
-- PODEM haver mais de 1 insert, porém devem sempre terminar com ";"
insert into salgadinho
(nome, nivel_sal, preco, apimentado)
values
('Doritos', 6, 7.50, true),
('Doritos Jalapeño', 6, 7.65, true),
('Pringles', 8, 10.90, false),
('Chettos', 5, 5.80, false),
('Ruffles', 5, 7.10, false),
('Baconzitos', 3, 6.25, false),
('Fandangos', 2, 5.70, false),
('Ovinhos', 2, 8.50, false),
('Torcida', 3, 4.32, false),
('Cebolitos', 3, 5.90, false),
('Tyrrells Trufas Negras', 4, 34.90, false),
('Tyrrells Sal Marinho', 4, 20.0, false);

-- insert numa 2a tabela;

-- insert numa 3a tabela;