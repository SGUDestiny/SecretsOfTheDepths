package destiny.secretsofthevoid.server.util;

import net.minecraft.world.level.Level;

public class MathUtil {
    public float getBoundRandomFloat(Level level, float origin, float limit) {
        return origin + (limit - origin) * level.getRandom().nextFloat();
    }

    public static float getBoundRandomFloatStatic(Level level, float origin, float limit) {
        return origin + (limit - origin) * level.getRandom().nextFloat();
    }

    public double getBoundRandomDouble(Level level, double origin, double limit) {
        return origin + (limit - origin) * level.getRandom().nextDouble();
    }

    public static double getBoundRandomDoubleStatic(Level level, double origin, double limit) {
        return origin + (limit - origin) * level.getRandom().nextDouble();
    }
}
