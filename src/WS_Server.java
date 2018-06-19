import java.net.ConnectException;
import javax.xml.ws.Endpoint;
import com.rabbitmq.client.*;
import org.apache.commons.cli.*;

import messenger.MessengerImpl;

public class WS_Server {

  public static void main(String[] args) throws Exception {
    String ws_loc = "localhost:9999";
    String mq_uri = "amqp://localhost";
    
    // Create the Options
    Options options = new Options();
    // "localhost:port"
    options.addOption("s", "ws_loc", true, "Web-Service's endpoint address to publish");
    // "amqp://userName:password@hostName:portNumber/virtualHost"
    options.addOption("m", "mq_uri", true, "Message Broker's connection URI");

    // Create the command line parser
    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    // Parse the command line arguments
    try {
      CommandLine line = parser.parse( options, args );
    
      if( line.hasOption( "ws_loc" ) ) {
        ws_loc = line.getOptionValue( "ws_loc" );
      }
    
      if( line.hasOption( "mq_uri" ) ) {
        mq_uri = line.getOptionValue( "mq_uri" );
      }
    }
    catch ( ParseException exp ) {
      System.out.println( exp.getMessage() );
      formatter.printHelp("Server", options);
      System.exit(1);
    }

    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri(mq_uri);
    Connection conn = null;
    Channel chan = null;
    
    // Connect to the Message Broker
    try {
      conn = factory.newConnection();
      chan = conn.createChannel();
    }
    catch (ConnectException exp) {
      System.out.println("Message Broker exception - " + exp.getMessage());
      System.exit(1);
    }

    String endpointAddress = "http://" + ws_loc + "/messenger";
    Endpoint.publish(endpointAddress, new MessengerImpl(chan));

    System.out.println("\r\n\r\nPress any key to close");
    System.in.read();
    
    chan.close();
    conn.close();
    System.exit(0);
  }
  
}
