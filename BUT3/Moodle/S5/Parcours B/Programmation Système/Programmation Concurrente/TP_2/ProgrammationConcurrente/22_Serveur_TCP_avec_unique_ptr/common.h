#pragma once
#include <string>

// Where the server listens by default
const std::string_view DEFAULT_HOST("localhost");
const unsigned short DEFAULT_PORT(4096);

// Type of messages

// Packets originated in the server
enum class ServerMsgType
{
	Broadcast,
	ClientIdResponse,
	newClient
};

// Packets originated in the client
enum class ClientMsgType
{
	ClientIdRequest,
	IntToBroadcast
};

