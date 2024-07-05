import java.io.*;
import java.net.*;
import java.util.*;
public class Client {
  public static final int PORT = 2000;
  public static void main(String[] args){ //Driver code
    // establish a connection by providing host and port
    System.out.println("[Client] Attempting to connect to server!\n");
    try {
      Socket socket = new Socket("localhost", PORT);
      System.out.println("[Client] Connected to Server!\n");
      //Create output stream FIRST, it was bugging if not
      ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
      //Create input stream
      ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
      /* THIS DIDN'T WORK MAYBE FIX LATER???
      //prompt user to login
      System.out.println("[Client] Would you like to login? Enter 'y' or 'n'\n");
      String decision = new Scanner (System.in).nextLine();
      if (decision == "Y" || decision == "y") {
        //Send message of type "login" to the server
        Message loginMessage = new Message("login", "", "");
        outputStream.writeObject(loginMessage);
      }
      else {
        System.out.println("[Client] Login Input Failed! Closing Client\n");
        socket.close();
      }
      */
      //Send message of type "login" to the server
      Message loginMessage = new Message("login", "", "");
      outputStream.writeObject(loginMessage);
      // Receive response message from the server
      Message loginResponse = (Message) inputStream.readObject();
      //if response from server is a message of status "success"
      if ("success".equals(loginResponse.getStatus())) {
        System.out.println("[Client] Login successful! \n");
        //Scanner for user input
        Scanner scanner = new Scanner(System.in);
        boolean isSockClosed = false;
        //infinite loop until socket closes
        while (true) {
          System.out.println("\nYou Can Send A Message Or Logout\nSend A Message By Entering '1'\nLogout By Entering '2' Or Anything Else");
          System.out.println("(1)Send Message \t (2)Logout");
          String choice = scanner.nextLine();
          //switch case 1 or 2
          switch (choice) {
            case "1":
              System.out.println("[Client] Enter the text that you want to send! \n");
              String inputText = scanner.nextLine();
              //Create new message object with type "text", with inputText String as the text.
              Message textMessage = new Message("text", "", inputText);
              //Send through output stream
              outputStream.writeObject(textMessage);
              System.out.println("[Client] Text Message sent to Server! \n");
              // Receive response message from the server
              Message serverResponse = (Message)inputStream.readObject();
              // Display server's response
              System.out.println(serverResponse.getText());
              break;
            default:
              System.out.println("[Client] Attempting to exit...");
              // Send logout message to the server
              Message logoutMessage = new Message("logout", "", "");
              outputStream.writeObject(logoutMessage);
              // Close the socket and exit the program
              System.out.println("[Client] Exit success! closing socket!");
              socket.close();
              isSockClosed = true;
              break;
          }
          if (isSockClosed == true) {
            break;
          }
        }
      }
      else{
        System.out.println("Login failed. Exiting.");
      }
    }catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
