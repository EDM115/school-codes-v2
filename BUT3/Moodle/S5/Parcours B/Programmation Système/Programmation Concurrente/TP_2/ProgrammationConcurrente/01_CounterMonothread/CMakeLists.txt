
add_executable(01_CounterMonothread
  Source_01.cpp
  )

target_link_libraries(01_CounterMonothread PUBLIC get_cpu_time)