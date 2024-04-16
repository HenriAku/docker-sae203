### Projet SAE 2.03, équipe 10

# Mise en place et organisation du projet
Nous nous sommes concertés et nous avons décidé que le projet serait un morpion à deux joueurs, codé en java sur une IHM graphique, en swing.

# Programme java
Nous avons donc commencé par le code du Morpion en java, avec ces contraintes :

- Deux joueurs (clients) doivent pouvoir se connecter.
- Un serveur (plus tard hébergé sur docker) doit gérer la connexion entre les joueurs.

Nous avons donc décidé de diviser le code en deux fichiers :
- Un fichier ``Morpion.java`` (qu'on aurait pu appeler ``Client.java``) qui sera exécuté par les clients et qui va gérer l'interface des joueurs, en recevant et envoyant des informations au serveur.
- Un fichier ``Server.java`` qui sera exécuté sur le docker et qui va faire le lien entre les joueurs. Les clients devront se connecter sur le serveur tous les deux pour pouvoir jouer.

<img src="https://github.com/HenriAku/docker-sae203/assets/107880155/8bc37057-4163-498c-9cfa-6a7a4f3160e6" alt="image" width="70%" height="70%"/>

## Cas type d'une connexion
Nous ne rentreront pas trop en détail sur le contenu de ``Morpion.java``, comme ce n'est pas le but de cette saé.
Ainsi, nous nous concentrerons principalement sur l'échange de données clients <-> serveur dans cette section.

En lançant l'application, nous sommes accueillis par cet écran.

<img src="https://github.com/HenriAku/docker-sae203/assets/107880155/e70d4055-3fc0-47ef-a7f4-41713e6f89f5" alt="image" width="30%" height="30%"/>


Pour se connecter au serveur, nous avons besoin de son IP.
Après avoir entré l'IP et appuyé sur "Rejoindre une partie", le serveur attend un second joueur :

*Ci-dessous l'interface du client 1 et la console du serveur*

<img src="https://github.com/HenriAku/docker-sae203/assets/107880155/91b5004a-6cb8-4350-84e7-5a89789202bc" alt="image" width="50%" height="50%"/>


Ainsi, on connecte le second joueur de la même manière, et les deux joueurs ont accès à la partie :

<img src="https://github.com/HenriAku/docker-sae203/assets/107880155/d3355bcc-13bb-4cd7-8c78-35d169dd3c37" alt="image" width="50%" height="50%"/>

La connexion ne se coupe que lorsqu'un joueur appuie sur Quitter, pour leur permettre de rejouer sans fermer les Sockets du serveur.

## Code

Pour recevoir et envoyer les données, les clients passent par un serveur.
Les seuls données que les clients envoient sont le signe qu'ils ont joué et sa position. (Et aussi l'action du bouton "Rejouer").
Ensuite, ils calculent chacun de leur coté si c'est leur tour, leur interface et si un joueur a gagné.

<img src="https://github.com/HenriAku/docker-sae203/assets/107880155/7b5de8ce-d677-4102-bca5-5f6777df3a25" alt="image" width="75%" height="75%"/>


# Mise en place du serveur via Docker
