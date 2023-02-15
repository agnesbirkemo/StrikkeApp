package StrikkeSkap;

import java.util.ArrayList;

public class StrikkeSkap {

    // lister som sorterer prosjektene: alle prosjekter, uferdige og ferdige
    // prosjekter.
    private ArrayList<Prosjekt> prosjekter;
    private ArrayList<Prosjekt> uferdigeProsjekter;
    private ArrayList<Prosjekt> ferdigeProsjekter;

    // egne lister per kategori - tar utgangspunkt i ferdige prosjekter
    private ArrayList<Prosjekt> gensere;
    private ArrayList<Prosjekt> jakker;
    private ArrayList<Prosjekt> hodeplagg;
    private ArrayList<Prosjekt> kjoler;
    private ArrayList<Prosjekt> sokkerTofler;
    private ArrayList<Prosjekt> underdeler;
    private ArrayList<Prosjekt> topper;
    private ArrayList<Prosjekt> tilbehor;

    // konstruktøren oppretter flere tomme lister til objektet. disse fylles opp med
    // diverse metoder.
    public StrikkeSkap() {
        this.prosjekter = new ArrayList<>();
        this.uferdigeProsjekter = new ArrayList<>();
        this.ferdigeProsjekter = new ArrayList<>();

        this.gensere = new ArrayList<>();
        this.jakker = new ArrayList<>();
        this.hodeplagg = new ArrayList<>();
        this.kjoler = new ArrayList<>();
        this.sokkerTofler = new ArrayList<>();
        this.underdeler = new ArrayList<>();
        this.topper = new ArrayList<>();
        this.tilbehor = new ArrayList<>();

    }

    private void isValidName(String navn) {
        for (Prosjekt p : this.prosjekter) {
            if (p.getNavn().equals(navn))
                throw new IllegalArgumentException("Du har allerede lagt til et prosjekt med navn " + navn
                        + ". Vennligst velg et nytt navn.");
        }
    }

    // metode for å plassere et prosjekt i riktige lister
    public void addProsjekt(Prosjekt prosjekt) {
        isValidName(prosjekt.getNavn());

        this.prosjekter.add(prosjekt);
        // sjekker om prosjekt er ferdig, plasserer i passelig arraylist
        if (prosjekt.getFerdigStilt() == true) {
            ferdigeProsjekter.add(prosjekt);
        } else {
            uferdigeProsjekter.add(prosjekt);
        }

        // sjekker kategorien til prosjekt og plasserer i riktig arraylist:

        if (prosjekt.getKategori().equals("genser"))
            gensere.add(prosjekt);
        else if (prosjekt.getKategori().equals("jakke"))
            jakker.add(prosjekt);
        else if (prosjekt.getKategori().equals("hodeplagg"))
            hodeplagg.add(prosjekt);
        else if (prosjekt.getKategori().equals("kjole"))
            kjoler.add(prosjekt);
        else if (prosjekt.getKategori().equals("sokker/tøfler"))
            sokkerTofler.add(prosjekt);
        else if (prosjekt.getKategori().equals("underdel"))
            underdeler.add(prosjekt);
        else if (prosjekt.getKategori().equals("topp"))
            topper.add(prosjekt);
        else if (prosjekt.getKategori().equals("tilbehør"))
            tilbehor.add(prosjekt);
    }

    // STRIKKEBEREGNER:

    public int getListLength(ArrayList<Prosjekt> liste) {
        int count = 0;
        for (Prosjekt element : liste) {
            count += 1;
        }
        return count;
    }

    // meter strikket -- må kanskje ta utgangspunkt i de ferdige plaggene her
    public int meterStrikket() {
        int tot = 0;
        for (Prosjekt prosjekt : this.ferdigeProsjekter) {
            int antallNoster = prosjekt.getAntallNoster();
            int garnLengde = prosjekt.getGarnLengde();

            tot += (antallNoster * garnLengde);
        }
        return tot;
    }

    public int nosterBrukt() {
        int tot = 0;
        for (Prosjekt prosjekt : this.ferdigeProsjekter) {
            int antallNoster = prosjekt.getAntallNoster();
            tot += antallNoster;
        }
        return tot;
    }

    // antall plagg av ønsket kategori/liste
    // hvis visKunFerdige == false returnerer den ferdige óg uferdige prosjekter.
    // hvis visKunFerdige == true returnerer den kun ferdige prosjekter.

    public int antallAv(ArrayList<Prosjekt> liste, boolean visKunFerdige) {

        if (visKunFerdige == false) {
            try {
                return getListLength(liste);
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "Ugyldig parameter.");
            }
        }

        else if (visKunFerdige == true) {
            int count = 0;

            for (Prosjekt prosjekt : liste) {
                if (prosjekt.getFerdigStilt() == true)
                    count += 1;
            }
            return count;
        }
        return 0;
    }

    // endrer et uferdig prosjekt til et ferdig prosjekt.
    public void setFerdigProsjekt(Prosjekt prosjekt) {
        if (uferdigeProsjekter.contains(prosjekt))
            prosjekt.setFerdigStilt(true);
        this.uferdigeProsjekter.remove(prosjekt);
        this.ferdigeProsjekter.add(prosjekt);
    }

    public void removeProsjekt(Prosjekt prosjekt) {

        if (!this.prosjekter.contains(prosjekt))
            throw new NullPointerException(prosjekt.getNavn() + "finnes ikke i StrikkeSkap");

        if (prosjekter.remove(prosjekt)) {
            System.out.println(prosjekt.getNavn() + " fjernet fra StrikkeSkap");
        }

        /*
         * boolean isInStrikkeSkap = false;
         * for (Prosjekt p : this.prosjekter) {
         * if (p.getNavn().equals(prosjekt.getNavn()))
         * isInStrikkeSkap = true;
         * }
         * if (isInStrikkeSkap == false)
         * throw new
         * IllegalArgumentException("Du kan ikke fjerne et prosjekt som ikke finnes i strikkeskapet."
         * );
         * 
         * this.prosjekter.remove(prosjekt);
         */

        // this.prosjekter.forEach(removeIf(x -> x.getNavn().equals(navn)));

        if (prosjekt.getFerdigStilt() == true)
            this.ferdigeProsjekter.remove(prosjekt);
        else
            this.uferdigeProsjekter.remove(prosjekt);

        if (prosjekt.getKategori().equals("genser"))
            gensere.remove(prosjekt);
        else if (prosjekt.getKategori().equals("jakke"))
            jakker.remove(prosjekt);
        else if (prosjekt.getKategori().equals("hodeplagg"))
            hodeplagg.remove(prosjekt);
        else if (prosjekt.getKategori().equals("kjole"))
            kjoler.remove(prosjekt);
        else if (prosjekt.getKategori().equals("sokker/tøfler"))
            sokkerTofler.remove(prosjekt);
        else if (prosjekt.getKategori().equals("underdel"))
            underdeler.remove(prosjekt);
        else if (prosjekt.getKategori().equals("topp"))
            topper.remove(prosjekt);
        else if (prosjekt.getKategori().equals("tilbehør"))
            tilbehor.remove(prosjekt);

    }

    // metode for at brukeren skal se hvilke prosjekter den har strikket med ulike
    // "filtre" ut i fra listene.

    public String seProsjekter(ArrayList<Prosjekt> liste, boolean visKunFerdige) {
        String prosjekterStr = "";
        if (visKunFerdige == false) {
            for (Prosjekt prosjekt : liste) {
                String navn = prosjekt.getNavn();
                prosjekterStr += navn + ", ";
            }
        } else if (visKunFerdige == true) {
            for (Prosjekt prosjekt : liste) {
                if (prosjekt.getFerdigStilt() == true) {
                    String navn = prosjekt.getNavn();
                    prosjekterStr += navn + ", ";
                }
            }
        }

        // returnerer prosjektStr på en fin måte
        int lengde = 0;
        if (prosjekterStr != "" && prosjekterStr.length() > 0) {
            lengde = prosjekterStr.length();
            return prosjekterStr.substring(0, prosjekterStr.length() - 2);
        } else if (prosjekterStr.equals("")) {
            return prosjekterStr;
        }
        return prosjekterStr;
    }

    public ArrayList<Prosjekt> getProsjekter() {
        ArrayList<Prosjekt> kopiProsjekter = new ArrayList<>(prosjekter);
        return kopiProsjekter;
    }

    public ArrayList<Prosjekt> getUferdigeProsjekter() {
        ArrayList<Prosjekt> kopiUferdigeProsjekter = new ArrayList<>(uferdigeProsjekter);
        return kopiUferdigeProsjekter;
    }

    public ArrayList<Prosjekt> getFerdigeProsjekter() {
        ArrayList<Prosjekt> kopiFerdigeProsjekter = new ArrayList<>(ferdigeProsjekter);
        return kopiFerdigeProsjekter;
    }

    public ArrayList<Prosjekt> getGensere() {
        ArrayList<Prosjekt> kopiGensere = new ArrayList<>(gensere);
        return kopiGensere;
    }

    public ArrayList<Prosjekt> getJakker() {
        ArrayList<Prosjekt> kopiJakkker = new ArrayList<>(jakker);
        return kopiJakkker;
    }

    public ArrayList<Prosjekt> getHodeplagg() {
        ArrayList<Prosjekt> kopiHodeplagg = new ArrayList<>(hodeplagg);
        return kopiHodeplagg;
    }

    public ArrayList<Prosjekt> getKjoler() {
        ArrayList<Prosjekt> kopiKjoler = new ArrayList<>(kjoler);
        return kopiKjoler;
    }

    public ArrayList<Prosjekt> getSokkerTofler() {
        ArrayList<Prosjekt> kopiSokkerTofler = new ArrayList<>(sokkerTofler);
        return kopiSokkerTofler;
    }

    public ArrayList<Prosjekt> getUnderdeler() {
        ArrayList<Prosjekt> kopiUnderdeler = new ArrayList<>(underdeler);
        return kopiUnderdeler;
    }

    public ArrayList<Prosjekt> getTopper() {
        ArrayList<Prosjekt> kopiTopper = new ArrayList<>(topper);
        return kopiTopper;
    }

    public ArrayList<Prosjekt> getTilbehor() {
        ArrayList<Prosjekt> kopiTilbehor = new ArrayList<>(tilbehor);
        return kopiTilbehor;
    }

    public String getProsjektInfo(Prosjekt prosjekt) {
        String ferdig = "nei";
        if (prosjekt.getFerdigStilt() == true) {
            ferdig = "ja";
        }
        return "Info om plagget med navn: " + prosjekt.getNavn() + ":\n" + "\nType plagg: " + prosjekt.getKategori()
                + "\nAntall nøster brukt: "
                + prosjekt.getAntallNoster() + " stk." + "\nLengde på garnet: " + prosjekt.getGarnLengde() + " meter"
                + "\nFerdigstilt: "
                + ferdig;
    }

    
}
