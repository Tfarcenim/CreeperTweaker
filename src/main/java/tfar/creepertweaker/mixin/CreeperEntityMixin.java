package tfar.creepertweaker.mixin;

import tfar.creepertweaker.CreeperTweaker;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {
    @Inject(method = "func_234278_m_",at = @At("RETURN"))
    private static void modifySpeedAndAggroDistance(CallbackInfoReturnable<AttributeModifierMap.MutableAttribute> cir) {
        cir.getReturnValue().createMutableAttribute(Attributes.MOVEMENT_SPEED, CreeperTweaker.ServerConfig.creeper_speed.get())
                .createMutableAttribute(Attributes.FOLLOW_RANGE, CreeperTweaker.ServerConfig.creeper_aggro_distance.get());
    }
}
