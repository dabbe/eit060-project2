
/*
 * This example shows how to set up a key manager to perform client
 * authentication.
 *
 * This program assumes that the client is not inside a firewall.
 * The application can be modified to connect to a server outside
 * the firewall by following SSLSocketClientWithTunneling.java.
 */
public class client {

    public static void main(String[] args) throws Exception {
        String host = null;
        int port = -1;
        for (int i = 0; i < args.length; i++) {
            System.out.println("args[" + i + "] = " + args[i]);
        }
        if (args.length < 2) {
            System.out.println("USAGE: java client host port");
            System.exit(-1);
        }
        try { /* get input parameters */
            host = args[0];
            port = Integer.parseInt(args[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("USAGE: java client host port");
            System.exit(-1);
        }
        
        HospitalConnection c = null;
        try { /* set up a key manager for client authentication */
        	c = new HospitalConnection(host, port);
        	System.out.println("GET? " + c.getResponse("GET"));
        	System.out.println("PUT? " + c.getResponse("PUT"));
        	System.out.println("ADD? " + c.getResponse("ADD"));
        	System.out.println("DELETE? " + c.getResponse("DELETE"));
        	System.out.println("RANDOM? " + c.getResponse("RANDOM"));
        	c.close();
        } catch (Exception e) {
            e.printStackTrace();
            if(c != null)
            	c.close();
        }
    }

}
