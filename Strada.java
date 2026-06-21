import java.util.*;

public class Strada{
    private int cost, limitaGabarit;
    private List<Ambuteiaj> ambuteiaje;

    public Strada() {
    }

    /***
     * Initializeaza o strada cu limita de gabarit si costul date.
     * @param cost costul strazii
     * @param limitaGabarit limita de gabarit pe strada
     */
    public Strada(int cost, int limitaGabarit) {
        this.cost = cost;
        this.limitaGabarit = limitaGabarit;
        this.ambuteiaje = null;
    }

    /***
     * Getter ce intoarce costul strazii.
     * @return un intreg ce reprezinta costul strazii
     */
    public int getCost() {
        return cost;
    }

    /***
     * Getter-ul intoarce restrictia de gabarit a strazii.
     * @return un intreg reprezentand restrictia de gabarit
     */
    public int getLimitaGabarit() {
        return limitaGabarit;
    }

    /***
     * Metoda schimba costul strazii
     * @param cost noul cost pentru strada
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /***
     * Getter-ul intoarce ambuteiajele create pe strada
     * @return lista de ambuteiaje de pe strada
     */
    public List<Ambuteiaj> getAmbuteiaje() {
        return ambuteiaje;
    }

    /***
     * Metoda initializeaza lista de ambuteiaje a strazii.
     */
    public void initializareAmbuteiaje() {
        this.ambuteiaje = new ArrayList<Ambuteiaj>();
    }

    /***
     * Functie utilizata pentru a calcula costul pe care il va avea
     * vehiculul daca traverseaza strada curenta.
     * @param v un vehicul care poate circula pe strada curenta
     * @return costul pe care il are vehiculul daca circula pe aceasta strada
     */
    public int costTotal(Vehicul v){
        int total = 0;
        /*
        Daca exista ambuteiaje pe strada, se aduna si costul lor la costul
        traversarii strazii
        */
        if (this.ambuteiaje != null){
            for (Ambuteiaj elem:this.ambuteiaje){
                total += elem.getCostSuplimentar();
            }
        }
        //Se adauga, de asemenea, costul normal al trecerii pe strada
        total += v.getCost() * this.getCost();
        return total;
    }
}
