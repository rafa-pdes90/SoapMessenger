package messenger;

import javax.jws.WebService;
import javax.xml.ws.BindingType;

import model.Message;

@WebService(endpointInterface = "messenger.MessengerItf")
@BindingType(value = "http://java.sun.com/xml/ns/jaxws/2003/05/soap/bindings/HTTP/")
public class MessengerImpl implements MessengerItf {

  public void saveMessage(Message msg, String receiver) {
    StringBuffer strbuf = new StringBuffer(msg);
    System.out.println("Recebido: "+msg);
    String retorno = (strbuf.reverse()).toString();
    return retorno;
  }

  public Message[] getMessages(String receiver) {
    
  }

}
