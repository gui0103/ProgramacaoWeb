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
('Ruffles', 5, 7.10, false);

-- insert numa 2a tabela;

-- insert numa 3a tabela;