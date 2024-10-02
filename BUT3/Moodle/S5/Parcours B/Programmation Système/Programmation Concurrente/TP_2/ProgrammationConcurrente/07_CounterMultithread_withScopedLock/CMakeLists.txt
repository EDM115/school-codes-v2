
add_executable(07_CounterMultithread_withScopedLock
  Source_07.cpp
  )

target_link_libraries(07_CounterMultithread_withScopedLock PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(07_CounterMultithread_withScopedLock PUBLIC -pthread)
endif()
