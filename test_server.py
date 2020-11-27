import socket
import sys
import json

jsondata = {}


sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


ip = ''
port = 8080


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

        while True:
            data = connection.recv(32)
            print (sys.stderr, 'Received "%s"' % data)
            if data:
                stringArray = str(data).split("b'")[1].split("'")[0].split(":")
                action = stringArray[0]
                roomName = stringArray[1]
                valueToAction = stringArray[2]
                deviceUID = stringArray[3]
                deviceValue = stringArray[4]

                print()

                with open('office.json') as json_file:
                    jsondata = json.load(json_file)
                    for room in jsondata["rooms"]:
                        if (str(room["roomname"]) == roomName):
                            for device in room["devices"]:
                                if (str(device["UID"]) == deviceUID):
                                    print(str(device["UID"]))
                                    if (action == "SET"):
                                        device["value"] = int(deviceValue)
                                        connection.sendall((deviceUID + " is now " + deviceValue).encode())
                                    if (action == "GET"):
                                        connection.sendall(device["value"].encode())
                                    if (action == "DEL"):
                                        del device
                                        connection.sendall((deviceUID + " is now deleted").encode())

                with open('office.json', 'w') as outfile:
                    json.dump(jsondata, outfile)
                

                print (sys.stderr, 'Sending data back to the client')
                connection.sendall(data)
            else:
                print (sys.stderr, 'No more data from', client_address)
                break
            
    finally:
        # Clean up the connection
        connection.close()



