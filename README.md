# Gestion des Approvisionnements - Module Commandes Fournisseurs (Tricol)

## ✅ But de ce document
Ce README décrit le projet "CommandeFournisseur" : contexte métier, architecture, technologies, installation, usage de l'API REST (endpoints principaux), pagination/filtrage, gestion des mouvements de stock et méthodes de valorisation (CUMP/FIFO). Il inclut des exemples d'appels (curl/Postman) et des recommandations opérationnelles.

## Contexte
L’entreprise Tricol, spécialisée dans la fabrication de vêtements professionnels, digitalise son processus d’approvisionnement. Après le module de gestion des fournisseurs, ce projet ajoute la gestion complète des commandes fournisseurs pour assurer le suivi des approvisionnements (matières premières, équipements) et la valorisation du stock.

## Objectif du projet
Développer une API REST avec Spring Boot pour gérer le cycle de vie des commandes fournisseurs : création, modification, annulation, suivi des livraisons, mouvements de stock, et valorisation des coûts via FIFO ou CUMP. Le projet applique les bonnes pratiques Spring Boot, Spring Data JPA, MapStruct, Liquibase et Swagger/OpenAPI.

---

## Fonctionnalités principales
- Gestion des fournisseurs : CRUD (société, adresse, contact, email, téléphone, ville, ICE).
- Gestion des produits : CRUD (nom, description, prix unitaire, catégorie, stock, CUMP).
- Gestion des commandes fournisseurs : création, modification, annulation, consultation, calcul automatique du montant total, statut (EN_ATTENTE, VALIDEE, LIVREE, ANNULEE).
- Mouvements de stock automatiques : création de mouvements (ENTREE / SORTIE / AJUSTEMENT) lors des réceptions ou sorties liées aux commandes.
- Valorisation du stock : support de CUMP (par défaut) et FIFO (configurable).
- Consultation de l’historique des mouvements : filtrable par produit, type de mouvement, ou commande.
- Pagination & filtrage sur les endpoints de consultation (fournisseurs, produits, commandes, mouvements).

---

## Architecture & Organisation du code
Architecture en couches :
- Controller : endpoints REST (package `controller`).
- Service : logique métier (package `service`).
- Repository : accès données Spring Data JPA (package `repository`).
- DTO & Mapper : échanges API ↔ entités (MapStruct) (package `model.dto`, `model.mapper`).
- Entités JPA (package `model.entities`).
- Liquibase : scripts de migration (src/main/resources/db/changelog).


## Technologies utilisées
- Java 17, Spring Boot
- Spring Data JPA (Hibernate)
- MapStruct (mapping Entity ↔ DTO)
- Liquibase (migrations)
- Swagger / OpenAPI (doc auto)
- Jakarta Validation (validation champs)
- Maven (build)

---

## Modèle de données (résumé)
Exemples simplifiés d'entités :

- Fournisseur
  - id, societe, adresse, contact, email, telephone, ville, ICE

- Produit
  - id, nom, description, prixUnitaire, categorie, stock, cump

- CommandeFournisseur
  - id, dateCommande, statut, montantTotal, fournisseur (ManyToOne), commandeProduits (OneToMany)

- CommandeFournisseurProduit
  - id, commande (ManyToOne), produit (ManyToOne), quantite

- MouvementStock
  - id, dateMouvement, quantite, typeMouvement (ENTREE/SORTIE/AJUSTEMENT), produit, fournisseur (optionnel), commande (optionnel)

---

## Configuration
Les paramètres principaux se placent dans `src/main/resources/application.properties`.
Valeurs/propriétés utiles (exemples) :

- `server.port` : port HTTP (par défaut 8080)
- `app.stock.valuation-method` : méthode de valorisation (`CUMP` ou `FIFO`) — valeur par défaut : `CUMP`
- Pagination (optionnel via application.properties) :
  - `spring.data.web.pageable.default-page-size=10`
  - `spring.data.web.pageable.max-page-size=100`

---

## Démarrage du projet
1. Construire le Jar :

```bash
mvn -DskipTests package
```

2. Lancer l'application :

```bash
# avec Maven
mvn spring-boot:run

# ou avec le jar généré
java -jar target/CommandeFournisseur-0.0.1-SNAPSHOT.jar
```

3. API disponible par défaut :
```
http://localhost:8080
```

- Swagger UI (si activé) : `http://localhost:8080/swagger-ui.html` ou `/swagger-ui/index.html` selon configuration.

---

## Endpoints principaux (exemples)
Tous les endpoints supportent la pagination/tri sur les GET listant des ressources via les paramètres `page`, `size`, `sort` (Spring Data Pageable).

1) Fournisseurs
- GET /api/fournisseurs?page=0&size=10&sort=societe,asc
- GET /api/fournisseurs/{id}
- POST /api/fournisseurs
- PUT /api/fournisseurs/{id}
- DELETE /api/fournisseurs/{id}

2) Produits
- GET /api/produits?page=0&size=10&sort=nom,asc
- GET /api/produits/{id}
- POST /api/produits
- PUT /api/produits/{id}
- DELETE /api/produits/{id}

3) Commandes fournisseurs
- GET /api/commande?page=0&size=10&sort=dateCommande,desc
- GET /api/commande/{id}
- POST /api/commande
- PUT /api/commande/{id}/status?statut=VALIDEE
- DELETE /api/commande/{id}

4) Mouvements de stock
- GET /api/mouvements?produitId=3&type=ENTREE&commandeId=7
  - Les paramètres sont optionnels ; combinaison possible. Supporte pagination si souhaité.

---

## Format de réponse paginée
Les endpoints GET retournent un objet JSON contenant au minimum :
```json
{
  "content": [ /* liste d'objets DTO */ ],
  "totalElements": 123,
  "totalPages": 13
}
```

Si tu souhaites plus de métadonnées (page courante, pageSize, hasNext, hasPrevious), on peut étendre `PagedResponse` facilement.

---

## Exemples d'appels (Postman / curl)
Récupérer la page 1 (0‑based) de produits, 5 éléments par page :

```bash
curl -s 'http://localhost:8080/api/produits?page=1&size=5' | jq .
```

Récupérer mouvements filtrés par produit et type :

```bash
curl -s 'http://localhost:8080/api/mouvements?produitId=3&type=ENTREE' | jq .
```

Créer une commande (extrait JSON simplifié) :

POST /api/commande
```json
{
  "fournisseurId": 4,
  "produits": [
    { "produitId": 3, "quantite": 10 },
    { "produitId": 5, "quantite": 2 }
  ]
}
```

Réponse : 201 Created + DTO de la commande créée.

---
## Tests
Objectif
- Assurer la non-régression et la validité des fonctionnalités critiques (création commande → mouvements → valorisation).

Commandes principales (Windows cmd)
- Lancer tous les tests :

```cmd
mvn clean test
```

- Lancer une classe de test :

```cmd
mvn -Dtest=NomDeLaClasseTest test
```

- Construire sans tests :

```cmd
mvn -DskipTests package
```

Interprétation rapide des résultats
- BUILD SUCCESS : tous les tests sont passés.
- BUILD FAILURE : un ou plusieurs tests ont échoué ou il y a une erreur de compilation. Consulter `target/surefire-reports/` pour les détails.
- Couverture (JaCoCo) : fichier `target/site/jacoco/index.html` pour le rapport HTML.

Notes utiles
- Erreurs fréquentes : stubs Mockito inutilisés (supprimer ou rendre lenient) ; problèmes MapStruct/Lombok → vérifier les mappings et l'annotation processing.

---

## Conclusion
En résumé, ce module fournit une base technique et fonctionnelle solide pour gérer les commandes fournisseurs et le suivi des approvisionnements chez Tricol. Il permet de :

- Gérer le cycle de vie des commandes (création, mise à jour, annulation, suivi de statut).
- Tracer précisément les mouvements de stock associés aux opérations d'achat et mise à jour automatique des niveaux de stock.
- Valoriser le stock (implémentation CUMP, extensible à FIFO) afin de refléter fidèlement le coût d'approvisionnement.
- Consommer des bonnes pratiques techniques (architecture en couches, DTOs, mappers, migrations, validation).

Tests : des tests unitaires et d'intégration sont fournis (voir section Tests).

État actuel
- Fonctionnalités cœur implémentées : gestion des fournisseurs, produits, commandes et mouvements de stock.
- API REST paginée et triable disponible pour les principales ressources.
- Mécanique de création automatique des mouvements de stock lors des opérations pertinentes.
