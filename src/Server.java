import javax.xml.ws.Endpoint;
import org.apache.commons.cli.*;

import messenger.MessengerImpl;

public class Server {
  public static void main(String[] args) throws Exception {
    // create the Options
    Options options = new Options();
    options.addOption("h", "host", true, "host/ip address");
    options.addOption("p", "port", true, "port");

    // create the command line parser
    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    try {
      String host = "localhost", port;
      
      // parse the command line arguments
      CommandLine line = parser.parse( options, args );
  
      if( line.hasOption( "host" ) ) {
        host = line.getOptionValue( "host" );
      }
      else {
        host = "localhost";
      }
  
      if( line.hasOption( "port" ) ) {
        port = line.getOptionValue( "port" );
      }
      else {
        port = "9999";
      }

      String endpointAddress = "http://" + host + ":" + port + "/messenger";
      Endpoint.publish(endpointAddress, new MessengerImpl());
    }
    catch( ParseException exp ) {
        System.out.println( exp.getMessage() );
        formatter.printHelp("Server", options);

        System.exit(1);
    }

    System.out.println("\r\n\r\nPress any key to close");
    System.in.read();
    System.exit(0);
  }
}
