# Laboratoire 2 – Modificabilité  
**Cours : Architecture des logiciels (GEI311)**  
**Nom : Guilavogui Siba**  
**Code permanent : GUIS15049901**  
**Session : Automne 2025**

---

## Section I — Critique du diagramme initial

Le diagramme de départ proposait trois classes principales :  
- `User` : pour créer et mettre à jour les tickets.  
- `Admin` : pour assigner et fermer les tickets.  
- `Ticket` : pour représenter un ticket avec un titre, une description et un statut.  

### Forces
- Structure simple et facile à comprendre.  
- Relation claire entre `User`, `Admin` et `Ticket`.  

### Faiblesses
- Les statuts et priorités étaient représentés par de simples chaînes de caractères (`String`), ce qui entraîne un risque d’erreurs (fautes de frappe, valeurs incohérentes).  
- La description d’un ticket était limitée à un seul champ, pas adaptée pour différents types de contenus (texte, image, vidéo, fichier).  
- La classe `Ticket` risquait de devenir trop chargée si on ajoutait de nouveaux attributs ou comportements.  
- Peu d’anticipation des évolutions futures → **faible modificabilité**.

---

## Section II — Améliorations du diagramme et justifications

### Tableau comparatif — Avant vs Après amélioration

| Élément | Diagramme initial (Partie 1) | Diagramme amélioré (Partie 2) | Justification (modificabilité) |
|---------|-------------------------------|--------------------------------|--------------------------------|
| **Ticket.description** | Un simple champ `String` | Classe `Description` (texte, image, vidéo, fichier) | Permet de supporter différents types de contenu sans modifier `Ticket`. |
| **Ticket.status** | `String` libre | `enum Status {OUVERT, ASSIGNE, VALIDATION, TERMINE}` | Évite les fautes de frappe, centralise les valeurs possibles, extensible si on veut ajouter d’autres statuts. |
| **Ticket.priority** | `String` libre | `enum Priority {BASSE, MOYENNE, HAUTE}` | Plus robuste, facile d’ajouter une priorité "CRITIQUE". |
| **Ticket → Description** | Pas de relation | Composition `List<Description>` | Permet plusieurs descriptions par ticket, meilleure extensibilité. |
| **DescriptionType** | Non présent | `enum DescriptionType {TEXTE, IMAGE, VIDEO, FICHIER}` | Permet d’ajouter de nouveaux types facilement (ex. AUDIO). |
| **Responsabilités** | `Ticket` très chargé (tout géré dans la même classe) | Responsabilités mieux réparties (Ticket ↔ Description ↔ enums) | Respect des principes GRASP (*High Cohesion, Low Coupling*). |

---

## Section III — Améliorations dans le code et conception

- **Menu interactif (console)** : permet de créer des utilisateurs, des tickets, ajouter des descriptions, assigner des tickets, mettre à jour les statuts, fermer les tickets, et ajouter des commentaires.  
- **Encapsulation et validation** : utilisation de `Objects.requireNonNull` pour éviter les erreurs null et protéger l’intégrité des objets.  
- **Listes protégées** : `List<Description>` et `List<Ticket>` retournées avec des copies défensives → réduit le couplage.  
- **GRASP appliqués** :
  - *Information Expert* : chaque classe gère ses propres données.  
  - *Controller* : la classe `Main` agit comme contrôleur entre l’utilisateur et les objets.  
  - *Creator* : `User` crée des tickets, `Ticket` crée ses descriptions.  
  - *Low Coupling & High Cohesion* : classes indépendantes et focalisées sur une seule responsabilité.  
- **Patrons de conception implicites** :
  - *Factory Method (implicite)* : création de tickets et descriptions via des méthodes dédiées.  
  - *Strategy (léger)* : enums pour gérer l’extensibilité des statuts et priorités.  
  - *Composition over Inheritance* : `Ticket` contient une liste de `Description`.
Rôle de l’administrateur

L’administrateur (Admin) est responsable de la gestion globale du système :

Il crée les utilisateurs (développeurs, testeurs, ingénieurs, etc.) avant qu’ils puissent interagir avec les tickets.

Il peut assigner des tickets, fermer des tickets, et visualiser tous les tickets.

Ce choix de conception respecte les principes de :

Contrôle centralisé (Controller) → les opérations critiques sont sous supervision d’un seul acteur.

Faible couplage → les utilisateurs ne manipulent pas directement les objets d’administration.

Haute cohésion → l’administrateur se concentre sur la gestion et la supervision, sans interférer dans la logique métier des tickets.  

---

## Section IV — Leçons apprises

- Un bon diagramme UML initial doit anticiper les évolutions → c’est la clé de la **modificabilité**.  
- L’utilisation d’`enum` et de classes dédiées (comme `Description`) améliore la clarté, la maintenabilité et l’extensibilité du code.  
- Les principes GRASP (Information Expert, Controller, Creator, Low Coupling, High Cohesion) guident la répartition des responsabilités dans un système orienté objet.  
- L’importance de tester via un menu console qui relie **le diagramme théorique** et **l’exécution pratique**.  
- Enfin, j’ai appris à intégrer progressivement les notions de conception (composition, enums, validation) pour produire un système flexible et évolutif; j'ai aussi appris à appliquer les bonnes pratiques de GRASP; l'utilité de la modificabilité dans un projet de gestion.

Lien pour la démonstration https://uqac.ca.panopto.com/Panopto/Pages/Viewer.aspx?id=881914f3-7960-45a1-84ca-b36f013ffff8
Lien depot GitHub https://github.com/sibaguilavogui/laboratoire2-Modificabilit-

---

				 session Automne 2025.*

