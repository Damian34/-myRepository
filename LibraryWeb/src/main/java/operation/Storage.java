package operation;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import operation.table.Book;
import operation.table.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Storage<T> {

    private final XStream xstream = this.prepareXStream();
    private static final Logger logger = LogManager.getRootLogger();
    private Set<T> table;
    private FileName fileName;

    public Storage(Set<T> table, Class<T> type) {
        this.table = table;
        try {
            this.fileName = FileName.valueOf(type.getSimpleName().toUpperCase());
        } catch (IllegalArgumentException e) {
            this.logger.error(e);
        }
    }

    public void save() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName.name))) {
            xstream.toXML(this.table, fileWriter);
        } catch (IOException e) {
            this.logger.error(e.getMessage());
        }
        this.logger.info("saved to file " + fileName.name);
    }

    public Set<T> read() {
        try {
            this.logger.info("read from file " + fileName.name);
            this.table = (Set<T>) xstream.fromXML(new File(fileName.name));
        } catch (XStreamException e) {
            this.table = new LinkedHashSet<>();
            logger.error(e);
        }
        return this.table;
    }

    private XStream prepareXStream() {
        XStream xstream = new XStream();
        xstream.alias("book", Book.class);
        xstream.alias("user", User.class);
        XStream.setupDefaultSecurity(xstream);
        Class<?>[] classes = {Book.class, User.class};
        xstream.allowTypes(classes);
        return xstream;
    }
}
