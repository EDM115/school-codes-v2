#include <iostream>
#include <sstream>
#include <thread>
#include <vector>
#include <mutex>
#include <memory>

#include <SFML/Network.hpp>

#include "common.h"

using namespace std;

static vector<shared_ptr<sf::TcpSocket>> vecClientSock;

static mutex mutexVec;

void clientManager(shared_ptr<sf::TcpSocket> pClientSock)
{
	size_t posClientSockInVector;
	{
		lock_guard<mutex> guard(mutexVec);
		vecClientSock.push_back(pClientSock);
		posClientSockInVector = vecClientSock.size() - 1;
	}
	sf::Int32 clientId = posClientSockInVector;

	sf::Packet packet;
	while (pClientSock->receive(packet) == sf::Socket::Done) {
		sf::Int32 msgType;
		packet >> msgType;
		ClientMsgType messageType = static_cast<ClientMsgType>(msgType);
		switch (messageType) {
		case ClientMsgType::ClientIdRequest:
			cout << "Client '" << clientId << "' is connected and requesting its clientId" << endl;
			{
				sf::Packet packetToSend;
				packetToSend << static_cast<sf::Int32>(ServerMsgType::ClientIdResponse) << clientId;
				pClientSock->send(packetToSend);
			}
			break;
		case ClientMsgType::IntToBroadcast:
			sf::Int32 i;
			packet >> i;
			cout << "Client '" << clientId << "' requests to broadcast int \"" << i << "\"" << endl;
			{
				sf::Packet packetToSend;
				packetToSend << static_cast<sf::Int32>(ServerMsgType::Broadcast) << clientId << i;

				lock_guard<mutex> guard(mutexVec);
				for (auto && sock : vecClientSock)
					sock->send(packetToSend);
			}
			break;
		default:
			std::cerr << "Received unknown message type '" << static_cast<int>(messageType) << "' from client '" << clientId<< "'" << endl;
			exit(1);
		}
	}

	std::cerr << "Client '" << clientId << "' disconnected" << endl;
	{
		lock_guard<mutex> guard(mutexVec);
		vecClientSock.erase(vecClientSock.begin() + posClientSockInVector);
	}

}

void usage(string_view progName)
{
	cout << progName << " [port]" << endl;
	cout << "\tport: Port on which to listen (" << DEFAULT_PORT << " by default)" << endl;
}

int main(int argc, char** argv)
{
	if (argc > 2)
	{
		usage(argv[0]);
		return EXIT_FAILURE;
	}

	unsigned short port(DEFAULT_PORT);
	if (argc > 1)
	{
		istringstream iss(argv[1]);
		iss >> port;
		if (!iss)
		{
			usage(argv[0]);
			return EXIT_FAILURE;
		}	
	}

	sf::TcpListener listener;

	// Listen to port
	if (listener.listen(port) != sf::Socket::Done) {
		std::cerr << "Error: Problem during listen (probably, there is already another server running)" << std::endl;
		exit(1);
	}

	// Wait for a new connection
	std::cerr << "Waiting for connections on port " << port << std::endl;
	while (1) {
		auto pClientSock = make_shared<sf::TcpSocket>();
		if (listener.accept(*pClientSock) != sf::Socket::Done) {
			std::cerr << "Error: Problem during accept" << std::endl;
			exit(1);
		}

		// Fork thread to handle this clien's connection
		std::thread t(clientManager, pClientSock);
		t.detach();
	}

}
