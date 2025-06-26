package jmhTest;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime}) // 吞吐量 + 平均时间
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
public class MapBenchmark {

    @Param({"HashMap", "ConcurrentHashMap"})
    private String mapType;

    @Param({"100", "1000", "10000"})
    private int dataSize;

    private Map<Integer, Integer> map;
    private AtomicInteger counter;

    @Setup(Level.Iteration)
    public void setup() {
        switch (mapType) {
            case "HashMap":
                map = new HashMap<>();
                break;
            case "ConcurrentHashMap":
                map = new ConcurrentHashMap<>();
                break;
        }
        counter = new AtomicInteger();
        for (int i = 0; i < dataSize; i++) {
            map.put(i, i);
        }
    }

    @Benchmark
    @Threads(8)
    public void testRead() {
        int key = counter.getAndIncrement() % dataSize;
        map.get(key);
    }

    @Benchmark
    @Threads(4)
    public void testWrite() {
        int key = counter.getAndIncrement() % (dataSize * 2);
        map.put(key, key);
    }

    @Benchmark
    @Threads(2)
    public void testRemove() {
        int key = counter.getAndIncrement() % dataSize;
        map.remove(key);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MapBenchmark.class.getSimpleName())
                .warmupIterations(3)
                .measurementIterations(5)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(opt).run();
    }
}

