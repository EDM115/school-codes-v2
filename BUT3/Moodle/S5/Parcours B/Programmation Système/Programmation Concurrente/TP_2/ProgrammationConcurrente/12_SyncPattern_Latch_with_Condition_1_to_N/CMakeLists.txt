
add_executable(12_SyncPattern_Latch_with_Condition_1_to_N
  Source_12.cpp
  )

if (UNIX)
  target_link_options(12_SyncPattern_Latch_with_Condition_1_to_N PUBLIC -pthread)
endif()
