package Tags;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@JsonAutoDetect
public class Tag {
    private String timestamp;             //todo: set private
    private int uid;                //todo: set private
    private int antenna;             //todo: set private

    private boolean status; //true - in(вьезд), false - out(выезд)

    public Tag(){}

    public Tag(String timestamp, int uid, int antenna) {
        this.uid = uid;
        this.antenna = antenna;
    }

    public Tag(Message message){
        this.uid = message.getUid();
        this.timestamp = message.getTimestamp();
        this.antenna = message.getAntenna();


    }



}
