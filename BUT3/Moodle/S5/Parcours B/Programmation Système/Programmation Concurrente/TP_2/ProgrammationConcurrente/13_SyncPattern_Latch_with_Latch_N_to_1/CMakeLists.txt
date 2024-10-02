
add_executable(13_SyncPattern_Latch_with_Latch_N_to_1
  Source_13.cpp
  )

if (UNIX)
  target_link_options(13_SyncPattern_Latch_with_Latch_N_to_1 PUBLIC -pthread)
endif()
