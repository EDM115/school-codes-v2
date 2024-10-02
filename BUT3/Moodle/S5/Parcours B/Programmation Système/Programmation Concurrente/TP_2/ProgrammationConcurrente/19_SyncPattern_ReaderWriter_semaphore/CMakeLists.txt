
add_executable(19_SyncPattern_ReaderWriter_semaphore
  Source_19.cpp
  )

if (UNIX)
  target_link_options(19_SyncPattern_ReaderWriter_semaphore PUBLIC -pthread)
endif()
