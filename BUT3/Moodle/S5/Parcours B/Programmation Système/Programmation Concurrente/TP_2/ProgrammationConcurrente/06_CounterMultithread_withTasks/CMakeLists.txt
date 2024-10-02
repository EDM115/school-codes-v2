
add_executable(06_CounterMultithread_withTasks
  Source_06.cpp
  )

target_link_libraries(06_CounterMultithread_withTasks PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(06_CounterMultithread_withTasks PUBLIC -pthread)
endif()
