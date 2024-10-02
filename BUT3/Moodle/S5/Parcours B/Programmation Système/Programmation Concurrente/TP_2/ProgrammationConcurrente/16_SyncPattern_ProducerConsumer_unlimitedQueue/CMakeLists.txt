
add_executable(16_SyncPattern_ProducerConsumer_unlimitedQueue
  Source_16.cpp
  )

if (UNIX)
  target_link_options(16_SyncPattern_ProducerConsumer_unlimitedQueue PUBLIC -pthread)
endif()
