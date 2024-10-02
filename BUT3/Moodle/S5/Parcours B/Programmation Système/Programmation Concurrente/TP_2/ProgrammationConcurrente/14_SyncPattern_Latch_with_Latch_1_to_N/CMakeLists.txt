
add_executable(14_SyncPattern_Latch_with_Latch_1_to_N
  Source_14.cpp
  )

if (UNIX)
  target_link_options(14_SyncPattern_Latch_with_Latch_1_to_N PUBLIC -pthread)
endif()
