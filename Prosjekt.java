package StrikkeSkap;

public class Prosjekt {

    private String navn;
    private String kategori;
    private int antallNoster;
    private int garnLengde;
    private boolean ferdigStilt;
    private static String[] kategorier = { "genser", "jakke", "hodeplagg", "kjole",
            "sokker/tøfler", "underdel", "topp", "tilbehør" };

    public Prosjekt(String navn, String kategori, int antallNoster, int garnLengde, Boolean ferdigStilt) {
        this.navn = navn;
        this.kategori = kategori;
        this.antallNoster = antallNoster;
        this.garnLengde = garnLengde;
        this.ferdigStilt = ferdigStilt;
        isValidGarn();
        isValidKategori();
        isValidFerdigStilt();

    }

    private void isValidKategori() {
        // sjekk gyldig kategori. Må være blant de i listen.
        boolean gyldigKatergori = false;
        for (String element : kategorier) {
            if (element.equals(this.kategori)) {
                gyldigKatergori = true;
            }
        }
        if (gyldigKatergori == false) {
            throw new IllegalArgumentException(
                    "Ugyldig kategori. Må være en av følgende kategorier: genser, jakke, hodeplagg, kjole/skjørt, sokker/tøfler, bukse/shorts, topp/t-skjorte, hansker/votter, tilbehør.");
        }
    }

    private void isValidGarn() {
        // garnlengde og antall nøster må være større enn 0.
        if (antallNoster <= 0) {
            throw new IllegalArgumentException("Antall nøster må være større enn 0.");
        }
        if (garnLengde <= 0) {
            throw new IllegalArgumentException("Lengde på garnet må være større enn 0.");
        }
    }

    private void isValidFerdigStilt() {
        if (ferdigStilt != false && ferdigStilt != true) {
            throw new IllegalArgumentException("Må være enten ferdigstilt (true) eller ikke ferdigstilt (false)");
        }
    }

    public static String[] getKategorier() {
        return kategorier;
    }

    public String getNavn() {
        return navn;
    }

    public String getKategori() {
        return kategori;
    }

    public int getAntallNoster() {
        return antallNoster;
    }

    public int getGarnLengde() {
        return garnLengde;
    }

    public boolean getFerdigStilt() {
        return ferdigStilt;
    }

    public void setFerdigStilt(boolean ferdigStilt) {
        this.ferdigStilt = ferdigStilt;
    }

    public static void main(String[] args) {
        Prosjekt p = new Prosjekt("The Boxy Jacket", "jakke", 4, 100, true);
        int f = p.getGarnLengde();
        System.out.println(f);

    }

}
