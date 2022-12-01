// 
// Decompiled by Procyon v0.5.36
// 

public class SimplesTableau
{
    void principal() {
        this.testVerifTab();
        this.testAfficherTab();
        this.testSaisir();
        this.testAfficherTabLgn();
        this.testTirerAleatoire();
        this.testRemplirAleatoire();
        this.testEgalite();
        this.testCopier();
        this.testNbOccurrences();
        this.testLeMinEtLeMax();
        this.testInverse();
        this.testEchange();
        this.testMelange();
        this.testDecalerGche();
        this.testSupprimerUneValeur();
        this.testInclusion();
        this.testRemplirToutesDiff();
    }
    
    void testVerifTab() {
        System.out.println("\n=== Test m\u00e9thode verifTab ===");
        System.out.println("=== Cas normal : le tableau n'est pas cr\u00e9\u00e9 ===");
        if (!this.verifTab(null, 4)) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
        System.out.println("=== Cas normal : le nombre d'\u00e9l\u00e9ments d\u00e9passe la taille ===");
        if (!this.verifTab(new int[5], 6)) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
        System.out.println("=== Cas normal : le nombre d'\u00e9l\u00e9ments est n\u00e9gatif ===");
        if (!this.verifTab(new int[5], -1)) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
        System.out.println("=== Cas limite : le nombre d'\u00e9l\u00e9ments est \u00e9gale \u00e0 z\u00e9ro ===");
        if (!this.verifTab(new int[5], 0)) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
    }
    
    void testAfficherTab() {
        System.out.println("\n=== Test m\u00e9thode afficherTab ===");
        System.out.println("=== Cas normal1 : que des z\u00e9ros ===");
        final int n = 10;
        final int[] array = new int[n];
        System.out.println("Contenu du tableau :");
        this.afficherTab(array, n);
        System.out.println("=== Cas normal2 : le nombre d'\u00e9l\u00e9ments est dans les bornes ===");
        this.afficherTab(new int[] { 1, -56, -20, 566 }, 4);
        System.out.println("=== Cas limite : le nombre d'\u00e9l\u00e9ments est \u00e9gale \u00e0 z\u00e9ro ===");
        this.afficherTab(new int[0], 0);
        System.out.println("=== Cas d'erreur : le nombre d'\u00e9l\u00e9ments n'est pas dans les bornes ===");
        this.afficherTab(new int[] { 12, -89 }, 4);
        System.out.println("=== Cas d'erreur : tableau inexistant ===");
        this.afficherTab(null, n);
    }
    
    void testSaisir() {
        System.out.println("\n=== Test de la m\u00e9thode saisir, cas normal ===");
        final int[] array = new int[5];
        final int n = 3;
        this.saisir(array, n);
        System.out.println("Contenu du tableau :");
        this.afficherTab(array, n);
        System.out.println("\n=== Test de la m\u00e9thode saisir, cas limite : tableau compl\u00e8tement rempli ===");
        final int[] array2 = new int[5];
        final int n2 = 5;
        this.saisir(array2, n2);
        System.out.println("Contenu du tableau :");
        this.afficherTab(array2, n2);
        System.out.println("\n=== Test de la m\u00e9thode saisir, cas d'erreur : le tableau n'existe pas ===");
        final int[] array3 = null;
        final int n3 = 15;
        this.saisir(array3, n3);
        System.out.println("Contenu du tableau :");
        this.afficherTab(array3, n3);
    }
    
    void testAfficherTabLgn() {
        System.out.println("\n=== Test de la m\u00e9thode afficherTabLgn ===");
        System.out.println("=== Cas normal1 : 2 lignes + 1 ligne incompl\u00e8te ===");
        final int[] array = { 12, -89, 25, -45, 660 };
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, 5, 2);
        System.out.println("=== Cas normal2 : 2 lignes compl\u00e8tes ===");
        final int[] array2 = { 12, -89, 25, 600 };
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array2, 4, 2);
        System.out.println("=== Cas limite : 1 seule ligne ===");
        final int[] array3 = { 12, -89, 25, 600 };
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array2, 4, 4);
    }
    
    void testTirerAleatoire() {
        System.out.println("\n=== Test de la m\u00e9thode tirerAleatoire ===");
        final int tirerAleatoire = this.tirerAleatoire(20, 30);
        System.out.println("Tirage al\u00e9atoire entre 20 et 30");
        System.out.println("Tirage = " + tirerAleatoire);
        if (tirerAleatoire < 20 || tirerAleatoire > 30) {
            System.err.println("Echec du test");
        }
        else {
            System.out.println("Test r\u00e9ussi");
        }
    }
    
    void testRemplirAleatoire() {
        System.out.println("\n=== Test de la m\u00e9thode remplirAleat ===");
        final int[] array = new int[15];
        final int n = 5;
        this.remplirAleatoire(array, n, 20, 25);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 3);
        System.err.println("Cas d'erreur : tableau inexistant");
        this.remplirAleatoire(null, n, 20, 25);
        System.err.println("Cas d'erreur : nombre d'\u00e9l\u00e9ments incorrect");
        this.remplirAleatoire(new int[15], 25, 20, 25);
        System.err.println("Cas d'erreur : min n'est pas inf\u00e9rieur ou \u00e9gale \u00e0 max");
        this.remplirAleatoire(new int[15], 10, 25, 20);
    }
    
    void testEgalite() {
        System.out.println("\n=== Test de la m\u00e9thode egalite ===");
        System.out.println("\nCas o\u00f9 l'\u00e9galit\u00e9 est vraie");
        final int n = 5;
        final int n2 = 5;
        final int[] array = new int[n];
        final int[] array2 = new int[n2];
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 3);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array2, n2, 3);
        System.out.println("Egalit\u00e9 des tableaux : " + this.egalite(array, array2, n, n2));
        System.out.println("\nCas o\u00f9 l'\u00e9galit\u00e9 est fausse : nombre d'\u00e9l\u00e9ments diff\u00e9rent");
        final int[] array3 = new int[n];
        final int[] array4 = new int[n2];
        final int n3 = 3;
        final int n4 = 4;
        this.remplirAleatoire(array3, n3, 20, 20);
        this.remplirAleatoire(array4, n4, 20, 20);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array3, n3, 3);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array4, n4, 3);
        System.out.println("Egalit\u00e9 des tableaux : " + this.egalite(array3, array4, n3, n4));
        System.out.println("\nCas o\u00f9 l'\u00e9galit\u00e9 est fausse : au moins une case diff\u00e9rente");
        final int[] array5 = new int[n];
        final int[] array6 = new int[n2];
        final int n5 = 4;
        final int n6 = 4;
        this.remplirAleatoire(array5, n5, 23, 50);
        this.remplirAleatoire(array6, n6, 51, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array5, n5, 3);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array6, n6, 3);
        System.out.println("Egalit\u00e9 des tableaux : " + this.egalite(array5, array6, n5, n6));
        System.err.println("\nCas d'erreur : tableau inexistant");
        this.egalite(null, new int[n2], 4, 4);
    }
    
    void testCopier() {
        System.out.println("\n=== Test de la m\u00e9thode copier ===");
        final int[] array = new int[25];
        final int n = 12;
        this.remplirAleatoire(array, n, 50, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 4);
        if (this.egalite(array, this.copier(array, n), n, n)) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
    }
    
    void testNbOccurrences() {
        System.out.println("\n=== Test de la m\u00e9thode nbOccurrences ===");
        final int[] array = new int[25];
        final int n = 12;
        this.remplirAleatoire(array, n, 50, 50);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 4);
        System.out.println("\nCas o\u00f9 le nombre d'occurrences est maximum");
        if (this.nbOccurrences(array, n, 50) == n) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
        System.out.println("\nCas o\u00f9 le nombre d'occurrences est z\u00e9ro");
        if (this.nbOccurrences(array, n, 40) == 0) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
    }
    
    void testLeMinEtLeMax() {
        System.out.println("\n=== Test des m\u00e9thodes leMin et leMax ===");
        final int[] array = new int[25];
        final int n = 12;
        this.remplirAleatoire(array, n, 40, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 4);
        final int leMin = this.leMin(array, n);
        final int leMax = this.leMax(array, n);
        System.out.println("Le minimum du tableau est : " + leMin);
        System.out.println("Le maximum du tableau est : " + leMax);
        System.out.println("\nCas o\u00f9 le tableau est limit\u00e9 \u00e0 un seul entier");
        final int[] array2 = new int[25];
        final int n2 = 1;
        this.remplirAleatoire(array2, n2, 40, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array2, n2, 4);
        if (this.leMin(array2, n2) == this.leMax(array2, n2)) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
    }
    
    void testInverse() {
        System.out.println("\n=== Test de la m\u00e9thode inverse ===");
        final int[] array = new int[25];
        final int n = 12;
        this.remplirAleatoire(array, n, 40, 60);
        System.out.println("Contenu du tableau tab1 :");
        this.afficherTabLgn(array, n, 4);
        final int[] inverse = this.inverse(array, n);
        System.out.println("Contenu du tableau tab2 inverse de tab1 :");
        this.afficherTabLgn(inverse, n, 4);
        boolean b = true;
        for (int i = 0; i < n; ++i) {
            if (array[i] != inverse[n - 1 - i]) {
                b = false;
                break;
            }
        }
        if (b) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
    }
    
    void testEchange() {
        System.out.println("\n=== Test de la m\u00e9thode echange ===");
        final int[] array = new int[25];
        final int n = 12;
        this.remplirAleatoire(array, n, 30, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 4);
        System.out.println("Echange du contenu des indices 0 et " + (n - 1));
        final int n2 = array[0];
        final int n3 = array[n - 1];
        this.echange(array, n, 0, n - 1);
        if (n2 == array[n - 1] && n3 == array[0]) {
            System.out.println("Test r\u00e9ussi");
        }
        else {
            System.err.println("Echec du test");
        }
        System.out.println("\nCas o\u00f9 un ou +sieurs indices sortent du tableau");
        this.echange(array, n, -1, n);
    }
    
    void testMelange() {
        System.out.println("\n=== Test de la m\u00e9thode melange ===");
        final int[] array = new int[25];
        final int n = 3;
        this.remplirAleatoire(array, n, 30, 60);
        System.out.println("Contenu du tableau initial :");
        this.afficherTabLgn(array, n, 4);
        final int[] melange = this.melange(array, n);
        System.out.println("Contenu du tableau m\u00e9lang\u00e9 :");
        this.afficherTabLgn(melange, n, 4);
    }
    
    void testDecalerGche() {
        System.out.println("\n=== Test de la m\u00e9thode decalerGche ===");
        final int[] array = new int[25];
        final int n = 12;
        this.remplirAleatoire(array, n, 30, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 12);
        this.decalerGche(array, n, 8);
        System.out.println("Contenu du tableau apr\u00e8s d\u00e9calage en case 8 :");
        this.afficherTabLgn(array, n, 12);
        System.out.println("\nCas o\u00f9 l'indice de d\u00e9but du d\u00e9calage est impossible : indice 13");
        this.decalerGche(array, n, 13);
    }
    
    void testSupprimerUneValeur() {
        System.out.println("\n=== Test de la m\u00e9thode supprimerUneValeur ===");
        final int[] array = new int[25];
        final int n = 12;
        this.remplirAleatoire(array, n, 30, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array, n, 4);
        final int supprimerUneValeur = this.supprimerUneValeur(array, n, SimpleInput.getInt("Entrer une valeur \u00e0 supprimer"));
        System.out.println("Contenu du tableau apr\u00e8s suppression :");
        this.afficherTabLgn(array, supprimerUneValeur, 4);
        System.out.println("\nCas de la suppression totale du tableau valeur par valeur");
        final int[] array2 = new int[25];
        int i = 4;
        this.remplirAleatoire(array2, i, 30, 60);
        System.out.println("Contenu du tableau :");
        this.afficherTabLgn(array2, i, 4);
        while (i > 0) {
            i = this.supprimerUneValeur(array2, i, SimpleInput.getInt("Entrer une valeur \u00e0 supprimer"));
            System.out.println("Contenu du tableau apr\u00e8s suppression :");
            this.afficherTabLgn(array2, i, 4);
        }
        System.out.println("Tableau compl\u00e8tement vid\u00e9");
    }
    
    void testInclusion() {
        final int n = 20;
        final int[] array = new int[n];
        final int[] array2 = new int[n];
        System.out.println("\n=== Test de la m\u00e9thode inclusion ===");
        System.out.println("\nCas simple d'in\u00e9galit\u00e9 des tailles des tableaux de d\u00e9part");
        System.out.println("\nCas d'une inclusion de tab1 dans tab2");
        final int n2 = 5;
        final int n3 = 8;
        for (int i = 0; i < n2; ++i) {
            array[i] = i;
        }
        array[n2 - 1] = 0;
        System.out.println("\nTab1 :");
        this.afficherTab(array, n2);
        for (int j = 0; j < n3; ++j) {
            array2[j] = j;
        }
        array2[n3 - 1] = 0;
        System.out.println("\nTab2 :");
        this.afficherTab(array2, n3);
        System.out.println("\nInclusion ? " + this.inclusion(array, array2, n2, n3));
        System.out.println("\nCas de NON inclusion de tab1 dans tab2");
        System.out.println("\nTab1 :");
        this.afficherTab(array2, n3);
        System.out.println("\nTab2 :");
        this.afficherTab(array, n2);
        System.out.println("\nInclusion ? " + this.inclusion(array2, array, n3, n2));
    }
    
    void testRemplirToutesDiff() {
        final int[] array = new int[5];
        System.out.println("\n=== Test de la m\u00e9thode remplirToutesDiff ===");
        System.out.println("\n=== Cas normal1 : saisir un tableau avec quelques doublons ===");
        this.remplirToutesDiff(array, 5);
        this.afficherTab(array, 5);
        System.out.println("\n=== Cas normal2 : saisir un tableau aucun doublon ===");
        this.remplirToutesDiff(array, 5);
        this.afficherTab(array, 5);
        System.out.println("\n=== Cas limite : saisir un tableau avec 1 \u00e9l\u00e9ment ===");
        this.remplirToutesDiff(array, 1);
        this.afficherTab(array, 1);
    }
    
    void afficherTab(final int[] array, final int n) {
        if (!this.verifTab(array, n)) {
            System.err.println("afficherTab : Probl\u00e8me de tableau");
        }
        else {
            System.out.println();
            for (int i = 0; i < n; ++i) {
                System.out.println("tab[" + i + "] = " + array[i]);
            }
            System.out.println();
        }
    }
    
    void afficherTabLgn(final int[] array, final int n, final int n2) {
        if (!this.verifTab(array, n)) {
            System.err.println("afficherTabLgn : Probl\u00e8me de tableau");
        }
        else {
            for (int i = 1; i <= n; ++i) {
                if (i % n2 == 0) {
                    System.out.println(array[i - 1]);
                }
                else {
                    System.out.print(array[i - 1] + "\t");
                }
            }
            System.out.print("\n");
        }
    }
    
    void saisir(final int[] array, final int n) {
        int i = 0;
        if (!this.verifTab(array, n)) {
            System.err.println("saisir : Probl\u00e8me de tableau");
        }
        else {
            while (i < n) {
                array[i] = SimpleInput.getInt("Saisir un entier : ");
                ++i;
            }
        }
    }
    
    boolean egalite(final int[] array, final int[] array2, final int n, final int n2) {
        boolean b = true;
        if (!this.verifTab(array, n) || !this.verifTab(array2, n2)) {
            System.err.println("egalite : Probl\u00e8me de tableau tab1 ou tab2");
        }
        else if (n2 == n) {
            for (int i = 0; i < n; ++i) {
                if (array[i] != array2[i]) {
                    b = false;
                    break;
                }
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    void remplirAleatoire(final int[] array, final int n, final int i, final int j) {
        if (!this.verifTab(array, n)) {
            System.err.println("remplirAleatoire : Probl\u00e8me de tableau");
        }
        else if (i > j) {
            System.err.println("remplirAleatoire : Erreur : " + i + " n'est pas <= \u00e0 " + j);
        }
        else {
            for (int k = 0; k < n; ++k) {
                array[k] = this.tirerAleatoire(i, j);
            }
        }
    }
    
    int tirerAleatoire(final int n, final int n2) {
        int n3 = -1;
        if (n <= n2) {
            n3 = (int)(Math.random() * (n2 - n + 1) + n);
        }
        else {
            System.err.println("tirerAleatoire : erreur : min doit \u00eatre inf\u00e9rieur \u00e0 max");
        }
        return n3;
    }
    
    int[] copier(final int[] array, final int n) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < n; ++i) {
            array2[i] = array[i];
        }
        return array2;
    }
    
    int nbOccurrences(final int[] array, final int n, final int n2) {
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            if (array[i] == n2) {
                ++n3;
            }
        }
        return n3;
    }
    
    int leMin(final int[] array, final int n) {
        int n2 = array[0];
        for (int i = 1; i < n; ++i) {
            if (array[i] < n2) {
                n2 = array[i];
            }
        }
        return n2;
    }
    
    int leMax(final int[] array, final int n) {
        int n2 = array[0];
        for (int i = 1; i < n; ++i) {
            if (array[i] > n2) {
                n2 = array[i];
            }
        }
        return n2;
    }
    
    int[] inverse(final int[] array, final int n) {
        final int[] array2 = new int[array.length];
        for (int i = 0; i < n; ++i) {
            array2[i] = array[n - 1 - i];
        }
        return array2;
    }
    
    void echange(final int[] array, final int n, final int n2, final int n3) {
        if (n2 < 0 || n2 >= n || n3 < 0 || n3 >= n) {
            System.err.println("echange : Indice(s) en dehors des bornes");
        }
        else {
            final int n4 = array[n2];
            array[n2] = array[n3];
            array[n3] = n4;
        }
    }
    
    int[] melange(final int[] array, final int n) {
        final int[] copier = this.copier(array, n);
        for (int i = 0; i < n - 1; ++i) {
            this.echange(copier, n, i, this.tirerAleatoire(i + 1, n - 1));
        }
        return copier;
    }
    
    void decalerGche(final int[] array, final int n, final int n2) {
        if (n2 < 0 || n2 >= n - 1) {
            System.err.println("decalerGche : L'indice du d\u00e9calage n'est pas valide");
        }
        else {
            for (int i = n2; i < n - 1; ++i) {
                array[i] = array[i + 1];
            }
        }
    }
    
    int supprimerUneValeur(final int[] array, int n, final int n2) {
        for (int i = 0; i < n; ++i) {
            if (array[i] == n2) {
                if (i != n - 1) {
                    this.decalerGche(array, n, i);
                }
                --n;
                break;
            }
        }
        return n;
    }
    
    boolean inclusion(final int[] array, final int[] array2, final int n, final int n2) {
        boolean b = true;
        final int[] copier = this.copier(array2, n2);
        int supprimerUneValeur = n2;
        for (int n3 = 0; n3 < n && b; ++n3) {
            final int n4 = array[n3];
            final int n5 = supprimerUneValeur;
            supprimerUneValeur = this.supprimerUneValeur(copier, supprimerUneValeur, n4);
            if (n5 == supprimerUneValeur) {
                b = false;
            }
        }
        return b;
    }
    
    void remplirToutesDiff(final int[] array, final int n) {
        if (!this.verifTab(array, n)) {
            System.err.println("remplirToutesDiff : Probl\u00e8me de tableau");
        }
        else {
            for (int i = 0; i < n; ++i) {
                final int int1 = SimpleInput.getInt("Saisir un entier : ");
                int n2 = 0;
                int n3 = 0;
                while (n2 < i && n3 == 0) {
                    if (array[n2] == int1) {
                        n3 = 1;
                    }
                    else {
                        ++n2;
                    }
                }
                if (n3 == 0) {
                    array[i] = int1;
                }
            }
        }
    }
    
    boolean verifTab(final int[] array, final int n) {
        boolean b = false;
        if (array != null) {
            if (n <= array.length && n > 0) {
                b = true;
            }
            else {
                System.err.println("verifTab : Erreur : nombre d'\u00e9l\u00e9ments incorrect");
            }
        }
        else {
            System.err.println("verifTab : Tableau inexistant");
        }
        return b;
    }
}