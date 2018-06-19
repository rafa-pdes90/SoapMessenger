package mom;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.SerializationUtils;

import model.Message;

public class Recv {

  private final static String QUEUE_NAME = "hello";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    
    boolean autoAck = false;
    GetResponse response;
    do {
      response = channel.basicGet(QUEUE_NAME, autoAck);
      
      if (response != null) {
        //AMQP.BasicProperties props = response.getProps();
        byte[] body = response.getBody();
        Message msg = SerializationUtils.deserialize(body);
        long deliveryTag = response.getEnvelope().getDeliveryTag();
        
        System.out.println(" [x] Received '" + msg.Content + " from " + msg.Sender + "'");
        
        channel.basicAck(deliveryTag, false); // acknowledge receipt of the message
      }
    } while (response != null);

    System.exit(0);
  }
}
