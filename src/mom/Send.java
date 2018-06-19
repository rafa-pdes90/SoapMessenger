package mom;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.SerializationUtils;

import model.Message;

public class Send {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String sender = "Rafael";
    String message = "Hello World!";
    Message msg = new Message();
    msg.Sender = sender;
    msg.Content = message;
    byte[] out = SerializationUtils.serialize(msg);
    channel.basicPublish("", QUEUE_NAME, null, out);
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
  }
}
