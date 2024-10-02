if(UNIX)
	add_compile_definitions(UNIX)
endif()

add_executable(21_Serveur_TCP_avec_shared_ptr Source_21.cpp)
target_link_libraries(21_Serveur_TCP_avec_shared_ptr PUBLIC sfml-system sfml-network)
