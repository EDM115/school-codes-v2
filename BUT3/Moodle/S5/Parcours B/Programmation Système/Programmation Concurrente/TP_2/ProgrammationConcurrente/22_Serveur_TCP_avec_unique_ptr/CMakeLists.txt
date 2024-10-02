if(UNIX)
	add_compile_definitions(UNIX)
endif()

add_executable(22_Serveur_TCP_avec_unique_ptr Source_22.cpp)
target_link_libraries(22_Serveur_TCP_avec_unique_ptr PUBLIC sfml-system sfml-network)
