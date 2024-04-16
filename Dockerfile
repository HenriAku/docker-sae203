# Utilisation de l'image Debian officielle comme base
FROM debian:latest

# Installation des dépendances Java
RUN apt-get update && apt-get install -y openjdk-11-jdk

# Copie des fichiers source dans l'image
ADD  app /morpion/

# Définition du répertoire de travail
WORKDIR /app

# Compilation des fichiers Java
RUN javac morpion/*.java

# Commande par défaut pour exécuter votre service
CMD ["java", "morpion/Morpion"]