
add_executable(03_CounterMultithread_withMutex
  Source_03.cpp
  )

target_link_libraries(03_CounterMultithread_withMutex PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(03_CounterMultithread_withMutex PUBLIC -pthread)
endif()
