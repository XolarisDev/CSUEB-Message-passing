import java.io.*;
import java.net.*;
public class Server {
public static final int PORT = 2000; //Server PORT
public static void main(String[] args) {//Driver code
  ServerSocket server = null;
  //try to connect to clients
  try {
    //listen on PORT
    server = new ServerSocket (PORT);
    server.setReuseAddress(true);
    System.out.println("[Server] Server is running on PORT" + PORT + "\n");
    //infinite loop for connecting to client
    while (true) {
      System.out.println("[Server] Waiting for clients to connect...\n");
      // socket object to receive incoming client requests
      Socket client = server.accept();
      // Displaying that new connected client
      System.out.println("\n[Server] New client connected" + client.getInetAddress().getHostAddress());
      // Upon receiving an incoming connection, create a new thread for handling this connection until it is closed
      ClientHandler clientSock = new ClientHandler(client);
      // This thread will handle the client
      new Thread(clientSock).start();
    }
  }catch (IOException e){ //End of try
    e.printStackTrace();
  }finally {
    if (server != null) {
      try {
        server.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}//End of Server Class
  private static class ClientHandler implements Runnable {
    private final Socket clientSocket;
    //constructor initializes the client socket
    public ClientHandler(Socket socket) {
      this.clientSocket = socket;
    }
    public void run() {
      try {
        //Create input stream
        ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
        //Create output stream
        ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        while (true) {
          // Receive 1 message from the client
          Message receivedMessage = (Message) inputStream.readObject();
          //If message is of type login
          if ("login".equals(receivedMessage.getType())) {
            // create new message. Of type login, with "success" status.
            Message loginSuccess = new Message("login", "success", "");
            //send the new message
            outputStream.writeObject(loginSuccess);
            System.out.println("[Server] Sent Login Success message to client!\n");
          }
            //if message type is "text"
          else if ("text".equals(receivedMessage.getType())) {
            System.out.println("[Server] Recieved this message from client:");
            System.out.println(receivedMessage.getText() + "\n");
            //Variable containing the received text
            String newText = receivedMessage.getText();
            receivedMessage.setText("[Server] " + newText.toUpperCase());
            // Send uppercased text back to the client
            outputStream.writeObject(receivedMessage);
            System.out.println("[Server] Sent back in all caps to client!\n");
          }
            //if message type is "logout"
          else if ("logout".equals(receivedMessage.getType())) {
            System.out.println("[Server] Recieved logout message from client...\n");
            //Create a new Message of type "logout", with status "Success"
            Message logoutResponse = new Message("logout", "success", "");
            //Send the new message to client
            outputStream.writeObject(logoutResponse);
            // Close socket
            clientSocket.close();
            System.out.println("[Server] Successfully closed client socket!\n");
            break;
          }
        }
      }catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
  }
}
