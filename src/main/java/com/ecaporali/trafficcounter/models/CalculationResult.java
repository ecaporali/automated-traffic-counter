package com.ecaporali.trafficcounter.models;

import java.util.List;

import static com.ecaporali.trafficcounter.utils.AssertUtils.checkNonNull;

final public class CalculationResult {

    private final int totalCarsCount;
    private final List<LogCounter> totalCarsPerDay;
    private final List<LogCounter> topNHalfHours;
    private final ContiguousLogCounter contiguousNLeastHalfHours;

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
        private ContiguousLogCounter contiguousNLeastHalfHours;

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

        public Builder withContiguousNLeastHalfHours(ContiguousLogCounter contiguousNLeastHalfHours) {
            this.contiguousNLeastHalfHours = contiguousNLeastHalfHours;
            return this;
        }

        public CalculationResult build() {
            checkNonNull(this.totalCarsPerDay, "totalCarsPerDay must not be null");
            checkNonNull(this.topNHalfHours, "topNHalfHours must not be null");
            checkNonNull(this.contiguousNLeastHalfHours, "contiguousNLeastHalfHours must not be null");
            return new CalculationResult(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n---------------------------------\n");
        sb.append(totalCarsCount);
        sb.append("\n---------------------------------\n");
        totalCarsPerDay.forEach(logCounter -> sb.append(logCounter.toStringWithDate()).append("\n"));
        sb.append("---------------------------------\n");
        topNHalfHours.forEach(logCounter -> sb.append(logCounter.toStringWithDateTime()).append("\n"));
        sb.append("---------------------------------\n");
        sb.append(contiguousNLeastHalfHours).append("\n");
        sb.append("---------------------------------");
        return sb.toString();
    }
}
