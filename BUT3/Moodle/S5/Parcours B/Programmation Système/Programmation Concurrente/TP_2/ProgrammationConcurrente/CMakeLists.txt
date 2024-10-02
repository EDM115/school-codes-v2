cmake_minimum_required(VERSION 3.15)
cmake_policy(VERSION 3.15)

include(FetchContent)
project(MultithreadExperiments VERSION 1.0.0 LANGUAGES CXX)

if(WIN32)
  # SonarLint support for CMake requires the following flag to be set on.
  #(see https://community.sonarsource.com/t/sonarlint-for-visual-studio-v4-38-support-cmake-projects/50127)
  set (CMAKE_EXPORT_COMPILE_COMMANDS ON)
endif()

set (BUILD_SHARED_LIBS FALSE)

# Get SFML
if(APPLE)
  find_package(SFML 2.5 COMPONENTS system window graphics network audio REQUIRED)
  include_directories(${SFML_inc_DIRS})
else()
  # Linux or Windows
  cmake_policy(SET CMP0135 NEW) # This cmake_policy avoids warning by cmake when we fetch contents based on URL
  FetchContent_Declare(
          sfml
          URL https://github.com/SFML/SFML/archive/refs/tags/2.6.1.tar.gz
  )
  FetchContent_MakeAvailable(sfml)
endif()

set(CMAKE_CXX_STANDARD 20)

add_subdirectory(get_cpu_time)

add_subdirectory(01_CounterMonothread)
add_subdirectory(02_CounterMultithread)
add_subdirectory(03_CounterMultithread_withMutex)
add_subdirectory(04_CounterMultithread_withAtomic)
add_subdirectory(05_CounterMultithread_withSharedNonMutable)
add_subdirectory(06_CounterMultithread_withTasks)
add_subdirectory(07_CounterMultithread_withScopedLock)
add_subdirectory(08_Static_and_local_thread)
add_subdirectory(09_AtomicVector_NotOK)
add_subdirectory(10_SyncPattern_Cohort_with_Condition)
add_subdirectory(11_SyncPattern_Latch_with_Condition_N_to_1)
add_subdirectory(12_SyncPattern_Latch_with_Condition_1_to_N)
add_subdirectory(13_SyncPattern_Latch_with_Latch_N_to_1)
add_subdirectory(14_SyncPattern_Latch_with_Latch_1_to_N)
add_subdirectory(15_SyncPattern_Barrier)
add_subdirectory(16_SyncPattern_ProducerConsumer_unlimitedQueue)
add_subdirectory(17_SyncPattern_ProducerConsumer_controledProd)
add_subdirectory(18_SyncPattern_Cohort_with_Semaphore)
add_subdirectory(19_SyncPattern_ReaderWriter_semaphore)
add_subdirectory(20_SyncPattern_ReaderWriter_lock)
include_directories(inc_Client_Serveur_TCP)
add_subdirectory(Client_TCP)
add_subdirectory(21_Serveur_TCP_avec_shared_ptr)
add_subdirectory(22_Serveur_TCP_avec_unique_ptr)
add_subdirectory(24_execution_policy)
add_subdirectory(25_parallel_execution_and_data_race)
add_subdirectory(26_Coroutine)
