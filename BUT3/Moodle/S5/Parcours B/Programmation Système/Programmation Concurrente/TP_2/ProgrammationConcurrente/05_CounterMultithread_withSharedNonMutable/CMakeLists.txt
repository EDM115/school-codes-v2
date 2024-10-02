
add_executable(05_CounterMultithread_withSharedNonMutable
  Source_05.cpp
  )

target_link_libraries(05_CounterMultithread_withSharedNonMutable PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(05_CounterMultithread_withSharedNonMutable PUBLIC -pthread)
endif()
