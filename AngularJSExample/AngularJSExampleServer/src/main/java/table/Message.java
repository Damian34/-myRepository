package table;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Message {

    @XmlElement
    public String message;

    public Message() {
        this.message = "";
    }

    public Message(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"message\":\"" + message + "\"}";
    }

}
