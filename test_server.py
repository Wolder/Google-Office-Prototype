import socket
import sys
import json

jsondata = {}


sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

ip = '127.0.0.1'
port = 8080

server_address = (ip, port)

print('Starting up: ' + str(ip) + ':' + str(port))
sock.bind(server_address)

# Listen for incoming connections
sock.listen(1)



def RESTcall(input):
    stringArray = str(input).split(":")
    action = stringArray[0]
    roomName = stringArray[1]
    valueToAction = stringArray[2]
    deviceUID = stringArray[3]
    if (action == "SET"):
        deviceValue = stringArray[4]


    with open('office.json') as json_file:
        jsondata = json.load(json_file)
        for room in jsondata["rooms"]:
            if (str(room["roomname"]) == roomName):
                for device in room["devices"]:
                    if (str(device[str(valueToAction)]) == deviceUID):
                        if (action == "SET"):
                            device["value"] = int(deviceValue)
                            connection.sendall((deviceUID + " is now " + deviceValue).encode())
                        if (action == "GET"):
                            connection.sendall(str(device["value"]).encode())
                        if (action == "DEL"):
                            del device
                            connection.sendall((deviceUID + " is now deleted").encode())

    with open('office.json', 'w') as outfile:
        json.dump(jsondata, outfile)

def JSONcall(input):
    stringArray = str(input).split(":JSON:")
    action = stringArray[0].replace(" ", "")
    content = stringArray[1]

    print(action)

    if ("GET" in action):
        with open('office.json') as json_file:
            jsondata = json.load(json_file)
            connection.sendall(str(jsondata).encode())
            print (sys.stderr, 'Sending "%s"' % str(jsondata))

    if ("SET" in action):
        with open('office.json', 'w') as outfile:
            json.dump(content, outfile)
            connection.sendall(("JSON Updated").encode())
            
while True:
    # Wait for a connection
    print ('Waiting for Connection')
    connection, client_address = sock.accept()

    try:
        print ('connection from' + str(client_address))
        input = ""

        while True:
            data = connection.recv(1024)
            print (sys.stderr, 'Received "%s"' % data)
            if data:
                if ("JSON" in data.decode()):
                    JSONcall(data.decode())
                elif (("GET" or "SET" or "DEL") in data.decode()):
                    RESTcall(input)
                else:
                    connection.sendall("NAN")
            else:
                print (sys.stderr, 'No more data from', client_address)
                break

        jsondata = {}

    finally:
        # Clean up the connection
        print(sys.stderr, 'Connection closed to', client_address)
        connection.close()

