add_library(get_cpu_time get_cpu_time.cpp get_cpu_time.h )

target_include_directories(get_cpu_time PUBLIC ./)

target_link_libraries(get_cpu_time)