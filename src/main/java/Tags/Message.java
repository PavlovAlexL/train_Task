package Tags;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

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




}
