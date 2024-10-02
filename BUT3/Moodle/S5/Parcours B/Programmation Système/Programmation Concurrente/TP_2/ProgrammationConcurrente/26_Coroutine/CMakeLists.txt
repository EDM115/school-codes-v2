
add_executable(26_Coroutine
  Source_26.cpp
  )

if (UNIX)
  target_link_options(26_Coroutine PUBLIC -pthread)
endif()
