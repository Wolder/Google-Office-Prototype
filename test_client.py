import socket
import sys

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Connect the socket to the port where the server is listening
#server_address = ('192.168.0.106', 8080)
server_address = ('', 8080)
print (sys.stderr, 'connecting to %s port %s' % server_address)
sock.connect(server_address)

try:
    
    # Send data
    message = 'SET:CEO Office:UID:L11:22'.encode()
    print (sys.stderr, 'sending "%s"' % message)
    sock.sendall(message)

    # Look for the response
    amount_received = 0
    amount_expected = len(message)
    data_received = ""
    
    while amount_expected > amount_received:
        data = sock.recv(32)
        amount_received += len(data)
        data_received += str(data)

        #DO STUFF
        print (sys.stderr, 'received "%s"' % data)


        print(data_received)

finally:
    print (sys.stderr, 'closing socket')
    sock.close()