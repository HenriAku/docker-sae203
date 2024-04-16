## Instructions pour lancer l'application
- Tout d'abord lancer docker

- Vérifiez si docker est installé :
```shell
docker --version
```

- Cloner le référentiel :
 ```shell
git clone git@github.com:HenriAku/docker-sae203.git
```

- Aller au référentiel :
```shell
cd docker-sae203
```

- Construisez l'image décrite dans dockerfile avec docker build : 
```shell
docker build -t <choisir-un-nom-pour-l'image> .
```

- Lancer le serveur web :
```shell
docker run -d -p 5001:5001 <nom-de-l'image-choisie>
```

- Vérifier que le conteneur associé est actif :
```shell
docker ps
```

- La sortie de ```docker ps``` doit être similaire à :
```shell
CONTAINER ID   IMAGE          COMMAND              CREATED          STATUS          PORTS                                   NAMES
b8f8f406b03c   nom-image "httpd-foreground"   30 minutes ago   Up 30 minutes   0.0.0.0:50001->5001/tcp, :::5001->5001/tcp   quirky_tesla
```
-Pour lancer le Server il faut l'executer 
```shell
docker exec -it b8f8f406b03c /bin/bash
```

-Arriver dans `root@b8f8f406b03c:/app#` faite cette commande pour lancer le Server
```shell
java morpion.Server
```

-Une fois cela fait les 2 joueur doivent aller dans un terminal.
 Puis aller dans le repetoire ou est le code et faite ceci
```shell
java morpion.Morpion
```

- Finalement, arrêtez le conteneur avec la commande suivante (les dernières chiffres sont le code de hachage affiché par docker ps):
```shell
docker stop b8f8f406b03c
```

- Encore, si on souhaite supprimer le conteneur, on peut taper :
```shell
docker rm b8f8f406b03c
```

**NOTE :** Au lieu du code de hachage, on peut toujours taper le nom du conteneur. Dans le cas d'exemple ce nom est ```quirky_tesla```
