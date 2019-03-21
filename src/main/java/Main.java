import Tags.Message;
import Tags.Tag;
import Tags.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import static spark.Spark.*;

public class Main {

    private static ObjectMapper om = new ObjectMapper();    //Создаем объект для десериализации из JSON

    public static void main(String[] args) {
        port(8080);
        Service Service = new Service();   //Создаем поток для обработки наших входных данных
        Service.start();

        get("/", (request, response) -> "Hello my friend, POST your data to /event");

        post("/event", "application/json", (request, response) -> {
            String jsonStirng = request.body(); //Получаем request
            Message message = om.readValue(jsonStirng, Message.class);  //Десериализуем request
            Service.putMessage(message); //отдаем на обработку
            return response.raw().getStatus();
        });

    }

}

//curl -d '{"timestamp": "1552438095.245", "uid": 123, "antenna": 1}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438095.245", "uid": 123, "antenna": 3}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438095.245", "uid": 123, "antenna": 1}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438095.325", "uid": 123, "antenna": 1}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438095.345", "uid": 123, "antenna": 1}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438095.360", "uid": 123, "antenna": 2}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438096.245", "uid": 123, "antenna": 3}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438096.345", "uid": 123, "antenna": 2}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438097.345", "uid": 123, "antenna": 2}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438097.445", "uid": 123, "antenna": 4}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438097.545", "uid": 123, "antenna": 4}' -H "Content-Type: application/json" -X POST http://localhost:8080/event
//curl -d '{"timestamp": "1552438098.245", "uid": 123, "antenna": 2}' -H "Content-Type: application/json" -X POST http://localhost:8080/event


