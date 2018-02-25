package pl.com.bottega.inventory.cesar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CesarInputStream extends InputStream {

    private InputStream docorated;

    private Integer key;

    public CesarInputStream(InputStream docorated, Integer key) {
        this.docorated = docorated;
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int result = docorated.read();
        if (result == -1) {
            return result;
        }
        result = (result - key) % 256;
        if (result < 0) {
            return result + 256;
        }
        return result;
    }
}
