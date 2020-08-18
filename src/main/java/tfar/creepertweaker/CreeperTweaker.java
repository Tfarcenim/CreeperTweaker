package tfar.creepertweaker;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import tfar.creepertweaker.mixin.CreeperEntityAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CreeperTweaker.MODID)
public class CreeperTweaker {
    // Directly reference a log4j logger.

    public static final String MODID = "creepertweaker";

    public CreeperTweaker() {
        // Register ourselves for server and other game events we are interested in
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::client);
        MinecraftForge.EVENT_BUS.addListener(this::modifyBlastPower);
    }

    public void client(FMLClientSetupEvent e) {
        MinecraftForge.EVENT_BUS.addListener(this::preRender);
        MinecraftForge.EVENT_BUS.addListener(this::postRender);
    }

    public static final ServerConfig SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<ServerConfig, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
        SERVER_SPEC = specPair2.getRight();
        SERVER = specPair2.getLeft();
    }


    public static class ServerConfig {

        public static ForgeConfigSpec.DoubleValue creeper_scale;
        public static ForgeConfigSpec.DoubleValue creeper_speed;
        public static ForgeConfigSpec.DoubleValue creeper_aggro_distance;
        public static ForgeConfigSpec.IntValue creeper_blast_power;

        public ServerConfig(ForgeConfigSpec.Builder builder) {
            builder.push("server");
            creeper_scale = builder.
                    comment("Size of all creepers")
                    .defineInRange("creeper_scale", 1, 0, 100000d);

            creeper_speed = builder.
                    comment("Speed of all creepers")
                    .defineInRange("creeper_speed", .25, 0, 100000d);

            creeper_aggro_distance = builder.
                    comment("Aggro distance of all creepers")
                    .defineInRange("creeper_aggro_distance", 16, 0, 100000d);

            creeper_blast_power = builder.
                    comment("Explosion multiplier of all creepers")
                    .defineInRange("creeper_blast_power", 3, 0, 100000);
            builder.pop();
        }
    }

    public void modifyBlastPower(EntityJoinWorldEvent e) {
        if (e.getEntity() instanceof CreeperEntity) {
            ((CreeperEntityAccessor)e.getEntity()).setExplosionRadius(ServerConfig.creeper_blast_power.get());
        }
    }

    public void preRender(RenderLivingEvent.Pre<CreeperEntity,CreeperModel<CreeperEntity>> event) {
        if (event.getEntity() instanceof CreeperEntity) {
            float scale = ServerConfig.creeper_scale.get().floatValue();
            event.getMatrixStack().scale(scale, scale, scale);
        }
    }

    public void postRender(RenderLivingEvent.Post<CreeperEntity,CreeperModel<CreeperEntity>> event) {
        if (event.getEntity() instanceof CreeperEntity) {
            float scale = 1 / ServerConfig.creeper_scale.get().floatValue();
            event.getMatrixStack().scale(scale, scale, scale);
        }
    }
}
