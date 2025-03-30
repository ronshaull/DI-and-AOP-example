import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
public class Logging {
    private long startTime;
    private Map<String, Integer> times = new HashMap<>();
    private Map<String, Integer> executionCountMap = new HashMap<>();

    @Pointcut("execution(* SortingAlgorithm+.sort(..))")
    public void sortingMethod() {
    }

    // 2 before, one counting number of execution and one counting time.
    @Before("sortingMethod()")
    public void BeforeMethod() {
        startTime = System.currentTimeMillis();
    }

    // Before advice to count method executions
    @Before("sortingMethod()")
    public void countExecution(JoinPoint.StaticPart staticPart) {
        String className = staticPart.getSignature().getDeclaringType().getSimpleName();
        executionCountMap.put(className, executionCountMap.getOrDefault(className, 0) + 1);
    }

    @After("sortingMethod()")
    public void AfterMethod(JoinPoint.StaticPart staticPart) {
        String className = staticPart.getSignature().getDeclaringType().getSimpleName();
        long endTime = System.currentTimeMillis();
        times.put(className, times.getOrDefault(className, 0) + (int) (endTime - startTime));
    }

    // Pointcut for the runAlgorithms method in the AlgorithmRunner class
    @Pointcut("execution(* AlgorithmRunner.runAlgorithms(..))")
    public void runAlgorithmsMethod() {
    }

    @After("runAlgorithmsMethod()")
    public void logAfterMethod2() {
        AtomicInteger totalRunTime = new AtomicInteger();
        times.forEach((key, value) -> {
            totalRunTime.addAndGet(value);
        });
        System.out.println("Total time of running all sort functions was  " + totalRunTime + " ms");
        System.out.println("In detail:");
        executionCountMap.forEach((key, value) -> {
            System.out.println("Function sort in " + key + " ran " + value + " times and took in total " + times.getOrDefault(key, 0) + " ms");
        });
    }
}
