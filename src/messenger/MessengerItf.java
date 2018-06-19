package messenger;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface MessengerItf { 
  @WebMethod 
  String messenger(String name); 
}
