package timecounter.operation;

import java.io.IOException;
public class CloseSystem {

    public void shutdownSystem() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("shutdown -s -t 0");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("error: "+e);
        }
    }
}
