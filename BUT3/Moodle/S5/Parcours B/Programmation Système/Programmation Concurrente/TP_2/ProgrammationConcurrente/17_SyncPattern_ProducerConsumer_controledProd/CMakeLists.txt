
add_executable(17_SyncPattern_ProducerConsumer_controledProd
  Source_17.cpp
  )

if (UNIX)
  target_link_options(17_SyncPattern_ProducerConsumer_controledProd PUBLIC -pthread)
endif()
