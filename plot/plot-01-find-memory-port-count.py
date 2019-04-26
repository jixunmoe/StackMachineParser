#!/usr/bin/env python3

import matplotlib.pyplot as plt
import numpy as np


loop1, s1, s2, s3, s4 = [[
 1, 2093, 2.552,
 2, 1168, 1.424,
 3, 876, 1.068,
 4, 732, 0.893,
 5, 696, 0.849,
 6, 636, 0.776,
 7, 600, 0.732,
 8, 540, 0.659,
 9, 540, 0.659,
10, 540, 0.659,
], [
 1, 3231, 2.459,
 2, 2020, 1.537,
 3, 1720, 1.309,
 4, 1520, 1.157,
 5, 1520, 1.157,
 6, 1520, 1.157,
 7, 1520, 1.157,
 8, 1520, 1.157,
 9, 1520, 1.157,
10, 1520, 1.157,
], [
 1, 360, 2.000,
 2, 208, 1.156,
 3, 208, 1.156,
 4, 208, 1.156,
 5, 208, 1.156,
 6, 208, 1.156,
 7, 208, 1.156,
 8, 208, 1.156,
 9, 208, 1.156,
10, 208, 1.156,
], [
 1, 6024, 2.000,
 2, 3726, 1.237,
 3, 3372, 1.120,
 4, 3372, 1.120,
 5, 3372, 1.120,
 6, 3372, 1.120,
 7, 3372, 1.120,
 8, 3372, 1.120,
 9, 3372, 1.120,
10, 3372, 1.120,
], [
 1, 2925, 2.092,
 2, 1901, 1.360,
 3, 1651, 1.181,
 4, 1595, 1.141,
 5, 1595, 1.141,
 6, 1595, 1.141,
 7, 1595, 1.141,
 8, 1595, 1.141,
 9, 1595, 1.141,
10, 1595, 1.141,
]]

def extract2(x):
    data = np.array(x).reshape((10, 3))
    return data[:,2]

def extract(x):
    data = np.array(x).reshape((10, 3))
    col = data[:,2]
    before = col[:-1].reshape(9)
    after  = col[ 1:].reshape(9)
    return col[0] / after

start = 1
end = 10
fig = plt.figure(figsize=(6,3))
plt.plot(np.arange(start, end + 1), extract2(loop1), label='loop1')
plt.plot(np.arange(start, end + 1), extract2(s1), label='sample1')
plt.plot(np.arange(start, end + 1), extract2(s2), label='sample2')
plt.plot(np.arange(start, end + 1), extract2(s3), label='sample3')
plt.plot(np.arange(start, end + 1), extract2(s4), label='sample4')
plt.grid(which='both', alpha=0.2)
plt.legend()
plt.close(fig)

start = 2
end = 10
fig = plt.figure(figsize=(6,4))
plt.plot(np.arange(start, end + 1), extract(loop1), label='loop1')
plt.plot(np.arange(start, end + 1), extract(s1), label='sample1')
plt.plot(np.arange(start, end + 1), extract(s2), label='sample2')
plt.plot(np.arange(start, end + 1), extract(s3), label='sample3')
plt.plot(np.arange(start, end + 1), extract(s4), label='sample4')
plt.grid(which='both', alpha=0.2)
plt.legend()
plt.title('performance ratio compared against to 1 memory port\nwith infinity number of ALU and search depth')
plt.xlabel('number of memory ports')
plt.xticks(np.arange(2, 10, 1))
plt.xlim(2, 6)
fig.tight_layout()


plt.show()
