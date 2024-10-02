
add_executable(11_SyncPattern_Latch_with_Condition_N_to_1
  Source_11.cpp
  )

if (UNIX)
  target_link_options(11_SyncPattern_Latch_with_Condition_N_to_1 PUBLIC -pthread)
endif()
