package tfar.creepertweaker.mixin;

import net.minecraft.entity.monster.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CreeperEntity.class)
public interface CreeperEntityAccessor {

    @Accessor void setExplosionRadius(int newRadius);

}
