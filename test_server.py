import socket
import sys

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ip = '127.0.0.1'
port = 65432

server_address = (ip, port)

print('Starting up: ' + str(ip) + ':' + str(port))
sock.bind(server_address)


# Listen for incoming connections
sock.listen(1)

while True:
    # Wait for a connection
    print ('Waiting for Connection')
    connection, client_address = sock.accept()

    try:
        print ('connection from' + str(client_address))

        # Receive the data in small chunks and retransmit it
        while True:
            data = connection.recv(32)
            print (sys.stderr, 'Received "%s"' % data)
            if data:
                print (sys.stderr, 'Sending data back to the client')
                connection.sendall(data)
            else:
                print (sys.stderr, 'No more data from', client_address)
                break
            
    finally:
        # Clean up the connection
        connection.close()