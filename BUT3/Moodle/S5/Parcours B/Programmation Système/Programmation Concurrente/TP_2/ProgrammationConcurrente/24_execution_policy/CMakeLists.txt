
add_executable(24_execution_policy
  Source_24.cpp
  )

if (UNIX)
  target_link_options(24_execution_policy PUBLIC -pthread)
endif()
