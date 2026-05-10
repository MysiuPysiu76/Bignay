package com.mysiupysiu.bignay;

import com.mysiupysiu.bignay.utils.Fuels;

public class BignayMod {
    public static final String MODID = "bignay";

    public static void init() {
        Fuels.register();
    }
}
