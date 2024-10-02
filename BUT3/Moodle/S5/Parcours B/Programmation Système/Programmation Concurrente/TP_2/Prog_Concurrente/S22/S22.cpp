#include <iostream>
#include <sstream>
#include <thread>
#include <vector>
#include <mutex>
#include <memory>

#include <SFML/Network.hpp>

#include "common.h"

using namespace std;

void clientManager(unique_ptr<sf::TcpSocket> pClientSock, vector<sf::TcpSocket*>& vecClientSock, mutex& mutexVec)
{
	size_t posClientSockInVector;
	{
		lock_guard<mutex> guard(mutexVec);
		vecClientSock.push_back(pClientSock.get());
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
				for (auto&& sock : vecClientSock)
					sock->send(packetToSend);
			}
			break;
		default:
			std::cerr << "Received unknown message type '" << static_cast<int>(messageType) << "' from client '" << clientId << "'" << endl;
			exit(1);
		}
	}

	std::cerr << "Client '" << clientId << "' disconnected" << endl;
	{
		// Le lock_guard ci-dessous genere un warning C26110 qui semble un bug d'analyse de Visual Studio
		// (cf. https://developercommunity.visualstudio.com/content/problem/670717/incorrect-lock-warnings-by-analyzer-c26110.html)
		lock_guard<mutex> guard(mutexVec);
		vecClientSock.erase(std::remove(vecClientSock.begin(), vecClientSock.end(), pClientSock.get()), vecClientSock.end());
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
	mutex mutexVec;
	vector<sf::TcpSocket*> vecClientSock;
	while (1) {
		auto pClientSock = make_unique<sf::TcpSocket>();
		if (listener.accept(*pClientSock) != sf::Socket::Done) {
			std::cerr << "Error: Problem during accept" << std::endl;
			exit(1);
		}

		// Fork thread to handle this clien's connection
		std::thread t(clientManager, std::move(pClientSock), std::ref(vecClientSock), std::ref(mutexVec));
		t.detach();
	}

}
