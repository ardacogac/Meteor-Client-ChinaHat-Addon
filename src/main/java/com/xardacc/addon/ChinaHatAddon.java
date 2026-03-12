package com.xardacc.addon;

import com.xardacc.addon.modules.ChinaHat;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChinaHatAddon extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("ChinaHat");

    @Override
    public void onInitialize() {
        LOG.info("ChinaHat Addon yükleniyor...");

        // Modülü sisteme kaydet
        Modules.get().add(new ChinaHat());
    }

    @Override
    public String getPackage() {
        // Burası projenin ana package yolu olmalı
        return "com.xardacc.addon";
    }
}