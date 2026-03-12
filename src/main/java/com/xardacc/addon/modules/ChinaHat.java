package com.xardacc.addon.modules;

import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.Renderer3D;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.MathHelper;

public class ChinaHat extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    double quality = 256;
    double radius = 0.550;

    private final Setting<Boolean> showInFirstPerson = sgGeneral.add(new BoolSetting.Builder()
            .name("show-in-first-person")
            .description("Birinci şahıs kamerasında gösterir.")
            .defaultValue(true)
            .build()
    );

    private final ColorSetting topColor = sgGeneral.add(new ColorSetting.Builder()
        .name("top-color")
        .description("Tepenin rengi.")
        .defaultValue(new Color(255, 255, 255, 200))
        .build()
);

private final ColorSetting bottomColor = sgGeneral.add(new ColorSetting.Builder()
        .name("bottom-color")
        .description("Kenarların rengi.")
        .defaultValue(new Color(45, 170, 255, 200))
        .build()
);

    public ChinaHat() {
        super(Categories.Render, "china-hat", "Oyuncunun kafasına bir şapka ekler.");
    }

    @EventHandler
    public void onRender(Render3DEvent event) {
        if (mc.player == null || mc.world == null) return;
    if (!showInFirstPerson.get() && mc.options.getPerspective().isFirstPerson()) return;

        // Enterpolasyonlu pozisyon hesaplama
        double x = MathHelper.lerp(event.tickDelta, mc.player.lastRenderX, mc.player.getX());
        double y = MathHelper.lerp(event.tickDelta, mc.player.lastRenderY, mc.player.getY());
        double z = MathHelper.lerp(event.tickDelta, mc.player.lastRenderZ, mc.player.getZ());

        double heightOffset = mc.player.getEyeHeight(mc.player.getPose()) + 0.20;
        //if (mc.player.isSneaking()) heightOffset -= 0.2;

        double baseY = y + heightOffset;
        double topY = baseY + 0.3;

        Color topC = topColor.get();
        Color botC = bottomColor.get();

        Renderer3D renderer = event.renderer;

        for (int i = 0; i < quality; i++) {
            double angle1 = (i * (Math.PI * 2)) / quality;
            double angle2 = ((i + 1) * (Math.PI * 2)) / quality;

            double x1 = x + Math.cos(angle1) * radius;
            double z1 = z + Math.sin(angle1) * radius;

            double x2 = x + Math.cos(angle2) * radius;
            double z2 = z + Math.sin(angle2) * radius;

            // Üçgen yüzeyler (Daha güzel görünür)
            renderer.line(x, topY, z, x1, baseY, z1, topC, botC);
            renderer.line(x1, baseY, z1, x2, baseY, z2, botC, botC);
        }
    }
}