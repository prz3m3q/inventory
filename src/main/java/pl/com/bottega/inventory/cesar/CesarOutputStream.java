package pl.com.bottega.inventory.cesar;

import java.io.IOException;
import java.io.OutputStream;

public class CesarOutputStream extends OutputStream {

    private OutputStream docorated;
    private Integer key;

    public CesarOutputStream(OutputStream docorated, Integer key) {
        this.docorated = docorated;
        this.key = key;
    }

    @Override
    public void write(int i) throws IOException {
        docorated.write((i + key) % 256);
    }
}
