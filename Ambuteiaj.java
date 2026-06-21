public class Ambuteiaj {
    private int costSuplimentar;
    private String tip;

    /***
     * Constructor care initializeaza un ambuteiaj de tipul dat
     * si care are costul dat
     * @param costSuplimentar costul generat de ambuteiaj
     * @param tip tipul blocajului
     */
    public Ambuteiaj(int costSuplimentar, String tip) {
        this.tip = tip;
        this.costSuplimentar = costSuplimentar;
    }

    /***
     * Getter-ul este utilizat pentru obtinerea costului suplimentar.
     * @return o variabila de tip int care reprezinta costul
     * ambuteiajului
     */
    public int getCostSuplimentar() {
        return costSuplimentar;
    }

    /***
     * Metoda adauga ambuteiajul pe o anumita strada.
     * @param street strada unde se produce ambuteiajul
     */
    public void adaugaLaStrada(Strada street){
        street.getAmbuteiaje().add(this);
    }
}
