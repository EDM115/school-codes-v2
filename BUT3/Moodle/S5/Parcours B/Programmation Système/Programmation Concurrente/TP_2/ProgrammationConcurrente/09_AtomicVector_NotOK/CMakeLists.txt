
add_executable(09_AtomicVector_NotOK
  Source_09.cpp
  )

target_link_libraries(09_AtomicVector_NotOK PUBLIC get_cpu_time)

if (UNIX)
  target_link_options(09_AtomicVector_NotOK PUBLIC -pthread)
endif()
