public class NodCoada {
    private int valoare, cost;

    /***
     * Constructorul initializeaza un NodCoada utilizat pentru coada
     * de prioritati
     * @param valoare reprezinta nodul destinatie
     * @param cost costul strazii
     */
    public NodCoada(int valoare, int cost) {
        this.valoare = valoare;
        this.cost = cost;
    }

    /***
     * Getter-ul intoarce nodul destinatie curent.
     * @return un intreg reprezentand nodul destinatie
     */
    public int getValoare() {
        return valoare;
    }

    /***
     * Getter-ul intoarce costul pana la destinatie.
     * @return un intreg reprezentand costul pana la nodul destinatie
     */
    public int getCost() {
        return cost;
    }

    /***
     * Metoda schimba valoarea costului cu suma celor doi parametri.
     * @param cost1 primul cost
     * @param cost2 al doilea cost
     */
    public void seteazaCost(int cost1, int cost2) {
        this.cost = cost1 + cost2;
    }
}
