package messenger;

import java.io.IOException;
import java.util.*;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import org.apache.commons.lang3.SerializationUtils;
import com.rabbitmq.client.*;

import model.Message;

@WebService(endpointInterface = "messenger.MessengerItf")
@BindingType(value = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class MessengerImpl implements MessengerItf {
  
  private Channel chan;
  
  public MessengerImpl(Channel chan) {
    this.chan = chan;
  }

  public void saveMessage(Message msg, String receiver) {
    try {
      this.chan.queueDeclare(receiver, false, false, false, null);
      byte[] byteMsg = SerializationUtils.serialize(msg);
      this.chan.basicPublish("", receiver, null, byteMsg);
    }
    catch (IOException exp) {
      System.out.println(exp.getMessage());
    }
  }

  public List<Message> getMessages(String receiver) {
    List<Message> messages = new ArrayList<Message>();
    
    try {
      this.chan.queueDeclare(receiver, false, false, false, null);

      boolean autoAck = false;
      GetResponse response;
      
      do {
        response = this.chan.basicGet(receiver, autoAck);
        
        if (response != null) {
          //AMQP.BasicProperties props = response.getProps();
          byte[] body = response.getBody();
          Message msg = SerializationUtils.deserialize(body);
          messages.add(msg);
          
          // acknowledge receipt of the message
          long deliveryTag = response.getEnvelope().getDeliveryTag();
          this.chan.basicAck(deliveryTag, false); 
        }
      } while (response != null);
    }
    catch (IOException exp) {
      System.out.println(exp.getMessage());
    }
    
    return messages;
  }

}
