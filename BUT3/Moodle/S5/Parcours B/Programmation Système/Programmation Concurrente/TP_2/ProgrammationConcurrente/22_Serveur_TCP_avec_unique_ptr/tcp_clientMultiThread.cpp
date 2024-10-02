#ifdef UNIX
// pour gethostname
#include <unistd.h>
// pour getpid()
#include <sys/types.h>
#include <unistd.h>
#define GETPID getpid
#else
// pour gethostname (NB : requiert librairie Ws2_32.lib ou DLL Ws2_32.dll
#include <winsock2.h>
// pour _getpid ()
#include <process.h>  
#define GETPID _getpid
#endif

#include <iostream>
#include <sstream>
#include <thread>
#include <memory>

#include <SFML/Network.hpp>
#include <SFML/System.hpp> // Pour sf::sleep

#include "common.h"

using namespace std;

const int DEFAULT_NB_MESSAGES(60);

static sf::Int32 clientId;

void messageManager(shared_ptr<sf::TcpSocket> pSocket) {
	sf::Packet packet;
	while (pSocket->receive(packet) == sf::Socket::Done) {
		sf::Int32 msgType;
		packet >> msgType;
		ServerMsgType messageType = static_cast<ServerMsgType>(msgType);
		switch (messageType) {
		case ServerMsgType::Broadcast:
		{
			sf::Int32 senderClientId;
			sf::Int32 i;
			packet >> senderClientId >> i;
			cout << "Client '" << senderClientId << "' has broadcast (thanks to server) integer: " << i << endl;
			break;
		}
		case ServerMsgType::ClientIdResponse:
		{
			packet >> clientId;
			cout << "Received my clientId from server. It is '" << clientId << "'" << endl;
			break;
		}
		default:
			cerr << "ERROR: Received from server unknown messageType:'" << static_cast<int>(messageType) << "'" << endl;
			exit(EXIT_FAILURE);
		}
	}

	cerr << "Server disconnected ==> Client terminates" << endl;
	exit(EXIT_FAILURE);
}

void usage(string_view progName)
{
	cout << progName << " [nbMessages [host [port]]]" << endl;
	cout << "\tnbMessages: Number of messages to send to server for broadcast (" << DEFAULT_NB_MESSAGES << " by default)" << endl;
	cout << "\thost: Host where server is located (" << DEFAULT_HOST << " by default)" << endl;
	cout << "\tport: Port on which to contact server (" << DEFAULT_PORT << " by default)" << endl;
}

int main(int argc, char* argv[])
{
	if (argc > 4)
	{
		usage(argv[0]);
		return EXIT_FAILURE;
	}

	unsigned short port(DEFAULT_PORT);
	if (argc > 3)
	{
		istringstream iss(argv[3]);
		iss >> port;
		if (!iss)
		{
			usage(argv[0]);
			return EXIT_FAILURE;
		}
	}

	string_view host(DEFAULT_HOST.data());
	if (argc > 2)
	{
		host = argv[2];
	}

	int nbMessages(DEFAULT_NB_MESSAGES);
	if (argc > 1)
	{
		std::istringstream iss(argv[1]);
		iss >> nbMessages;
		if (!iss)
		{
			usage(argv[0]);
			return EXIT_FAILURE;
		}
	}

	char hostname[64];
	int rc = gethostname(hostname, sizeof(hostname));
	if (rc < 0) {
		cerr << "Erreur lors de l'execution de gethostname()" << endl;
		return EXIT_FAILURE;
	}
	ostringstream buffer;
	buffer << "host=" << hostname << "/pid=" << GETPID();
	string clientName(buffer.str());

	cout << "My clientName is '" << clientName << "'" << endl;

	auto pSocket = make_shared<sf::TcpSocket>();
	cout << "Connect to " << host << ":" << port << endl;
	sf::Socket::Status status = pSocket->connect(host.data(), port);
	if (status != sf::Socket::Done) {
		cerr << "Error: Problem during connect (status = " << status << ") :" << endl;
		cerr << "\t- server is probably not running;" << endl;
		cerr << "\t- or the server you are trying to connect to is *not* the right one;" << endl;
		cerr << "\t- or the port you are trying to connect to is *not* the one listened by the server." << endl;
		exit(EXIT_FAILURE);
	}

	// Fork thread to handle messages received on this connection
	std::thread t(messageManager, pSocket);
	t.detach();

	// Send our clientId to the server
	sf::Packet packet;
	packet << static_cast<int>(ClientMsgType::ClientIdRequest);
	if (pSocket->send(packet) != sf::Socket::Done) {
		cerr << "Error: Problem during send of CLIENT_ID" << endl;
		exit(EXIT_FAILURE);
	}
	sf::sleep(sf::milliseconds(1000));

	// Ask server to broadcast integers.
	cout << "Broadcasting " << nbMessages << " messages (at frequency of 1 message per second)" << endl;
	for (int i = 1; i <= nbMessages; ++i) {
		cout << "Request to broadcast integer: " << i << endl;
		sf::Packet packet;
		packet << static_cast<int>(ClientMsgType::IntToBroadcast) << i;
		if (pSocket->send(packet) != sf::Socket::Done) {
			cerr << "Error: Problem during send of Client::IntToBroadcast" << endl;
			exit(EXIT_FAILURE);
		}

		sf::sleep(sf::milliseconds(1000));
	}
	cout << "Done" << endl;
}
