package org.sample;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
public class SoftBenchmarj {
    private static final int N = 1000;
    private static final List<Integer> testData = new ArrayList<Integer>();

    @Setup
    public static final void setup() {
        Random randomGenerator = new Random();
        for (int i = 0; i < N; i++)
            testData.add(randomGenerator.nextInt(Integer.MAX_VALUE));
        System.out.println("Setup Complete");
    }

   // @Benchmark
    public List<Integer> classicSort() {
        List<Integer> copy = new ArrayList<Integer>(testData);
        Collections.sort(copy);
        return copy;
    }

   // @Benchmark
    public List<Integer> standardSort() {
        return testData.stream().sorted().collect(Collectors.toList());
    }

    public static void main(String[] args) throws RunnerException {
        Options optionsBuilder = new OptionsBuilder()
                .include(SoftBenchmarj.class.getSimpleName())
                .warmupIterations(10)
                .measurementIterations(5).forks(1)
                .jvmArgs("-server", "-Xms2048m", "-Xmx2048m")
                .addProfiler(GCProfiler.class)
                .addProfiler(StackProfiler.class)
                .build();
        new Runner(optionsBuilder).run();
    }

    @Benchmark
    public void test() {
        for(Integer i: testData) {
            int k = i + 1 ;
        }
    }

    @Benchmark
    public void test2() {
        for(Integer i: testData) {
            Integer k = i + 1 ;
        }
    }

    @Benchmark
    public void test3() {
        for(Integer i: testData) {
            Integer k = i + Integer.valueOf(1) ;
        }
    }

}
