public class Vehicul {
    private int gabarit, cost;
    private String tip;

    public Vehicul() {
    }

    /***
     * Constructorul creeaza un Vehicul cu proprietatile date
     * @param gabarit gabaritul vehiculului
     * @param cost costul vehiculului
     * @param tip tipul vehiculului
     */
    public Vehicul(int gabarit, int cost, String tip) {
        this.gabarit = gabarit;
        this.cost = cost;
        this.tip = tip;
    }

    /***
     * Getter-ul returneaza gabaritul vehiculului
     * @return un intreg reprezentand gabaritul vehiculului
     */
    public int getGabarit() {
        return gabarit;
    }

    /***
     * Getter-ul returneaza costul vehiculului
     * @return un intreg reprezentand costul vehiculului
     */
    public int getCost() {
        return cost;
    }

    /***
     * Metoda determina daca vehiculul curent poate trece pe strada s
     * @param s o strada oarecare
     * @return 0 daca se poate circula pe strada, -1 altfel
     */
    public int poateCircula(Strada s) {
        if (this.getGabarit() <= s.getLimitaGabarit()) {
            return 0;
        }
        return -1;
    }

    //Metoda initializeaza vectorul de distante si pe cel ce retine nodul anterior unuia.
    private static void initializareVectori(NodCoada[] distante, Integer[] anterioare,
                                           int nrNoduri, int start){
        for (int i = 0; i < nrNoduri; i++){
            /*
            Valoarea maxima a intregului este echivalent cu infinitul din algoritmul
            dijkstra
            */
            distante[i] = new NodCoada(i, Integer.MAX_VALUE);
            anterioare[i] = -1;
        }
        //Distanta de la start la el insusi e 0
        distante[start].seteazaCost(0, 0);
    }

    /*
    Metoda se apeleaza recursiv pe un vector ce retine nodul anterior celuilalt.
    Se porneste de la nodul final
    */
    private String obtineTraseu(Integer[] anterioare, int end){
        String outputPartial = " P" + end;
        if (anterioare[end] == -1){
            return "P" + end;
        }
        return obtineTraseu(anterioare, anterioare[end]) + outputPartial;
    }

    /*
    Metoda intoarce traseul prin care trece vehiculul de la start la end
    si costul drumului.
    */
    private String traseu(NodCoada[] distante, Integer[] anterioare,
                                 int start, int end){
        String traseu;
        traseu = obtineTraseu(anterioare, end);
        /*
        Daca pana la destinatie este un cost diferit de infinit, se adauga
        costul la finalul traseului
        */
        if (distante[end].getCost() != Integer.MAX_VALUE){
            traseu += " " + distante[end].getCost() + "\n";
        }
        //Daca este un cost infinit, se reformateaza traseul
        else{
            traseu = "P" + start + " P" + end + " null\n";
        }
        return traseu;
    }

    /***
     * Metoda implementeaza algoritmul lui dijkstra utilizand o coada de
     * prioritati. Se calculeaza costurile minime de la nodul start catre
     * toate celalte noduri, insa se va returna doar traseul catre end si
     * costul acestuia.
     * @param harta harta strazilor disponibile
     * @param start nodul de unde incepe traseul
     * @param end nodul unde se termina traseul
     * @param nrNoduri numarul de noduri de pe harta
     * @return un string reprezentand traseul care are costul cel mai scurt
     */
    public String dijkstra(Harta harta, int start, int end, int nrNoduri){
        int valMinima, distantaLaMinim;
        NodCoada[] distante = new NodCoada[nrNoduri];
        Integer[] anterioare = new Integer[nrNoduri];
        //Se initializeaza vectorii de distante si cel de noduri anterioare
        initializareVectori(distante, anterioare, nrNoduri, start);
        CoadaPrioritate q = new CoadaPrioritate();
        //La inceput se adauga in coada nodul pentru start
        q.adaugareInCoada(distante[start]);
        //Cat timp coada nu este goala, se actualizeaza vectorii
        while (!q.esteGoala()){
            //Se elimina un element din coada
            NodCoada minim = q.scoateDinCoada();
            //Se obtine nodul elementului minim si costul catre acel nod
            valMinima = minim.getValoare();
            distantaLaMinim = distante[valMinima].getCost();
            for (int j = 0; j < nrNoduri; j++){
                Strada stradaCurenta = harta.getNod(valMinima, j);
                /*
                Se actualizeaza distantele si nodurile anterioare daca strada curenta
                exista, daca distanta catre nodul extras este diferita de infinit, daca
                vehiculul poate circula pe strada si daca suma dintre distanta catre nod si
                costul total al strazii este mai mica decat distanta catre nodul curent
                */
                if (stradaCurenta != null && distantaLaMinim != Integer.MAX_VALUE &&
                    this.poateCircula(stradaCurenta) == 0 &&
                        distantaLaMinim + stradaCurenta.costTotal(this) < distante[j].getCost()){
                    distante[j].seteazaCost(distantaLaMinim, stradaCurenta.costTotal(this));
                    //Se adauga in coada nodul curent si se actualizeaza anteriorul acestuia
                    q.adaugareInCoada(distante[j]);
                    anterioare[j] = valMinima;
                }
            }
        }
        //Se returneaza traseul dintre nodurile de start si de final
        return traseu(distante, anterioare, start, end);
    }
}
