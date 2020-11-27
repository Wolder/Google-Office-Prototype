import socket
import sys
import json

jsondata = {}


sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

<<<<<<< HEAD
ip = ''
port = 8080
=======

ip = ''
port = 8080

>>>>>>> e4fd3322d88704828f8a0b0a91b5e39bec7c8a1a

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
    deviceValue = stringArray[4]

    print(action + " : " + roomName + " : " + valueToAction + " : " + deviceUID + " : " + deviceValue)


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
                            connection.sendall(device["value"].encode())
                        if (action == "DEL"):
                            del device
                            connection.sendall((deviceUID + " is now deleted").encode())

    with open('office.json', 'w') as outfile:
        json.dump(jsondata, outfile)

def JSONcall(input):
    stringArray = str(input).split(":")
    action = stringArray[0]
    content = stringArray[1]

    if (action == "GET"):
        with open('office.json') as json_file:
            jsondata = json.load(json_file)
            connection.sendall(jsondata)

    if (action == "SET"):
        with open('office.json', 'w') as outfile:
            json.dump(content, outfile)
            connection.sendall("JSON Updated")


while True:
    # Wait for a connection
    print ('Waiting for Connection')
    connection, client_address = sock.accept()

    try:
        print ('connection from' + str(client_address))
        input = ""

        while True:
            data = connection.recv(16).decode()
            print (sys.stderr, 'Received "%s"' % data)
            if data:
                input = input + str(data)
                #if (len(str(data)) < 16):
                #    break
            else:
                print (sys.stderr, 'No more data from', client_address)
                break

        if ("JSON" in input):
            print("TODO")
        else:
            RESTcall(input)
        
        jsondata = {}

    finally:
        # Clean up the connection
        print(sys.stderr, 'Connection closed to', client_address)
        connection.close()

