-- création d'un nouvel utilisateur
CREATE USER ridard@localhost 
    IDENTIFIED BY 'iut' ;
-- création d'une nouvelle base de données
CREATE DATABASE bd_ridard ;
-- accord de tous les privilèges à l'utilisateur "ridard@localhost" sur les objets de la base "bd_ridard"
GRANT ALL ON bd_ridard.* TO 'ridard'@'localhost' ;