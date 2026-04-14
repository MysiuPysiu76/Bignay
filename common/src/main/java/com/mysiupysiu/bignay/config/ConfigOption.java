package com.mysiupysiu.bignay.config;

public abstract class ConfigOption<T> {

    private final String name;
    private final String comment;
    private final String translation;
    private final T defaultValue;
    protected T value;

    protected ConfigOption(String name, String comment, String translation, T defaultValue) {
        this.name = name;
        this.comment = comment;
        this.translation = translation;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T reset() {
        this.value = this.defaultValue;
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public String getComment() {
        return this.comment;
    }

    public String getTranslation() {
        return this.translation;
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public static abstract class BaseBuilder<T, B extends BaseBuilder<T, B>> {
        protected final String name;
        protected final T defaultValue;
        protected String comment = "";
        protected String translation = "";

        public BaseBuilder(String name, T defaultValue) {
            this.name = name;
            this.defaultValue = defaultValue;
        }

        public abstract ConfigOption<T> build();

        public B comment(String comment) {
            this.comment = comment;
            return (B) this;
        }

        public B translation(String key) {
            this.translation = key;
            return (B) this;
        }
    }
}
