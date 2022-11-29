![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)

# Projet 9 Openclassrooms

Mediscreen est une application permettant de suivre les notes médicales de patient afin de détecter les risques de diabète.

# Technologies

- Java 11
- Spring Boot
- Thymeleaf
- Feign
- Docker

# Lancement de l'application

Avant de démarrer l'application, en invite de commandes veuillez vous rendre dans le dossier mediscreenUI et entrée cette ligne de commande:
docker-compose up -d

Ceci permettra de démarrer les conteneurs des microServices sachant que :
- L'API Patient utilise le port 8081
- L'API Note utilise le port 8082
- L'API Report utilise le port 8083

Vous pouvez ensuite démarrer l'application mediscreenUI.

Pour arrêter les conteneurs, utiliser la commande suivante :
docker-compose stop

# Documentation API

Lorsque le docker compose est lancé vous trouverez la documentation des API aux url suivantes : 

La documentation de l'API du microService Patient :
http://localhost:8081/swagger-ui.html

La documentation de l'API du microService Note :
http://localhost:8082/swagger-ui.html

La documentation de l'API du microService Report :
http://localhost:8083/swagger-ui.html
