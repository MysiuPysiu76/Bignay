package com.mysiupysiu.bignay.config;

public class IntOption extends ConfigOption<Integer> {

    private final int min;
    private final int max;

    private IntOption(String name, String comment, String translation, Integer defaultValue, int min, int max) {
        super(name, comment, translation, defaultValue);
        this.min = min;
        this.max = max;
        this.value = clamp(defaultValue);
    }

    @Override
    public void set(Integer newValue) {
        super.set(clamp(newValue));
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    private int clamp(int val) {
        return Math.max(min, Math.min(max, val));
    }

    public static class Builder extends BaseBuilder<Integer, Builder> {
        private int min = Integer.MIN_VALUE;
        private int max = Integer.MAX_VALUE;

        public Builder(String name, Integer defaultValue) {
            super(name, defaultValue);
        }

        public Builder range(int min, int max) {
            this.min = min;
            this.max = max;
            return this;
        }

        @Override
        public IntOption build() {
            return new IntOption(name, comment, translation, defaultValue, min, max);
        }
    }
}
