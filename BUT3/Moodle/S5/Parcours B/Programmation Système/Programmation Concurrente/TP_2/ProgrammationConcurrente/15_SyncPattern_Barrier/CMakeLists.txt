
add_executable(15_SyncPattern_Barrier
  Source_15.cpp
  )

if (UNIX)
  target_link_options(15_SyncPattern_Barrier PUBLIC -pthread)
endif()
