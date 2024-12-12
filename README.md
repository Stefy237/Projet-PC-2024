# General

## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


# Projet de PC

L'intégralité du code source du projet de programmation concurente 2024 se trouve dans le dossier src. Celui a été fait sans build (de type maven ou gradle) donc ne peut malheureusement pour l'instant être compilé que dans l'éditeur VScode. L'aborescence du projet est la suivante : 

- src
    - prodcons
        - v1
        - v2
        - v3
        - v4
        - v5
        - v6
        - vadd
            - o1
            - o2
    - threads
    - utils

## Le package prodcons

Il contient les différents codes du buffer producteurs/consommateurs suivant les différents objectifs. Dans chaque sous package v<i> se trouvent les classe ProdConsBuffer.java représentant l'implémentation du buffer et TestProdCons.java représentant la classe de test. 

Pour le package vadd, il contient deux sous package o1 et o2 représentatant chacun des objectifs de l'objectif additionnel. 


## Le package threads

Ce package contient les différentes implémentations des threads utilisés tout le long du projet.


## Le package utils

Ce package contient les différentes classe "utiles" du projet telle que la classe Message.java ou l'interface IProdConsBuffer.java.


> Malheureusement les options de configurations sont pour l'instant codées en "dur" à l'intérieur des programmes; les fichiers de configuration n'étant pas encore disponible.



*Version du 12/12/2024*
