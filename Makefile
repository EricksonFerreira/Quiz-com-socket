conf:
	mysql -u root -p --execute="drop database if exists socket; create database socket; drop user if exists 'socket'; create user 'socket' identified by 'socket'; grant all privileges on socket.* to 'socket';"
	sed -i 's/DB_DATABASE.*/DB_DATABASE=socket/' .env
	sed -i 's/DB_USERNAME.*/DB_USERNAME=socket/' .env
	sed -i 's/DB_PASSWORD.*/DB_PASSWORD=socket/' .env
    sed -i "use socket;CREATE TABLE `perguntas` ( `id` int(11) NOT NULL, `titulo` varchar(100) NOT NULL, `resposta` varchar(100) NOT NULL, `jaRespondida` tinyint(1) NOT NULL DEFAULT 1, `ativa` tinyint(1) NOT NULL DEFAULT 1, `respondeu` varchar(100) DEFAULT NULL)"