package messenger;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

import model.Message;

@WebService
public interface MessengerItf { 

  @WebMethod 
  void saveMessage(Message msg, String receiver);

  @WebMethod
  List<Message> getMessages(String receiver);
  
}
