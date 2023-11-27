package mockesimerkki;

public interface IHinnoittelija {
    public float getAlennusProsentti(Asiakas asiakas, Tuote tuote);
    public void setAlennusProsentti(Asiakas asiakas, float prosentti);
    public void aloita();
    public void lopeta();
}
