package Tags;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Objects;

@JsonAutoDetect
public class Message {

    private String timestamp;
    private int uid;
    private int antenna;

    public Message(){}

    String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    int getAntenna() {
        return antenna;
    }

    public void setAntenna(int antenna) {
        this.antenna = antenna;
    }

    public Message(String timestamp, int uid, int antenna) {
        this.timestamp = timestamp;
        this.uid = uid;
        this.antenna = antenna;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return uid == message.uid &&
                antenna == message.antenna &&
                Objects.equals(timestamp, message.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, uid, antenna);
    }

    @Override
    public String toString() {
        return timestamp + " " + uid + " " + antenna;
    }
}
