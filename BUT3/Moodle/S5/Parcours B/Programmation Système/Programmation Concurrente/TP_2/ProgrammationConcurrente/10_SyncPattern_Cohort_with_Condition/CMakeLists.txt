
add_executable(10_SyncPattern_Cohort_with_Condition
  Source_10.cpp
  )

if (UNIX)
  target_link_options(10_SyncPattern_Cohort_with_Condition PUBLIC -pthread)
endif()
