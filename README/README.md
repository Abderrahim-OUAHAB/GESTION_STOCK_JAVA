# Système de Gestion de Stock

## Aperçu du Projet

Ce projet est un système de gestion de stock implémenté en Java avec JavaFX pour l'interface utilisateur et MySQL pour la base de données. Le système prend en charge les opérations CRUD de base et met automatiquement à jour la base de données en utilisant une classe Singleton pour maintenir une seule connexion à la base de données. L'application fournit également des tableaux de bord pour différents éléments (clients, produits, ventes, inventaire, paiements) et permet de télécharger les données sous forme de fichiers PDF.

## Fonctionnalités

1. **Opérations CRUD** : Le système prend en charge les opérations de Création, Lecture, Mise à jour et Suppression pour la gestion des éléments de stock.
2. **Connexion Singleton à la Base de Données** : Une classe Singleton (`SingleConnection.java`) est utilisée pour garantir qu'une seule connexion à la base de données est maintenue tout au long de l'application.
3. **Tableaux de Bord** : Des tableaux de bord séparés sont fournis pour les clients, les produits, les ventes, l'inventaire et les paiements.
4. **Téléchargement PDF** : Les données de chaque élément peuvent être téléchargées sous forme de fichiers PDF.

## Configuration et Installation

### Prérequis

- Kit de Développement Java (JDK) 21
- JavaFX SDK
- Base de Données MySQL
- JAR (itextpdf-5.1.0-mysql-connector-j-8.2.0) .

### Configuration de la Base de Données

1. Installez MySQL et créez une base de données.
2. Utilisez les scripts SQL fournis pour créer les tables nécessaires et insérer les données initiales.

### Compilation du Projet

1. Clonez le dépôt.
2. Ouvrez le projet dans votre IDE préféré (comme Eclipse).
3. Ajoutez le SDK JavaFX et jar:(itextpdf-5.1.0-mysql-connector-j-8.2.0) à votre projet. (DANS DOSSIER "OUTILS")
4. Configurez la connexion à la base de données dans `SingleConnection.java`.

## Création d'un JAR Exécutable

https://youtu.be/xTHum2n2igs?si=kUHyV58z_Sp6iwGH
Puis executer :

    java -jar --module-path path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.swing,javafx.base,javafx.media,javafx.web,javafx.swt path/votre_jar.jar

**Pour plus de détails, consultez le dossier "DEMO" ou contactez-moi par email : abderrahim.ouahab23@ump.ac.ma .**

**NB : - Si le fichier PDF ne se lance pas, assurez-vous de modifier la fonction `static void openFile(String path)` dans chaque classe `PdfClients.java` et autres. Commentez la ligne pour macOS et activez celle pour Windows.
       - Pour les Pdf telechrager voir le dossier "GESTION_MAGASIN/PDF" **

### Développé par  Abderrahim OUAHAB.
