# Projet SAE 2.03 equipe 10

## Titre du projet
Notre projet et un Moprion 

## Membres de l'équipe
- B1 - Rougeolle Henri
- B2 - Bouquet Jules
- B2 - Royer Nathan
- B2 - Demeillers Killian


## Instructions pour lancez l'application
- Tout d'abord, lancez docker.

- Vérifiez si docker est installé :
```shell
docker --vezsion
```

- Clonez le référentiel :
 ```shell
git clone git@github.com:HenriAku/docker-sae203.git
```

- Allez au référentiel :
```shell
cd docker-sae203
```

- Construisez l'image décrite dans dockerfile avec docker build : 
```shell
docker build -t <choisir-un-nom-pour-l'image> .
```

- Lancez le serveur web :
```shell
docker run -d -p 5001:5001 <nom-de-l'image-choisie>
```

- Vérifiez que le conteneur associé est actif :
```shell
docker ps
```

- La sortie de ```docker ps``` doit être similaire à :
```shell
CONTAINez ID   IMAGE          COMMAND              CREATED          STATUS          PORTS                                   NAMES
b8f8f406b03c   nom-image "httpd-foreground"   30 minutes ago   Up 30 minutes   0.0.0.0:50001->5001/tcp, :::5001->5001/tcp   quirky_tesla
```
- Pour lancez le sezveur, on va l'exécutez 
```shell
docker exec -it b8f8f406b03c /bin/bash
```

- Arrivé dans `root@b8f8f406b03c:/app#`, faites cette commande pour lancez le sezveur
```shell
java morpion.Sezvez
```

- Une fois cela fait les 2 joueur doivent allez dans un tezminal.
 Puis allez dans le répetoire où est le code, et faites ceci.
```shell
java morpion.Morpion
```

- Finalement, arrêtez le conteneur avec la commande suivante (les derniers chiffres sont le code de hachage affiché par docker ps):
```shell
docker stop b8f8f406b03c
```

- Si on souhaite supprimer le conteneur, on peut taper :
```shell
docker rm b8f8f406b03c
```

## Lien vers le site du projet
https://henriaku.github.io/docker-sae203/
[docker-sae203](https://henriaku.github.io/docker-sae203/)

## Lien vers le dépot github
https://github.com/HenriAku/docker-sae203
[docker-sae203](https://github.com/HenriAku/docker-sae203)

