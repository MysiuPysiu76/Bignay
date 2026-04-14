package com.mysiupysiu.bignay.config;

public class EnumOption<T extends Enum<T>> extends ConfigOption<T> {

    private final T[] values;

    private EnumOption(String name, String comment, String translation, T defaultValue) {
        super(name, comment, translation, defaultValue);
        this.values = defaultValue.getDeclaringClass().getEnumConstants();
    }

    public void cycle() {
        int nextOrdinal = (this.value.ordinal() + 1) % values.length;
        this.set(values[nextOrdinal]);
    }

    public T[] getValues() {
        return values;
    }

    public static class Builder<T extends Enum<T>> extends BaseBuilder<T, Builder<T>> {

        public Builder(String name, T defaultValue) {
            super(name, defaultValue);
        }

        @Override
        public EnumOption<T> build() {
            return new EnumOption<>(name, comment, translation, defaultValue);
        }
    }
}
