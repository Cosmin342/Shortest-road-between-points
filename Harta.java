public class Harta{
    private Strada[][] map;
    private int dim;

    /***
     * Constructorul genereaza o harta de strazi.
     * @param dimensiune dimensiunea hartii
     */
    public Harta(int dimensiune) {
        this.map = new Strada[dimensiune][dimensiune];
        int i, j;
        for (i = 0; i < dimensiune; i++){
            for (j = 0; j < dimensiune; j++){
                map[i][j] = null;
            }
        }
        this.dim = dimensiune;
    }

    /***
     * Getter-ul returneaza strada care leaga nodurile start si end.
     * @param start nodul de inceput
     * @param end nodul de sfarsit
     * @return intoarce strada ce leaga nodurile
     */
    public Strada getNod(int start, int end) {
        return map[start][end];
    }

    /***
     * Metoda este utilizata pentru a adauga o noua strada in harta(graf).
     * @param start nodul de unde incepe strada
     * @param end nodul unde strada se termina
     * @param cost costul strazii
     * @param gabarit limita de gabarit impusa pe strada
     */
    public void addStreet(int start, int end, int cost, int gabarit) {
        this.map[start][end] = new Strada(cost, gabarit);
    }

    /***
     * Metoda addRestriction adauga la strada ce leaga nodurile start si
     * end un anumit tip de ambuteiaj cu un cost aferent.
     * @param type tipul de ambuteiaj
     * @param start locul unde incepe strada
     * @param end locul unde se termina strada
     * @param cost costul suplimentar generat de ambuteiaj
     */
    public void addRestriction(String type, int start, int end, int cost){
        //Se creeaza un nou ambuteiaj
        Ambuteiaj ambuteiaj = new Ambuteiaj(cost, type);
        //Se initializeaza lista de ambuteiaje pe strada respectiva, daca nu este deja
        if (this.getNod(start, end).getAmbuteiaje() == null) {
            this.getNod(start, end).initializareAmbuteiaje();
        }
        //Se adauga ambuteiajul in lista
        ambuteiaj.adaugaLaStrada(this.getNod(start, end));
    }

    /***
     * Metoda drive este utilizata pentru a arata traseul care are costul
     * cel mai mic dintre doua puncte pe harta utilizand metoda dijkstra
     * definita pentru clasa Vehicul.
     * @param v vehiculul ce va traversa drumul
     * @param start locul de unde incepe traseul
     * @param end locul unde se termina traseul
     * @return un sir ce contine punctele prin care va trece vehiculul
     * si costul aferent traseului (sau null daca nu exist drum intre
     * punctele respective)
     */
    public String drive(Vehicul v, int start, int end){
        return v.dijkstra(this, start, end, this.dim);
    }
}
