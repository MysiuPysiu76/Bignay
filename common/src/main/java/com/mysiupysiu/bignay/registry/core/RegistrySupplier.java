package com.mysiupysiu.bignay.registry.core;

import java.util.function.Supplier;

public interface RegistrySupplier<T> extends Supplier<T> {
    T get();
}
