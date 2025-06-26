package jmhTest;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

// 统计平均响应时间
@BenchmarkMode(Mode.AverageTime)
// 每个进行基准测试的线程都会独享一个对象示例
@State(Scope.Thread)
// 表示开启一个线程进行测试
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// 微基准测试前进行三次预热执行
@Warmup(iterations = 3)
// 进行 5 次微基准测试
@Measurement(iterations = 5)
public class JmhTest {

    String string = "";
    StringBuilder stringBuilder = new StringBuilder();

    // 表示这个方法是要进行基准测试的方法
    @Benchmark
    public String stringAdd() {
        for (int i = 0; i < 1000; i++) {
            string = string + i;
        }
        return string;
    }

    @Benchmark
    public String stringBuilderAppend() {
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append(i);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JmhTest.class.getSimpleName())
                .build();
        new Runner(opt).run();
    }
}