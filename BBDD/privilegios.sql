# Privilegios para `AdminP1`@`localhost`

GRANT ALL PRIVILEGES ON *.* TO 'AdminP1'@'localhost' IDENTIFIED BY PASSWORD '*63CC9B3B1A2B6487C3A924AD5F23BA1586A4618D' WITH GRANT OPTION;

GRANT ALL PRIVILEGES ON `abd`.* TO 'AdminP1'@'localhost' WITH GRANT OPTION;


# Privilegios para `UsuarioP1`@`localhost`

GRANT USAGE ON *.* TO 'UsuarioP1'@'localhost' IDENTIFIED BY PASSWORD '*9B11F8D6D0FD12212185B4FEEF05CA05A494183A';

GRANT SELECT ON `abd`.`series` TO 'UsuarioP1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON `abd`.`sigue` TO 'UsuarioP1'@'localhost';

GRANT SELECT ON `abd`.`pertenece` TO 'UsuarioP1'@'localhost';

GRANT SELECT ON `abd`.`genero` TO 'UsuarioP1'@'localhost';

GRANT SELECT ON `abd`.`episodios` TO 'UsuarioP1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON `abd`.`usuarios` TO 'UsuarioP1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON `abd`.`visto` TO 'UsuarioP1'@'localhost';

GRANT SELECT, INSERT, UPDATE, REFERENCES ON `abd`.`votaserie` TO 'UsuarioP1'@'localhost';

GRANT SELECT, INSERT, UPDATE, REFERENCES ON `abd`.`votaepisodio` TO 'UsuarioP1'@'localhost';

GRANT SELECT, REFERENCES ON `abd`.`interpreta` TO 'UsuarioP1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON `abd`.`comentaserie` TO 'UsuarioP1'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES ON `abd`.`comentaepisodio` TO 'UsuarioP1'@'localhost';