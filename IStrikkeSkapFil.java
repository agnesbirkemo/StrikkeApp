package StrikkeSkap;

import java.io.File;
import java.io.FileNotFoundException;

public interface IStrikkeSkapFil {

    StrikkeSkap readStrikkeSkap(String filename, StrikkeSkap strikkeSkap) throws FileNotFoundException;

    void writeStrikkeSkap(String filename, StrikkeSkap strikkeSkape) throws FileNotFoundException;

}
