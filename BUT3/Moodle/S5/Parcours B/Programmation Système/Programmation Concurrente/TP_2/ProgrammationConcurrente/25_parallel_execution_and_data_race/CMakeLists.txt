
add_executable(25_parallel_execution_and_data_race
  Source_25.cpp
  )

if (UNIX)
  target_link_options(25_parallel_execution_and_data_race PUBLIC -pthread)
endif()
