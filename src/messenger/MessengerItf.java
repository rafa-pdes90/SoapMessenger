package messenger;

import javax.jws.WebMethod;
import javax.jws.WebService;

import model.Message;

@WebService
public interface MessengerItf { 

  @WebMethod 
  void saveMessage(Message msg, String receiver);

  @WebMethod
  Message[] getMessages(String receiver);
  
}
