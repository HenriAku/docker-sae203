# Utilisation de l'image Debian officielle comme base
FROM debian:latest

# Installation du JDK OpenJDK 11
RUN apt-get update && apt-get install -y default-jdk

# Copie des fichiers source dans l'image
COPY app /app

# Définition du répertoire de travail
WORKDIR /app

# Compilation des fichiers Java
RUN javac -encoding UTF-8 morpion/*.java

# Commande par défaut pour exécuter votre service
CMD ["java", "morpion/Morpion"]