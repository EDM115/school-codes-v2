
add_executable(20_SyncPattern_ReaderWriter_lock
  Source_20.cpp
  )

if (UNIX)
  target_link_options(20_SyncPattern_ReaderWriter_lock PUBLIC -pthread)
endif()
