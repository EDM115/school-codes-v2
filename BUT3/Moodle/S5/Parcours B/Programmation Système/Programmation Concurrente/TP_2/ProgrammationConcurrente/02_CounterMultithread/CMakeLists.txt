
add_executable(02_CounterMultithread
  Source_02.cpp
  )

target_link_libraries(02_CounterMultithread PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(02_CounterMultithread PUBLIC -pthread)
endif()
