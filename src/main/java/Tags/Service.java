package Tags;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Service extends Thread {

    private ArrayDeque<Message> messages = new ArrayDeque<>();    //общая очередь для сбора сообщений
    private Map<Integer, List<Message>> parsingUID = new ConcurrentHashMap<>();

    private static boolean isRun = true;

    public void Finish() {
        isRun = false;
    }

    public void putMessage(Message message) {
        messages.addLast(message);
    }

    @Override
    public void run() { //Парсим очередь, запускаем потоки на боработку
        while (isRun) {
            try {
                if (messages.isEmpty()) {
                    Thread.sleep(500);
                    continue;    //Ждем пока что то придет от датчиков
                }
                Message message = messages.pollFirst();     //Забираем из очереди первый message
                if (parsingUID.containsKey(message.getUid())) {
                    parsingUID.get(message.getUid()).add(message);
                } else {
                    parsingUID.put(message.getUid(), new ArrayList<>(Arrays.asList(message)));
                    new Thread(new UidCheckingThread(message)).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Поток, который будет обрабатывать конкретный UID.
     */
    class UidCheckingThread implements Runnable {
        private int uid;    //обрабатываемая RFID метка
        boolean run = true;
        List<Message> listMessages;

        UidCheckingThread(Message message) {
            this.uid = message.getUid();
            this.listMessages = parsingUID.get(uid);

        }

        public void run() {
            try {
                while (run) {
                    int size = listMessages.size();
                    Thread.sleep(2000);
                    if (listMessages.size() > size) { //если после паузы счетчик сообщений все еще растет, то
                        System.out.println("Поезд №" + listMessages.get(0).getUid() + " не покинул перегон");
                        softCheck(listMessages);
                    } else if (listMessages.size() == size) { // будем считать что поезд уехал, и мы можем проверить первую и последнюю точку видимости
                        strongCheck(listMessages);
                        run = false;
                    } else throw new Exception("Что то пошло не так!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                parsingUID.remove(uid);
            }
        }


        private boolean checkIn(List<Message> list) {
            return ((list.get(0).getAntenna() == 1) || (list.get(0).getAntenna() == 3)
                    & (list.get(1).getAntenna() == 1) || (list.get(1).getAntenna() == 3)
                    & (list.get(2).getAntenna() == 1) || (list.get(2).getAntenna() == 3)
            );
        }

        private boolean checkOut(List<Message> list) {
            return ((list.get(0).getAntenna() == 2) || (list.get(0).getAntenna() == 4)
                    & (list.get(1).getAntenna() == 2) || (list.get(1).getAntenna() == 4)
                    & (list.get(2).getAntenna() == 2) || (list.get(2).getAntenna() == 4)
            );
        }


        private void softCheck(List<Message> list) {
            if (checkIn(list)) {
                System.out.println("Поезд № " + list.get(0).getUid() + " въехал на перегон в направлении вьезда");
            } else {
                if (checkOut(list)) {
                    System.out.println("Поезд № " + list.get(0).getUid() + " въехал на перегон в направлении выезда");
                }
            }
        }

        private void strongCheck(List<Message> list) {
            int last = list.size() - 1;

            if (checkIn(list) & (
                    (list.get(last).getAntenna() == 2) || (list.get(last).getAntenna() == 4)
                    & (list.get(last - 1).getAntenna() == 2) || (list.get(last - 1).getAntenna() == 4)
                    & (list.get(last - 2).getAntenna() == 2) || (list.get(last - 2).getAntenna() == 4))
            ) {
                System.out.println("Поезд № " + list.get(0).getUid() + " въехал в тонель");
            } else {
            if (checkOut(list)& (
                    (list.get(last).getAntenna() == 1) || (list.get(last).getAntenna() == 3)
                    & (list.get(last - 1).getAntenna() == 1) || (list.get(last - 1).getAntenna() == 3)
                    & (list.get(last - 2).getAntenna() == 1) || (list.get(last - 2).getAntenna() == 3))
            ) {
                System.out.println("Поезд № " + list.get(0).getUid() + " выехал из тонеля");
            }
            }
        }
    }
}
