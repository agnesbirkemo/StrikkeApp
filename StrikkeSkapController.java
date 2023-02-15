package StrikkeSkap;

import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class StrikkeSkapController {

    private StrikkeSkap strikkeSkap;
    private IStrikkeSkapFil strikkeSkapFil = new StrikkeSkapFil();

    // Legg til/fjern prosjekt:
    @FXML
    public Label typePlagg, antallNoster, garnlengde, ferdigstilt, navnOppskrift;

    @FXML
    public Text meterText, stkText, sokProsjektInfoText;
    @FXML
    public ChoiceBox<String> kategoriPlaggAdd;
    @FXML
    public TextField navnAdd, antallNosterAdd, lengdeGarnAdd;

    @FXML
    public ChoiceBox<String> ferdigstiltAdd;

    @FXML
    public Button addProsjektButton, slettProsjektButton, ferdigStillButton, sokButton;

    @FXML
    public Button addProsjektView, ferdigstillView, slettProsjektView, sokProsjektView;

    // Oversikt:

    @FXML
    public Text totAntPlaggText, antUferdigeText, antFerdigeText, totAntNosterText, totMeterStrikketText;

    // Trykk på det du vil se:

    @FXML
    public Button antallGenserebButton, antallUnderdelerButton, antallJakkerButton, antallHodeplaggButton,
            antallSokkerButton, antallTilbehorButton, antallTopperButton, antallKjolerButton;

    @FXML
    public Button visGensereButton, visUnderdelerButton, visJakkerButton, visHodeplaggButton, visSokkerButton,
            visTilbehorButton, visTopperButton, visKjolerButton;

    @FXML
    public CheckBox kunFerdigeCheckBox;

    @FXML
    public Button visAlleButton;

    @FXML
    public Text buttonOutputText;

    public ObservableList<String> kategorierList = FXCollections.observableArrayList("genser", "jakke", "hodeplagg",
            "kjole", "sokker/tøfler", "underdel", "topp", "tilbehør");
    private ObservableList<String> ferdigstiltList = FXCollections.observableArrayList("ja", "nei");

    @FXML
    public void initialize() {
        strikkeSkap = new StrikkeSkap();

        /*
         * TextInputDialog dialog = new TextInputDialog();
         * dialog.setTitle("Åpne strikkeskap");
         * dialog.setHeaderText("Skriv inn navnet på Strikkeskapet du vil åpne.");
         * dialog.setContentText("Navn:");
         * 
         * // sjekke om filen eksisterer, hvis ikke opprettes en ny fil med navnet som
         * gis.
         * String strikkeSkapNavn = dialog.showAndWait().get();
         * // strikkeSkapFil.getFile(strikkeSkapNavn);
         * 
         * try {
         * strikkeSkapFil.readStrikkeSkap(strikkeSkapNavn, strikkeSkap);
         * 
         * } catch (FileNotFoundException e) {
         * showErrorMessage("Strikkeskap ble ikke funnet.");
         * 
         * TextInputDialog d = new TextInputDialog();
         * d.setTitle("Åpne strikkeskap");
         * d.setHeaderText("Skriv inn navnet på Strikkeskapet du vil opprette.");
         * d.setContentText("Navn:");
         * }
         */

        try {
            strikkeSkapFil.readStrikkeSkap("strikkeSkap", strikkeSkap);

        } catch (FileNotFoundException e) {
            showErrorMessage("Strikkeskap ble ikke funnet.");
        }
        upDateOversikt();
        kategoriPlaggAdd.setItems(kategorierList);
        kategoriPlaggAdd.setValue("tilbehør");

        ferdigstiltAdd.setItems(ferdigstiltList);
        ferdigstiltAdd.setValue("ja");

        Text totAntPlaggText = new Text();
        Text antUferdigeText = new Text();
        Text antFerdigeText = new Text();
        Text totAntNosterText = new Text();
        Text totMeterStrikketText = new Text();

    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("En feil har oppstått:");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void save() {
        try {
            strikkeSkapFil.writeStrikkeSkap("strikkeSkap", strikkeSkap);
        } catch (FileNotFoundException e) {
            showErrorMessage("fant ikke filen.");
            // e.printStackTrace();
        }
    }

    @FXML
    public void handleAddProsjekt() {
        String navn = navnAdd.getText();
        String kategori = kategoriPlaggAdd.getValue();
        boolean ferdigstilt;
        int noster = -1;
        int lengde = -1;

        // så true = ja og false = nei.
        if (ferdigstiltAdd.getValue().equals("ja")) {
            ferdigstilt = true;
        } else {
            ferdigstilt = false;
        }

        // feilhåndering brukergrensesnitt:
        try {
            noster = Integer.parseInt(antallNosterAdd.getText());
        } catch (NumberFormatException e) {
            showErrorMessage("Antall nøster må oppgis som et positivt heltall. For eksempel 8.");
        }
        try {
            lengde = Integer.parseInt(lengdeGarnAdd.getText());
        } catch (NumberFormatException e) {
            showErrorMessage("Garnlengden må oppgis som et positivt heltall. For eksempel 125.");
        }

        if (noster != -1 && lengde != -1) {
            try {
                strikkeSkap.addProsjekt(new Prosjekt(navn, kategori, noster, lengde, ferdigstilt));
                upDateOversikt();
                checkBoxEndreOutput();

                navnAdd.clear();
                antallNosterAdd.clear();
                lengdeGarnAdd.clear();
                ferdigstiltAdd.setValue("ja");
                kategoriPlaggAdd.setValue("tilbehør");
            } catch (IllegalArgumentException e) {
                // showErrorMessage("Du har allerede et prosjekt med navn " + navn + ".
                // Vennligst tast inn et unikt navn.");
                showErrorMessage("Navn må være unikt. Lengde på garn og antall nøster må være positive heltall.");
            }
        }
        save();
    }

    private void upDateOversikt() {
        int antPlagg = strikkeSkap.antallAv(strikkeSkap.getProsjekter(), false);
        totAntPlaggText.setText("Totalt antall plagg: " + antPlagg);

        int antUferdige = strikkeSkap.antallAv(strikkeSkap.getUferdigeProsjekter(), false);
        antUferdigeText.setText("Pågående prosjekter: " + antUferdige);

        int antFerdige = strikkeSkap.antallAv(strikkeSkap.getFerdigeProsjekter(), true);
        antFerdigeText.setText("Ferdige prosjekter: " + antFerdige);

        String totAntNoster = String.valueOf(strikkeSkap.nosterBrukt());
        totAntNosterText.setText("Antall nøster brukt: " + totAntNoster);

        String totMeterStrikket = String.valueOf(strikkeSkap.meterStrikket());
        totMeterStrikketText.setText("Antall meter strikket: " + totMeterStrikket);
    }

    @FXML
    public void fjernProsjektView() {
        typePlagg.setVisible(false);
        antallNoster.setVisible(false);
        garnlengde.setVisible(false);
        ferdigstilt.setVisible(false);
        kategoriPlaggAdd.setVisible(false);
        antallNosterAdd.setVisible(false);
        stkText.setVisible(false);
        lengdeGarnAdd.setVisible(false);
        meterText.setVisible(false);
        ferdigstiltAdd.setVisible(false);
        sokProsjektInfoText.setVisible(false);
        sokProsjektInfoText.setText(" ");

        slettProsjektButton.setVisible(true);
        ferdigStillButton.setVisible(false);
        addProsjektButton.setVisible(false);
        sokButton.setVisible(false);

        addProsjektView.setDefaultButton(false);
        ferdigstillView.setDefaultButton(false);
        slettProsjektView.setDefaultButton(true);
        sokProsjektView.setDefaultButton(false);

    }

    @FXML
    public void addProsjektView() {
        typePlagg.setVisible(true);
        antallNoster.setVisible(true);
        garnlengde.setVisible(true);
        meterText.setVisible(true);
        ferdigstilt.setVisible(true);
        kategoriPlaggAdd.setVisible(true);
        antallNosterAdd.setVisible(true);
        stkText.setVisible(true);
        lengdeGarnAdd.setVisible(true);
        ferdigstiltAdd.setVisible(true);
        sokProsjektInfoText.setVisible(false);
        sokProsjektInfoText.setText(" ");

        slettProsjektButton.setVisible(false);
        ferdigStillButton.setVisible(false);
        addProsjektButton.setVisible(true);
        sokButton.setVisible(false);

        addProsjektView.setDefaultButton(true);
        ferdigstillView.setDefaultButton(false);
        slettProsjektView.setDefaultButton(false);
        sokProsjektView.setDefaultButton(false);
    }

    @FXML
    public void ferdigstillView() {
        typePlagg.setVisible(false);
        antallNoster.setVisible(false);
        garnlengde.setVisible(false);
        meterText.setVisible(false);
        ferdigstilt.setVisible(false);
        kategoriPlaggAdd.setVisible(false);
        antallNosterAdd.setVisible(false);
        lengdeGarnAdd.setVisible(false);
        stkText.setVisible(false);
        ferdigstiltAdd.setVisible(false);
        sokProsjektInfoText.setVisible(false);
        sokProsjektInfoText.setText(" ");

        slettProsjektButton.setVisible(false);
        ferdigStillButton.setVisible(true);
        addProsjektButton.setVisible(false);
        sokButton.setVisible(false);

        addProsjektView.setDefaultButton(false);
        ferdigstillView.setDefaultButton(true);
        slettProsjektView.setDefaultButton(false);
        sokProsjektView.setDefaultButton(false);
    }

    @FXML
    public void sokProsjektView() {
        typePlagg.setVisible(false);
        antallNoster.setVisible(false);
        garnlengde.setVisible(false);
        meterText.setVisible(false);
        ferdigstilt.setVisible(false);
        kategoriPlaggAdd.setVisible(false);
        antallNosterAdd.setVisible(false);
        stkText.setVisible(false);
        lengdeGarnAdd.setVisible(false);
        ferdigstiltAdd.setVisible(false);
        sokProsjektInfoText.setVisible(true);

        slettProsjektButton.setVisible(false);
        ferdigStillButton.setVisible(false);
        addProsjektButton.setVisible(false);
        sokButton.setVisible(true);

        addProsjektView.setDefaultButton(false);
        ferdigstillView.setDefaultButton(false);
        slettProsjektView.setDefaultButton(false);
        sokProsjektView.setDefaultButton(true);
    }

    public void handleFjernProsjekt() {
        String navn = navnAdd.getText();
        boolean isInStrikkeSkap = false;

        for (Prosjekt p : strikkeSkap.getProsjekter()) {
            if (p.getNavn().equals(navn)) {
                strikkeSkap.removeProsjekt(p);
                isInStrikkeSkap = true;
            }
        }
        if (isInStrikkeSkap == false) {
            showErrorMessage("Plagget med navn " + navn + " finnes ikke i strikkeskapet ditt.");
        }
        checkBoxEndreOutput();
        navnAdd.clear();
        upDateOversikt();
        save();
    }

    public void setFerdigStilt() {
        String navn = navnAdd.getText();
        boolean isInUferdige = false;
        // må koble navnet til et prosjekt for å fjerne prosjektet:
        for (Prosjekt prosjekt : strikkeSkap.getUferdigeProsjekter()) {
            if (prosjekt.getNavn().equals(navn)) {
                strikkeSkap.setFerdigProsjekt(prosjekt);
                isInUferdige = true;
            }
        }

        if (isInUferdige == false) {
            showErrorMessage("Du har ikke et plagg med navn " + navn + " blant de uferdige prosjektene dine. " + navn
                    + " kan dermed ikke settes til ferdigstilt.");
        }

        checkBoxEndreOutput();
        navnAdd.clear();
        upDateOversikt();
        save();
    }

    public void handleSokEtterProsjekt() {
        String navn = navnAdd.getText();
        boolean isInStrikkeSkap = false;

        for (Prosjekt p : strikkeSkap.getProsjekter()) {
            if (p.getNavn().equals(navn)) {
                sokProsjektInfoText.setText(strikkeSkap.getProsjektInfo(p));
                isInStrikkeSkap = true;
            }
        }
        if (isInStrikkeSkap == false) {
            showErrorMessage("Plagget med navn " + navn + " finnes ikke i strikkeskapet ditt.");
        }
        checkBoxEndreOutput();
        navnAdd.clear();
    }

    String ButtonOutputTextStatus = "empty";

    public boolean visKunFerdigeCheckBox() {
        return kunFerdigeCheckBox.isSelected();
    }

    public void checkBoxEndreOutput() {

        if (ButtonOutputTextStatus.equals("empty"))
            ButtonOutputTextStatus = "empty";
        else if (ButtonOutputTextStatus.equals("#gensere"))
            handleAntallGensere();
        else if (ButtonOutputTextStatus.equals("#underdeler"))
            handleAntallUnderdeler();
        else if (ButtonOutputTextStatus.equals("#jakker"))
            handleAntallJakker();
        else if (ButtonOutputTextStatus.equals("#hodeplagg"))
            handleAntallHodeplagg();
        else if (ButtonOutputTextStatus.equals("#sokker"))
            handleAntallSokker();
        else if (ButtonOutputTextStatus.equals("#tilbehør"))
            handleAntallTilbehor();
        else if (ButtonOutputTextStatus.equals("#topper"))
            handleAntallTopper();
        else if (ButtonOutputTextStatus.equals("#kjoler"))
            handleAntallKjoler();
        else if (ButtonOutputTextStatus.equals("visGensere"))
            handleVisGensere();
        else if (ButtonOutputTextStatus.equals("visUnderdeler"))
            handleVisUnderdeler();
        else if (ButtonOutputTextStatus.equals("visJakker"))
            handleVisJakker();
        else if (ButtonOutputTextStatus.equals("visHodeplagg"))
            handleVisHodeplagg();
        else if (ButtonOutputTextStatus.equals("visSokker"))
            handleVisSokker();
        else if (ButtonOutputTextStatus.equals("visTilbehør"))
            handleVisTilbehor();
        else if (ButtonOutputTextStatus.equals("visTopper"))
            handleVisTopper();
        else if (ButtonOutputTextStatus.equals("visKjoler"))
            handleVisKjoler();
        else if (ButtonOutputTextStatus.equals("visAlle"))
            handleVisAlle();

    }

    public void handleVisAlle() {
        buttonOutputText.setText(
                "Plagg strikket: " + strikkeSkap.seProsjekter(strikkeSkap.getProsjekter(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visAlle";
    }

    public void handleAntallGensere() {
        buttonOutputText.setText(
                "Antall gensere strikket: " + strikkeSkap.antallAv(strikkeSkap.getGensere(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#gensere";
    }

    public void handleAntallUnderdeler() {
        buttonOutputText.setText(
                "Antall underdeler strikket: "
                        + strikkeSkap.antallAv(strikkeSkap.getUnderdeler(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#underdeler";
    }

    public void handleAntallJakker() {
        buttonOutputText.setText(
                "Antall jakker strikket: " + strikkeSkap.antallAv(strikkeSkap.getJakker(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#jakker";
    }

    public void handleAntallHodeplagg() {
        buttonOutputText.setText(
                "Antall hodeplagg strikket: "
                        + strikkeSkap.antallAv(strikkeSkap.getHodeplagg(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#hodeplagg";
    }

    public void handleAntallSokker() {
        buttonOutputText.setText(
                "Antall sokker/tøfler strikket: "
                        + strikkeSkap.antallAv(strikkeSkap.getSokkerTofler(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#sokker";
    }

    public void handleAntallTilbehor() {
        buttonOutputText.setText(
                "Antall tilbehør strikket: "
                        + strikkeSkap.antallAv(strikkeSkap.getTilbehor(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#tilbehør";
    }

    public void handleAntallTopper() {
        buttonOutputText.setText(
                "Antall topper strikket: " + strikkeSkap.antallAv(strikkeSkap.getTopper(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#topper";
    }

    public void handleAntallKjoler() {
        buttonOutputText.setText(
                "Antall kjoler strikket: " + strikkeSkap.antallAv(strikkeSkap.getKjoler(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "#kjoler";
    }

    // vis-knappene:
    public void handleVisGensere() {
        buttonOutputText.setText(
                "Gensere strikket: " + strikkeSkap.seProsjekter(strikkeSkap.getGensere(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visGensere";
    }

    public void handleVisUnderdeler() {
        buttonOutputText.setText(
                "Underdeler strikket: "
                        + strikkeSkap.seProsjekter(strikkeSkap.getUnderdeler(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visUnderdeler";
    }

    public void handleVisJakker() {
        buttonOutputText.setText(
                "Jakker strikket: " + strikkeSkap.seProsjekter(strikkeSkap.getJakker(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visJakker";
    }

    public void handleVisHodeplagg() {
        buttonOutputText.setText(
                "Hodeplagg strikket: " + strikkeSkap.seProsjekter(strikkeSkap.getHodeplagg(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visHodeplagg";
    }

    public void handleVisSokker() {
        buttonOutputText.setText(
                "Sokker/tøfler strikket: "
                        + strikkeSkap.seProsjekter(strikkeSkap.getSokkerTofler(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visSokker";
    }

    public void handleVisTilbehor() {
        buttonOutputText.setText(
                "Tilbehør strikket: " + strikkeSkap.seProsjekter(strikkeSkap.getTilbehor(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visTilbehør";
    }

    public void handleVisTopper() {
        buttonOutputText.setText(
                "Topper strikket: " + strikkeSkap.seProsjekter(strikkeSkap.getTopper(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visTopper";
    }

    public void handleVisKjoler() {
        buttonOutputText.setText(
                "Kjoler strikket: " + strikkeSkap.seProsjekter(strikkeSkap.getKjoler(), visKunFerdigeCheckBox()));
        ButtonOutputTextStatus = "visKjoler";
    }

}
