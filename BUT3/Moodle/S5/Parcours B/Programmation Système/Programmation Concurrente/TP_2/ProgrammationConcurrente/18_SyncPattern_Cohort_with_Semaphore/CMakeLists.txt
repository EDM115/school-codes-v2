
add_executable(18_SyncPattern_Cohort_with_Semaphore
  Source_18.cpp
  )

if (UNIX)
  target_link_options(18_SyncPattern_Cohort_with_Semaphore PUBLIC -pthread)
endif()
