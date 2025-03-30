import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Random;

@Dependent
// TODO: Add dependency injection and annotations to this file
public class AlgorithmRunner {
    @Inject
    @Named("quadraticAlgorithm")
    SortingAlgorithm<Integer> quadraticAlgorithm;

    @Inject
    @Named("nlognAlgorithm")
    SortingAlgorithm<Integer> nlognAlgorithm;

    @Inject
    @Named("randomAlgorithm1")
    SortingAlgorithm<Integer> randomAlgorithm1;

    @Inject
    @Named("randomAlgorithm2")
    SortingAlgorithm<Integer> randomAlgorithm2;

    @Inject
    @Named("numberOfElements")
    int numberOfElements;

    @Inject
    public void setNumberOfElements(@Named("numberOfElements") int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
    public void runAlgorithms() {
        Random rand = new Random();
        Integer[] ints = rand.ints(1, Integer.MAX_VALUE)
                .distinct()
                .limit(numberOfElements)
                .boxed()
                .toArray(Integer[]::new);
        Integer[] intsClone = ints.clone();
        quadraticAlgorithm.sort(intsClone);
        intsClone = ints.clone();
        nlognAlgorithm.sort(intsClone);
        intsClone = ints.clone();
        randomAlgorithm1.sort(intsClone);
        intsClone = ints.clone();
        randomAlgorithm2.sort(intsClone);
    }

}
