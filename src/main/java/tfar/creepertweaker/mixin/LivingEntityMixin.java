package tfar.creepertweaker.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.creepertweaker.CreeperTweaker;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(at = @At("HEAD"), method = "getRenderScale",cancellable = true)
	private void init(CallbackInfoReturnable<Float> cir) {
		if ((Object)this instanceof CreeperEntity) {
			cir.setReturnValue(CreeperTweaker.ServerConfig.creeper_scale.get().floatValue());
		}
	}
}
