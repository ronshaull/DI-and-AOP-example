import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

import java.util.Random;

// TODO: Add java classes (in separate files for annotations and aspects)
public class MainApp {

    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            AlgorithmRunner algorithmRunner = container.select(AlgorithmRunner.class).get();
            algorithmRunner.runAlgorithms();
        }
    }

    // TODO: Add producers
    public static class SortingAlgorithmProducer {
        @Inject
        private WeldContainer container;

        @Produces
        @Named("quadraticAlgorithm")
        public SortingAlgorithm<Integer> produceQuadraticAlgorithm() {
            return new BubbleSort(); // Replace with the actual quadratic sorting algorithm
        }

        @Produces
        @Named("nlognAlgorithm")
        public SortingAlgorithm<Integer> produceNlognAlgorithm() {
            return new QuickSort(); // Replace with the actual nlogn sorting algorithm
        }

        @Produces
        @Named("randomAlgorithm1")
        public SortingAlgorithm<Integer> produceRandomAlgorithm1() {
            return makeRandomSortingAlgorithm();
        }

        @Produces
        @Named("randomAlgorithm2")
        public SortingAlgorithm<Integer> produceRandomAlgorithm2() {
            return makeRandomSortingAlgorithm();
        }

        @Produces
        @Named("numberOfElements")
        public int produceNumberOfElements() {
            return new Random().nextInt(7000, 10001); // Replace with your desired number of elements or make it configurable
        }

        @Produces
        public SortingAlgorithm<Integer> makeRandomSortingAlgorithm() {
            Random random = new Random(System.currentTimeMillis());
            SortingAlgorithm<Integer> sortAlg = null;
            switch (random.nextInt(4)) {
                case 0:
                    sortAlg = container.select(QuickSort.class).get();
                    break;
                case 1:
                    sortAlg = container.select(MergeSort.class).get();
                    break;
                case 2:
                    sortAlg = container.select(BubbleSort.class).get();
                    break;
                case 3:
                    sortAlg = container.select(InsertionSort.class).get();
                    break;
            }
            return sortAlg;
        }
    }
}
