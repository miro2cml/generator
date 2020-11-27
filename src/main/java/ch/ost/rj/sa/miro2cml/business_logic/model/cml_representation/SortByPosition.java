package ch.ost.rj.sa.miro2cml.business_logic.model.cml_representation;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class SortByPosition implements Comparator<FlowStep>
{
    @Override
    public int compare(FlowStep flowStep, FlowStep t1) {
        return (int) (flowStep.getPosition() - t1.getPosition());
    }

    @Override
    public Comparator<FlowStep> reversed() {
        return null;
    }

    @Override
    public Comparator<FlowStep> thenComparing(Comparator<? super FlowStep> other) {
        return null;
    }

    @Override
    public <U> Comparator<FlowStep> thenComparing(Function<? super FlowStep, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return null;
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<FlowStep> thenComparing(Function<? super FlowStep, ? extends U> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<FlowStep> thenComparingInt(ToIntFunction<? super FlowStep> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<FlowStep> thenComparingLong(ToLongFunction<? super FlowStep> keyExtractor) {
        return null;
    }

    @Override
    public Comparator<FlowStep> thenComparingDouble(ToDoubleFunction<? super FlowStep> keyExtractor) {
        return null;
    }
}
