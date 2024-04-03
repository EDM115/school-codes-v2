#!/usr/bin/env python

# Any copyright is dedicated to the Public Domain.
# https://creativecommons.org/publicdomain/zero/1.0/

# Written by Francois Fleuret <francois@fleuret.org>

import time
import torch

######################################################################
print('# 1 #')

m = torch.full((13, 13), 1.0, dtype = torch.int8)

m[3:5, 3:5] = 3
m[8:10, 3:5] = 3
m[3:5, 8:10] = 3
m[8:10, 8:10] = 3

m[:, 1::5] = 2
m[1::5, :] = 2

print(m)

######################################################################
print('# 2 #')

m = torch.empty(20, 20).normal_()
d = torch.diag(torch.arange(1, m.size(0)+1).float())
q = m.inverse().mm(d).mm(m)
v = torch.linalg.eig(q).eigenvalues

print('Eigenvalues', v[v.real.sort()[1]])

######################################################################
print('# 3 #')

d = 5000
a = torch.empty(d, d).normal_()
b = torch.empty(d, d).normal_()

time1 = time.perf_counter()
c = torch.mm(a, b)
time2 = time.perf_counter()

print('Throughput {:e} flop/s'.format((d * d * d)/(time2 - time1)))

######################################################################
print('# 4 #')

def mul_row(m):
    r = torch.torch.empty(m.size())
    for i in range(m.size(0)):
        for j in range(m.size(1)):
            r[i, j] = m[i, j] * (i + 1)
    return r

def mul_row_fast(m):
    c = torch.arange(1, m.size(0) + 1).view(-1, 1).float()
    return m.mul(c)

m = torch.empty(1000, 400).normal_()

time1 = time.perf_counter()
a = mul_row(m)
time2 = time.perf_counter()
b = mul_row_fast(m)
time3 = time.perf_counter()

print('Speed ratio', (time2 - time1) / (time3 - time2))

print('Sanity check: error is ', torch.norm(a - b).item())

######################################################################
