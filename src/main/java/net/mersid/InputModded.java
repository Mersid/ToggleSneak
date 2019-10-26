package net.mersid;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.GameOptions;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

public class InputModded extends Input {
	private final GameOptions gameOptions;
	public boolean sprint;
	private ToggleSneak ZTS;
	private MinecraftClient mc;
	private int sneakWasPressed;
	private int sprintWasPressed;
	private ClientPlayerEntity player;
	private float originalFlySpeed = -1.0F;
	private float boostedFlySpeed;

	public InputModded()
	{
		this.gameOptions = mc.options;
		this.sprint = false;
		this.ZTS = ZTS;
		this.mc = MinecraftClient.getInstance(); // we'll need replace the static ref by a link passed as parameter
		this.sneakWasPressed = 0;
		this.sprintWasPressed = 0;

	}


	@Override

	public void tick(boolean slow, boolean noDampening)
	{

		player = mc.player;
		movementSideways = 0.0F;
		movementForward = 0.0F;

		if (this.pressingForward = gameOptions.keyForward.isPressed()) movementForward++;
		if (this.pressingBack = gameOptions.keyBack.isPressed()) movementForward--;
		if (this.pressingLeft = gameOptions.keyLeft.isPressed()) movementSideways++;
		if (this.pressingRight = gameOptions.keyRight.isPressed()) movementSideways--;

		jumping = gameOptions.keyJump.isPressed();

		if (ZTS.configuration.toggleSneak) {
			if (gameOptions.keySneak.isPressed()) {
				if (sneakWasPressed == 0) {
					if (sneaking) {
						sneakWasPressed = -1;
					} else if (player.isRiding() || player.abilities.flying) {
						sneakWasPressed = ZTS.configuration.keyHoldTicks + 1;
					} else {
						sneakWasPressed = 1;
					}
					sneaking = !sneaking;
				} else if (sneakWasPressed > 0){
					sneakWasPressed++;
				}
			} else {
				if ((ZTS.configuration.keyHoldTicks > 0) && (sneakWasPressed > ZTS.configuration.keyHoldTicks)) sneaking = false;
				sneakWasPressed = 0;
			}
		} else {
			sneaking = gameOptions.keySneak.isPressed();
		}

		if (sneaking) {
			movementSideways *= 0.3F;
			movementForward *= 0.3F;
		}

		if (ZTS.configuration.toggleSprint) {
			if (gameOptions.keySprint.isPressed()) {
				if (sprintWasPressed == 0) {
					if (sprint) {
						sprintWasPressed = -1;
					} else if (player.abilities.flying) {
						sprintWasPressed = ZTS.configuration.keyHoldTicks + 1;
					} else {
						sprintWasPressed = 1;
					}
					sprint = !sprint;
				} else if (sprintWasPressed > 0){
					sprintWasPressed++;
				}
			} else {
				if ((ZTS.configuration.keyHoldTicks > 0) && (sprintWasPressed > ZTS.configuration.keyHoldTicks)) sprint = false;
				sprintWasPressed = 0;
			}
		} else sprint = false;

		// sprint conditions same as in net.minecraft.client.entity.EntityPlayerSP.onLivingUpdate()
		// check for hungry or flying. But nvm, if conditions not met, sprint will
		// be canceled there afterwards anyways
		if (sprint && movementForward == 1.0F && player.onGround && !player.isUsingItem()
				&& !player.hasStatusEffect(StatusEffects.BLINDNESS)) player.setSprinting(true);

		if (ZTS.configuration.flyBoost && player.abilities.creativeMode && player.abilities.flying
				&& (mc.getCameraEntity() == player) && sprint) {

			if (originalFlySpeed < 0.0F || this.player.abilities.getFlySpeed() != boostedFlySpeed)
				originalFlySpeed = this.player.abilities.getFlySpeed();
			boostedFlySpeed = originalFlySpeed * (float)ZTS.configuration.flyBoostFactor;
			player.abilities.setFlySpeed(boostedFlySpeed);

			Vec3d motion = player.getVelocity();
			if (sneaking)
			{
				player.setVelocity(motion.x, motion.y - (0.15D * (double)(ZTS.configuration.flyBoostFactor - 1.0F)), motion.z);
			}
			if (jumping)
			{
				player.setVelocity(motion.x, motion.y + (0.15D * (double)(ZTS.configuration.flyBoostFactor - 1.0F)), motion.z);
			}

		} else {
			if (player.abilities.getFlySpeed() == boostedFlySpeed)
				this.player.abilities.setFlySpeed(originalFlySpeed);
			originalFlySpeed = -1.0F;
		}
	}
}
