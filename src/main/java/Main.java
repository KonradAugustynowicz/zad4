import com.formdev.flatlaf.FlatLightLaf;

import java.io.IOException;

public class Main {
    static Frame frame;

    public static void main(String[] args) throws IOException {
        FlatLightLaf.setup();
        frame = new Frame();

        frame.setVisible (true);
    }
}
