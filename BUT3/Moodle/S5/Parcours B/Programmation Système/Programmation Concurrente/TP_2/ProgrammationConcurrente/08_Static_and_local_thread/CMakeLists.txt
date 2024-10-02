
add_executable(08_CounterMultithread_withScopedLock_and_staticMutex
  Source_08.cpp
  )

target_link_libraries(08_CounterMultithread_withScopedLock_and_staticMutex PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(08_CounterMultithread_withScopedLock_and_staticMutex PUBLIC -pthread)
endif()
