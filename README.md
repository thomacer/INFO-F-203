Idée pour le fonctionnement de l'algorithme:

* Génerer par backtracking l'ensemble des configurations
    de plateau possible pour notre environnement donné
    ( tableau 5x5 par exemple ).

* En même temps que l'on fait cette génération exhaustive, 
    créer un graph, où chaques arrêtes représente un mouvement
    pour une voiture ( exemple: voiture 1 avance ).
    ---> Trouver un moyen de pouvoir retrouver les configurations
         créé auparavant.
    + Marquer toutes les configurations qui sont gagnantes
      ( avoir la voiture GOAL sans rien devant ).

* Sur ce graph que l'on vient de créer, exécuter l'algorithme de
    Dijkstra pour trouver le chemins le plus court vers une
    configuration gagnante.

L'algorithme devrait normalement s'executer en un O(2^n).
