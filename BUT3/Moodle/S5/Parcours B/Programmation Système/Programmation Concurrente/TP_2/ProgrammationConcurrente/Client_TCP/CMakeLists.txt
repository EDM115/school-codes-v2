if(UNIX)
	add_compile_definitions(UNIX)
endif()

add_executable(tcp_clientMultiThread tcp_clientMultiThread.cpp)
target_link_libraries(tcp_clientMultiThread PUBLIC sfml-system sfml-network)
