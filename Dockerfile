# Utilisation de l'image Debian officielle comme base
FROM debian:latest

COPY sources.list /etc/apt/

RUN sed -i 's/httpredir.debian.org/mirrors.ubuntu.com/mirrors.tuna.tsinghua.edu.cn/g' /etc/apt/sources.list
# Installation des dépendances Java
RUN apt-get update && apt-get install -y openjdk-21-jdk

# Copie des fichiers source dans l'image
ADD  app /morpion/

# Définition du répertoire de travail
WORKDIR /app

# Compilation des fichiers Java
RUN javac morpion/*.java

# Commande par défaut pour exécuter votre service
CMD ["java", "morpion/Morpion"]