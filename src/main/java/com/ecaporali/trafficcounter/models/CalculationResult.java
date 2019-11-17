package com.ecaporali.trafficcounter.models;

import java.util.List;
import java.util.Objects;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkCondition;
import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;

final public class CalculationResult {

    private final int totalCarsCount;
    private final List<LogCounter> totalCarsPerDay;
    private final List<LogCounter> topNHalfHours;
    private final List<ContiguousLogCounter> contiguousNLeastHalfHours;

    private CalculationResult(Builder builder) {
        this.totalCarsCount = builder.totalCarsCount;
        this.totalCarsPerDay = builder.totalCarsPerDay;
        this.topNHalfHours = builder.topNHalfHours;
        this.contiguousNLeastHalfHours = builder.contiguousNLeastHalfHours;
    }

    public static class Builder {
        private int totalCarsCount;
        private List<LogCounter> totalCarsPerDay;
        private List<LogCounter> topNHalfHours;
        private List<ContiguousLogCounter> contiguousNLeastHalfHours;

        public static Builder newInstance() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder withTotalCarsCount(int totalCarsCount) {
            this.totalCarsCount = totalCarsCount;
            return this;
        }

        public Builder withTotalCarsPerDay(List<LogCounter> totalCarsPerDay) {
            this.totalCarsPerDay = totalCarsPerDay;
            return this;
        }

        public Builder withTopNHalfHours(List<LogCounter> topThreeHalfHours) {
            this.topNHalfHours = topThreeHalfHours;
            return this;
        }

        public Builder withContiguousNLeastHalfHours(List<ContiguousLogCounter> contiguousNLeastHalfHours) {
            this.contiguousNLeastHalfHours = contiguousNLeastHalfHours;
            return this;
        }

        public CalculationResult build() {
            checkNonNull(this.totalCarsPerDay, "CalculationResult.Builder.build", "totalCarsPerDay must not be null");
            checkNonNull(this.topNHalfHours, "CalculationResult.Builder.build", "topNHalfHours must not be null");
            checkNonNull(this.contiguousNLeastHalfHours, "CalculationResult.Builder.build", "contiguousNLeastHalfHours must not be null");
            checkCondition(contiguousNLeastHalfHours.size() > 1, "CalculationResult.Builder.build", "contiguousNLeastHalfHours must not contain more than one element");
            return new CalculationResult(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n----------------------\n");
        sb.append(totalCarsCount);
        sb.append("\n----------------------\n");
        totalCarsPerDay.forEach(logCounter -> sb.append(logCounter.toStringWithDate()).append("\n"));
        sb.append("----------------------\n");
        topNHalfHours.forEach(logCounter -> sb.append(logCounter.toStringWithDateTime()).append("\n"));
        sb.append("----------------------\n");
        contiguousNLeastHalfHours.forEach(contiguousLogCounter -> sb.append(contiguousLogCounter).append("\n"));
        sb.append("----------------------");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationResult that = (CalculationResult) o;
        return totalCarsCount == that.totalCarsCount &&
                totalCarsPerDay.equals(that.totalCarsPerDay) &&
                topNHalfHours.equals(that.topNHalfHours) &&
                contiguousNLeastHalfHours.equals(that.contiguousNLeastHalfHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCarsCount, totalCarsPerDay, topNHalfHours, contiguousNLeastHalfHours);
    }
}
