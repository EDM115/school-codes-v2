import os
import time
from time import process_time

nb_count = 100000000

counter = nb_count

start = time.time()
start_cpu = process_time()
i = nb_count
while i > 0 :
    counter -= 1
    i -= 1
stop_cpu = process_time()
stop = time.time()

print('counter = ', counter)
print('(elapsed time =', (stop - start) * 1000000, 'microseconds ; CPU time =', (stop_cpu - start_cpu) * 1000000, 'microseconds)')
