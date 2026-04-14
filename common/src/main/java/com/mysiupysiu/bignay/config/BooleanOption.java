package com.mysiupysiu.bignay.config;

public class BooleanOption extends ConfigOption<Boolean> {

    private BooleanOption(String name, String comment, String translation, Boolean defaultValue) {
        super(name, comment, translation, defaultValue);
    }

    public static class Builder extends BaseBuilder<Boolean, Builder> {

        public Builder(String name, Boolean defaultValue) {
            super(name, defaultValue);
        }

        @Override
        public BooleanOption build() {
            return new BooleanOption(this.name, this.comment, this.translation, this.defaultValue);
        }
    }
}
