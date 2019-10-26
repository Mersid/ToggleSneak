package net.mersid;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.GameOptions;

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

		if (ZTS.config.toggleSneak) {
			if (gameOptions.keySneak.isPressed()) {
				if (sneakWasPressed == 0) {
					if (sneaking) {
						sneakWasPressed = -1;
					} else if (player.isRiding() || player.abilities.flying) {
						sneakWasPressed = ZTS.config.keyHoldTicks + 1;
					} else {
						sneakWasPressed = 1;
					}
					sneaking = !sneaking;
				} else if (sneakWasPressed > 0){
					sneakWasPressed++;
				}
			} else {
				if ((ZTS.config.keyHoldTicks > 0) && (sneakWasPressed > ZTS.config.keyHoldTicks)) sneaking = false;
				sneakWasPressed = 0;
			}
		} else {
			sneaking = gameOptions.keySneak.isPressed();
		}

		if (sneaking) {
			movementSideways *= 0.3F;
			movementForward *= 0.3F;
		}

		if (ZTS.config.toggleSprint) {
			if (gameOptions.keySprint.isPressed()) {
				if (sprintWasPressed == 0) {
					if (sprint) {
						sprintWasPressed = -1;
					} else if (player.abilities.flying) {
						sprintWasPressed = ZTS.config.keyHoldTicks + 1;
					} else {
						sprintWasPressed = 1;
					}
					sprint = !sprint;
				} else if (sprintWasPressed > 0){
					sprintWasPressed++;
				}
			} else {
				if ((ZTS.config.keyHoldTicks > 0) && (sprintWasPressed > ZTS.config.keyHoldTicks)) sprint = false;
				sprintWasPressed = 0;
			}
		} else sprint = false;

		// sprint conditions same as in net.minecraft.client.entity.EntityPlayerSP.onLivingUpdate()
		// check for hungry or flying. But nvm, if conditions not met, sprint will
		// be canceled there afterwards anyways
		if (sprint && movementForward == 1.0F && player.onGround && !player.isHandActive()
				&& !player.isPotionActive(Effects.BLINDNESS)) player.setSprinting(true);

		if (ZTS.config.flyBoost && player.abilities.isCreativeMode && player.abilities.flying
				&& (mc.getRenderViewEntity() == player) && sprint) {

			if (originalFlySpeed < 0.0F || this.player.abilities.getFlySpeed() != boostedFlySpeed)
				originalFlySpeed = this.player.abilities.getFlySpeed();
			boostedFlySpeed = originalFlySpeed * ZTS.config.flyBoostFactor;
			player.abilities.setFlySpeed(boostedFlySpeed);

			Vec3d motion = player.getMotion();
			if (sneaking)
			{
				player.setMotion(motion.x, motion.y - (0.15D * (double)(ZTS.config.flyBoostFactor - 1.0F)), motion.z);
			}
			if (jumping)
			{
				player.setMotion(motion.x, motion.y + (0.15D * (double)(ZTS.config.flyBoostFactor - 1.0F)), motion.z);
			}

		} else {
			if (player.abilities.getFlySpeed() == boostedFlySpeed)
				this.player.abilities.setFlySpeed(originalFlySpeed);
			originalFlySpeed = -1.0F;
		}
	}
}
