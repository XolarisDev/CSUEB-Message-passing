# CSUEB-Message-passing
A 2 way message passing objects.

Message Object:
The message types are: login, text, logout. The message type is simply a String attribute
that represents the type. For example, login messages should set type = “login”. The
message class is used for sending messages between server and client objects. 

Server Object:
The server listens for incoming connections from clients. On receiving an incoming
connection, create a new thread for handling this connection until it is closed. This will
need to be a two way connection.
The server does nothing with the new connection until a ‘login message’ is received
from the client. On receipt of the ‘login message’, the server changes the status of this
connection to ‘success’. It then returns a new ‘success’ object to the client.
If this connection has logged in, the server will accept a ‘text’ or ‘logout message’
message. On receipt of logout, a ‘logout message’ will be returned with status of
‘success’, then the connection will be closed by the server and the thread terminates.
If the client has sent a ‘login message’, the server will accept ‘text message’ messages.
On receipt of ‘text message’, the text of text messages will be changed to all caps. The
capitalized message is then returned to the client in a new ‘text’ message.
Client Object:
Client connects to a listening server object. On connection, pass a ‘login message’ to
the server. The server will return a login message with a status of ‘success’. Only after
login, the client prompts the user for text to send to the server. Text is sent using a ‘text
message’. On receipt of ‘text message’ from server, display the text field to the user. If
the user enters, ‘logout’, the client sends a ‘logout message’ to the server.
Deliverables
Submit your java source files and screenshots of your program operations. Submit only
your own original work.
