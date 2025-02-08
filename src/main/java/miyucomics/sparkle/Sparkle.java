package miyucomics.sparkle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@Mod(Sparkle.MOD_ID)
@Mod.EventBusSubscriber(modid = Sparkle.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class Sparkle {
    public static final String MOD_ID = "sparkle";

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);

    public static final RegistryObject<SimpleParticleType> SPARKLE_PARTICLE = PARTICLE_TYPES.register("sparkle",
            () -> new SimpleParticleType(true));

    public static SparkleConfig CONFIG = SparkleConfig.of(MOD_ID);

    public static List<Block> SPARKLY_BLOCKS;
    public static List<Item> SPARKLY_ITEMS;
    public static List<? extends EntityType<?>> SPARKLY_ENTITIES;

    public Sparkle() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        PARTICLE_TYPES.register(modEventBus);  // 添加这一行来注册粒子

        SPARKLY_BLOCKS = CONFIG.blocks.stream()
                .filter(string -> string != null && !string.isEmpty())
                .map(thing -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(thing)))
                .filter(java.util.Objects::nonNull)
                .toList();

        SPARKLY_ITEMS = CONFIG.items.stream()
                .filter(string -> string != null && !string.isEmpty())
                .map(thing -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(thing)))
                .filter(java.util.Objects::nonNull)
                .toList();

        SPARKLY_ENTITIES = CONFIG.entities.stream()
                .filter(string -> string != null && !string.isEmpty())
                .map(thing -> ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(thing)))
                .filter(java.util.Objects::nonNull)
                .toList();
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(SPARKLE_PARTICLE.get(), SparkleParticle.Factory::new);
    }
}