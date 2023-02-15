package StrikkeSkap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class StrikkeSkapFil implements IStrikkeSkapFil {

    @Override
    public StrikkeSkap readStrikkeSkap(String filename, StrikkeSkap strikkeSkap) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(getFile(filename))) {

            while (scanner.hasNextLine()) {
                String[] prosjektInfo = scanner.nextLine().split("\t");
                // legger til prosjektene som er lagret i fil til appen igjen.
                strikkeSkap
                        .addProsjekt(new Prosjekt(prosjektInfo[0], prosjektInfo[1], Integer.parseInt(prosjektInfo[2]),
                                Integer.parseInt(prosjektInfo[3]), Boolean.parseBoolean(prosjektInfo[4])));
            }
        }

        return strikkeSkap;
    }

    @Override
    public void writeStrikkeSkap(String filename, StrikkeSkap strikkeSkap) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(getFile(filename))) {

            for (Prosjekt prosjekt : strikkeSkap.getProsjekter()) {
                pw.println(prosjekt.getNavn() + "\t" + prosjekt.getKategori() + "\t" + prosjekt.getAntallNoster() + "\t"
                        + prosjekt.getGarnLengde() + "\t" + prosjekt.getFerdigStilt());
            }
        }

    }

    private static File getFile(String filename) {
        return new File("src\\main\\resources\\" + filename + ".txt");
    }

}
