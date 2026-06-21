import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {
    //Metoda care returneaza un int ce reprezinta un nod
    private static int prelucrareSir(String sir){
        //Se creeaza un StringBuilder pentru a sterge prima litera din sir
        StringBuilder build = new StringBuilder(sir);
        build.deleteCharAt(0);
        //Se intoarce conversia sirului la int
        return Integer.parseInt(build.toString());
    }

    /***
     * Metoda genereaza o harta si adauga strazile in aceasta, citindu-le
     * din fisier
     * @param input variabila pentru citirea din fisier
     * @return harta cu strazi
     */
    public static Harta creeareHarta(Scanner input){
        int nrStrazi, nrNoduri;
        String linie;
        //Se extrag numarul de strazi si de noduri
        nrStrazi = input.nextInt();
        nrNoduri = input.nextInt();
        Harta harta = new Harta(nrNoduri);
        //Se ignora \n
        input.nextLine();
        //Se iau pe rand strazile si se adauga pe harta
        for (int i = 0; i < nrStrazi; i++){
            linie = input.nextLine();
            prelucrare(linie, harta);
        }
        return harta;
    }

    /***
     * Metoda intoarce pe baza tipului un anumit tip de vehicul.
     * @param tip un sir cu un singur caracter reprezentand tipul
     *            de vehicul care va circula
     * @return un vehicul (Bicicleta/Motocicleta/Autoturism/Camion)
     */
    public static Vehicul creare(String tip){
        Vehicul v = null;
        if (tip.equals("b")){
            v = new Bicicleta();
        }
        if (tip.equals("m")){
            v = new Motocicleta();
        }
        if (tip.equals("a")){
            v = new Autoturism();
        }
        if (tip.equals("c")){
            v = new Camion();
        }
        return v;
    }

    /***
     * Metoda prelucreaza sirul de caractere si adauga strada rezultata
     * pe harta.
     * @param input datele despre strada
     * @param harta graful/harta strazilor
     */
    public static void prelucrare(String input, Harta harta){
        int nodStart, nodSfarsit, cost, gabarit;
        String[] token;
        //Se "sparge" sirul dupa spatiu
        token = input.split(" ");
        /*
        Se obtin nodurile cu metoda prelucrareSir, iar costul si gabaritul
        prin conversia sirurilor
        */
        nodStart = prelucrareSir(token[0]);
        nodSfarsit = prelucrareSir(token[1]);
        cost = Integer.parseInt(token[2]);
        gabarit = Integer.parseInt(token[3]);
        //Se adauga strada pe harta
        harta.addStreet(nodStart, nodSfarsit, cost, gabarit);
    }

    /***
     * Metoda creeaza, in functie de input, un ambuteiaj pentru o strada
     * sau simuleaza conducerea unui vehicul pe un drum si scrie in fisier
     * drumul si costul acestuia.
     * @param input o variabila de tip Scanner pentru citirea din fisier
     * @param harta harta de strazi
     * @param out variabila FileWriter utilizata la scrierea in fisier
     */
    public static void prelucrare(Scanner input, Harta harta, FileWriter out){
        String[] token;
        String line;
        line = input.nextLine();
        token = line.split(" ");
        if ((token[0].equals("accident")) || (token[0].equals("trafic")) ||
                (token[0].equals("blocaj"))){
            /*
            Daca este vorba de un ambuteiaj, se extrag din input punctul de plecare,
            cel de final si costul suplimentar
            */
            int start, sfarsit, costSuplimentar;
            start = prelucrareSir(token[1]);
            sfarsit = prelucrareSir(token[2]);
            costSuplimentar = Integer.parseInt(token[3]);
            //Se adauga restrictia pe strada corespunzatoare
            harta.addRestriction(token[0], start, sfarsit, costSuplimentar);
        }
        if ((token[0].equals("drive"))){
            Vehicul v = creare(token[1]);
            //Se extrag capetele drumului
            int start, sfarsit;
            start = prelucrareSir(token[2]);
            sfarsit = prelucrareSir(token[3]);
            try{
                //Se va scrie in fisier rezultatul metodei drive asociate grafului
                out.write(harta.drive(v, start, sfarsit));
            }
            //Daca se intalneste IOException, se afiseaza mesajul corespunzator
            catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Harta harta = null;
        //Se creeaza variabile pentru fisierele de intrare si de iesire
        File in = new File("map.in"), iesire = new File("map.out");
        Scanner input;
        FileWriter out;
        try {
             //Se incearca initializarea variabilei de citire
             input = new Scanner(in);
        }
        catch (FileNotFoundException e){
            //Daca se intalneste FileNotFoundException, se afiseaza mesajul corespunzator
            System.out.println(e.getMessage());
            return;
        }
        try{
            out = new FileWriter(iesire);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            return;
        }
        //Se genereaza harta
        harta = creeareHarta(input);
        //Dupa creeare, se executa fiecare comanda din fisierul de intrare
        while (input.hasNextLine()){
            prelucrare(input, harta, out);
        }
        try {
            out.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
