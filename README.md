# ckonsoru
- adresse git : <github.com/CharlesChaudron/ckonsoru>

## Introduciton

L'application est divisée en plusieurs classes pour répartir et cloisonner les différentes fonctions du programme. Voici les différentes classes avec la description de leurs utilités ci dessous.

## les différentes classes

### Menu

La classe Menu a pour responsabilité de gérer les interactions entre le programme et l'utilisateur. Elle est responsable de l'affichage des menus et de toutes les informations utiles à l'utilisateur. Elle est aussi responsable de récolter les saisies de l'utilisateur, comme par exemple une date, en faisant respecter le bon format.

### Manager

La classe Manager est responsable d'exécuter le bon service en fonction du choix de l'utilisateur. C'est ici qu'il faut ajouter le code en cas d'ajout d'une nouvelle fonctionnalité (ajouter le choix dans la méthode executerAction et y placer les appels des fonction/méthodes à executer)

### DatabaseInteraction (+Xml/PostgresInteration)

DatabaseInteraction est une interface qui vise à faire respecter un standard à toutes les classes l'implémentant (Xml/PostgresInteraction) afin de simplifier au maximum le code. En effet, au choix du mode de persistence, il n'y a pas besoin de gérer les différents modes de persistance, les classes héritant de cette interface ayant les mêmes méthodes, on est sûrs d'appeler la bonne fonction quelque soit le mode de persistance.

### RdvManager

Enfin, la classe RdvManager contient toutes les méthodes nécéssaires à l'execution des différents services proposés par l'application, par exemple la classe dispose d'une méthode gérant l'obtension de toutes les disponibilités pour une date donnée par l'utilisateur. Cette classe utilise un mode de persistance de données fourni à la création de l'objet.