import javax.xml.ws.Endpoint;
import messenger.MessengerImpl;

public class Server {
  public static void main(String[] args) {
    Endpoint.publish("http://localhost:9999/messenger", new MessengerImpl());
  }
}
