
add_executable(04_CounterMultithread_withAtomic
  Source_04.cpp
  )

target_link_libraries(04_CounterMultithread_withAtomic PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(04_CounterMultithread_withAtomic PUBLIC -pthread)
endif()
