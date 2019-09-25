package operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;

public class FileOperations {

    private String getConfigPath() {
        String path = "";
        try {
            File f = new File(System.getProperty("java.class.path"));
            File dir = f.getAbsoluteFile().getParentFile();
            path = dir.toString();
        } catch (Exception e) {
            System.out.println("error-getConfigPath: " + e);
        }
        return path;
    }

    public Config getConfigData(JLabel label) {
        String path = getConfigPath() + "\\config.txt";
        List<String> list = new ArrayList<String>();
        String host = "";
        String port = "";
        String datebase = "";
        String username = "";
        String pass = "";
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                list.add(st);
            }
            if (list.size() >= 5) {
                String[] p0 = list.get(0).split(" ");
                String[] p1 = list.get(1).split(" ");
                String[] p2 = list.get(2).split(" ");
                String[] p3 = list.get(3).split(" ");
                String[] p4 = list.get(4).split(" ");
                if (p0.length < 2 || p1.length < 2 || p2.length < 2 || p3.length < 2 || p4.length < 2) {
                    label.setText("nie prawidłowy format pliku Config.txt");
                    return null;
                }
                host = p0[1];
                port = p1[1];
                datebase = p2[1];
                username = p3[1];
                pass = p4[1];
                Config conf = new Config(host, port, datebase, username, pass);
                label.setText("pobrano tabele");
                return conf;
            } else {
                label.setText("nie prawidłowy format pliku Config.txt");
                return null;
            }
        } catch (Exception e) {
            label.setText("brak pliku Config.txt");
        }
        return null;
    }
}
