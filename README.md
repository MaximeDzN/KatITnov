# Introduction

Ce projet est mon interprétation "d'imdb" par une API REST avec spring boot

# Prérequis

- Java JDK 25
- Maven 4.0.0

# Description du besoin

La société a décidé de concurrencer IMDB et commence à créer
une base de connaissances regroupant les long-métrages existants.
Vous devrez mettre en place une API à partir des stories ci-dessous :
- En tant qu’utilisateur, je souhaite ajouter et retirer un film à/de mes
favoris
- En tant qu’utilisateur, je souhaite lister mes films favoris triés par date
de sortie ou par note globale
- En tant qu’utilisateur, je souhaite pouvoir différencier les films que j’ai
vu de ceux que je n’ai pas vu et notifier quand j’ai vu un film
- En tant qu’utilisateur, je souhaite lister les films que j’ai vu et ceux
que je n’ai pas vu

# Utilisation

1) Cloner ce repository dans un environnement disposant des bonnes version de java et maven
2) Rendez-vous dans le repository et réalisez la commande mvn spring-boot:run
3) Rendez-vous sur l'url http://localhost:8080/swagger-ui/index.html pour tester
