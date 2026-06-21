import java.util.*;

public class CoadaPrioritate implements Coada{
    //Coada de prioritate pleaca de la coada definita deja in Java
    private Queue<NodCoada> coada;

    //Constructor ce initializeaza coada ca LinkedList
    public CoadaPrioritate() {
        this.coada = new LinkedList<>();
    }

    /*
    Metoda utilizata pentru a adauga, in ordinea in care le primeste,
    nodurile n1 si n2
    */
    private static void adaugareOrdonata(NodCoada n1, NodCoada n2, Queue<NodCoada> q){
        q.add(n1);
        q.add(n2);
    }

    //Metoda utilizata pentru a adauga in coada auxiliara q nodCurrent
    private void adaugareInCoadaNenula(Queue<NodCoada> q, NodCoada nodCurent){
        //Cat timp coada de prioritate nu este nula, se pot extrage elemente
        while (!this.coada.isEmpty()){
            NodCoada nod2 = this.coada.remove();
            /*
            Daca valoarea costului celui de-al doilea nod e mai mare decat
            cea a nodului curent, se adauga nodul curent si apoi cel extras
            */
            if (nod2.getCost() > nodCurent.getCost()){
                adaugareOrdonata(nodCurent, nod2, q);
                break;
            }
            else {
                /*
                Altfel, daca este nula coada de prioritate, se adauga in q
                nodul curent
                */
                if (this.coada.isEmpty()) {
                    adaugareOrdonata(nod2, nodCurent, q);
                }
                else{
                    /*
                    Daca exista elemente in coada, se verifica nodul din
                    capatul cozii
                    */
                    if (this.coada.peek().getCost() > nodCurent.getCost()){
                        adaugareOrdonata(nod2, nodCurent, q);
                        break;
                    }
                    else{
                        q.add(nod2);
                    }
                }
            }
        }
    }

    /***
     * Metoda adauga in coada elementul nod in functie de prioritate.
     * @param nod reprezinta un NodCoada ce trebuie adaugat in coada
     */
    public void adaugareInCoada(NodCoada nod){
        Queue<NodCoada> aux = new LinkedList<>();
        //Daca nu exista elemente in coada, se adauga elementul dat ca paramentru
        if (this.coada.isEmpty()){
            this.coada.add(nod);
        }
        else{
            //Altfel, se adauga nod in coada auxiliara
            this.adaugareInCoadaNenula(aux, nod);
            //Se goleste coada de prioritate
            while (!this.coada.isEmpty()){
                aux.add(this.coada.remove());
            }
            //Se adauga elementele din coada auxiliara in coada prioritara
            while (!aux.isEmpty()){
                this.coada.add(aux.remove());
            }
        }
    }

    /***
     * Metoda utilizata pentru eliminarea unui element din coada.
     * @return o variabila de tip NodCoada eliminata din coada prioritara
     */
    public NodCoada scoateDinCoada(){
        return this.coada.remove();
    }

    /***
     * Metoda care verifica daca este goala coada de prioritate.
     * @return true daca este coada goala,false daca exista elemente in ea
     */
    public boolean esteGoala(){
        return this.coada.isEmpty();
    }
}
