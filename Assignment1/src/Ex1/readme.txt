To run the benchmark, run main in the Benchmark class. Output on my machine:

Intel(R) Core(TM) i7-4750HQ CPU @ 2.00GHz

m: 1, n: 1, i: 1000000 --> NoSync: 1 / Sync: 1.484210 / ReentrantLock: 2.374070
m: 2, n: 2, i: 1000000 --> NoSync: 1 / Sync: 2.123938 / ReentrantLock: 1.818682
m: 4, n: 4, i: 1000000 --> NoSync: 1 / Sync: 3.023165 / ReentrantLock: 2.502648
m: 8, n: 8, i: 1000000 --> NoSync: 1 / Sync: 2.585235 / ReentrantLock: 2.533843
