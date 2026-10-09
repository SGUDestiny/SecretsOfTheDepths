package destiny.secretsofthevoid.client.render.particle;

import com.github.alexmodguy.alexscaves.client.particle.ProtonParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;

public class NeutronParticle extends ProtonParticle {
    protected NeutronParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            NeutronParticle particle = new NeutronParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.trailR = 0f;
            particle.trailG = 0.78f;
            particle.trailB = 0.8f;
            particle.setColor(0.7f, 1f, 1f);
            return particle;
        }
    }
}
