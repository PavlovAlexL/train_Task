package Tags;

import java.util.*;

public class Service extends Thread{

    private ArrayDeque<Message> messages = new ArrayDeque<>();    //очередь для сбора сообщений
    private static Map<Integer, Tag> tagsMap = new HashMap<>(); //

    {
        //tagsMap.put(123,new Tag(123));
    }

    private static boolean isRun = true;
    public void Finish(){
        isRun = false;
    }


    public void putMessage(Message message){
        messages.addLast(message);
    }

    @Override
    public void run() {
        while(isRun){
            try{
                sleep(1000);
                if(messages.isEmpty()) continue;
                System.out.println("messages is not empty");
                while(true){
                    Message m = messages.removeFirst();
                    System.out.println(m.getTimestamp() + m.getUid());
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }


        }
    }

    public void parse(){

    }




}
