package com.mysiupysiu.bignay.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Registrar<T> {

    private final List<Entry<T>> entries = new ArrayList<>();

    public RegistrySupplier<T> register(String id, Supplier<T> supplier) {
        Entry<T> entry = new Entry<>(id, supplier);
        entries.add(entry);
        return entry;
    }

    public List<Entry<T>> getEntries() {
        return entries;
    }

    public static class Entry<T> implements RegistrySupplier<T> {
        private final String id;
        private final Supplier<T> supplier;

        private Supplier<T> resolvedSupplier;

        public Entry(String id, Supplier<T> supplier) {
            this.id = id;
            this.supplier = supplier;
        }

        public String getId() {
            return id;
        }

        public Supplier<T> getSupplier() {
            return supplier;
        }

        public void set(T value) {
            this.resolvedSupplier = () -> value;
        }

        public void setSupplier(Supplier<T> resolved) {
            this.resolvedSupplier = resolved;
        }

        @Override
        public T get() {
            return resolvedSupplier != null ? resolvedSupplier.get() : null;
        }
    }
}
