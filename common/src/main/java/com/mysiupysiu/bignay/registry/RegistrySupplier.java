package com.mysiupysiu.bignay.registry;

import java.util.function.Supplier;

public interface RegistrySupplier<T> extends Supplier<T> {
    T get();
}
