package net.mersid;

import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.mersid.callbacks.OnRenderCallback;
import net.mersid.config.Configuration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.Window;

public class GuiDrawer extends DrawableHelper {

	private final MinecraftClient mc = MinecraftClient.getInstance();
	private final ToggleSneak ZTS;
	private final InputModded MIM;
	private int mcDisplayWidth = -1, mcDisplayHeight = -1;
	private int x1, x2;
	private int sneaky1, sneaky2, sprinty1, sprinty2;
	//private String hPos, vPos;
	//private String[] hPosOptions, vPosOptions;
	private String sprintTxt, sneakTxt, onlyTxt;

	public GuiDrawer(ToggleSneak zTS, InputModded mIM) {
		super();
		ZTS = zTS;
		MIM = mIM;
		sprintTxt = "sprint";
		sneakTxt = "sneak";

		OnRenderCallback.EVENT.register(this::afterDraw);
	}

	public void setDrawPosition(String hPos, String vPos, String[] hPosOptions, String[] vPosOptions) {
		//System.out.println("HPOS: " + hPos + ", VPOS: " + vPos + ", HPOSOPTS: " + hPosOptions.toString() + ", VPOSOPTS: " + vPosOptions.toString());
		//this.hPos = hPos; this.vPos = vPos;
		//this.hPosOptions = hPosOptions; this.vPosOptions = vPosOptions;
		//sprintTxt = I18n.format("zebrastogglesneak.display.label.sprint");
		//sneakTxt = I18n.format("zebrastogglesneak.display.label.sneak");
		sprintTxt = "sprint";
		sneakTxt = "sneak";
		mcDisplayWidth = -1;
		mcDisplayHeight = -1;
	}

	public void afterDraw () {

		//if (ZTS.displayStatus() == 1) {
		if (ZTS.configuration.displayStyle == Configuration.DisplayStyle.COLOR_CODED)
		{
			computeDrawPosIfChanged();
			fill(x1, sneaky1, x2, sneaky2, ZTS.configuration.toggleSneak?colorPack(0,0,196,196):colorPack(196,196,196,64));
			drawString(mc.textRenderer, sneakTxt, x1 + 2, sneaky1 + 2,
					MIM.sneaking?colorPack(255,255,0,96):colorPack(64,64,64,128));
			fill(x1, sprinty1, x2, sprinty2, ZTS.configuration.toggleSprint?colorPack(0,0,196,196):colorPack(196,196,196,64));
			drawString(mc.textRenderer, sprintTxt, x1 + 2, sprinty1 + 2,
					MIM.sprint?colorPack(255,255,0,96):colorPack(64,64,64,128));
			/*
			fill(2, 226, 6, 229, colorPack(0, 255, 20, 127));
			fill(
					(int)(2),
					(int)(226),
					(int)(16 * mc.mainWindow.getGuiScaleFactor()),
					(int)(240 * mc.mainWindow.getGuiScaleFactor()),
					colorPack(0, 255, 20, 127)
					);
			System.out.println(mc.mainWindow.getGuiScaleFactor());
			*/
		}
		else if (ZTS.configuration.displayStyle == Configuration.DisplayStyle.TEXT_ONLY)
		{
			// no optimization here - I don't like the text only display anyway
			computeTextPos(onlyTxt = MIM.displayText());
			drawString(mc.textRenderer, onlyTxt, x1, sneaky1, colorPack(255,255,255,192));
		}
	}

	public void computeDrawPosIfChanged() {
		Window screen = mc.window;
		//if ((mcDisplayWidth == mc.displayWidth) && (mcDisplayHeight == mc.displayHeight)) return;
		//if ((mcDisplayWidth == screen.getScaledWidth()) && (mcDisplayHeight == screen.getScaledHeight())) return;
		//System.out.println("rectX1: " + x1 + ", rectX2: " + x2 + ", recySnY1: " + sneaky1 + ", rectSnY2: " + sneaky2 + ", rectSpY1: " + sprinty1 + ", rectSpY2: " + sprinty2);
		//System.out.println("screenX: " + screen.getWidth() + ", screenY: " + screen.getHeight());

		int displayWidth = screen.getScaledWidth();
		int textWidth = Math.max(mc.textRenderer.getStringWidth(sprintTxt), mc.textRenderer.getStringWidth(sneakTxt));

		if (ZTS.configuration.hAnchor == Configuration.HAnchor.RIGHT)
		{
			x2 = displayWidth - 2;
			x1 = x2 - 2 - textWidth - 2;
		}
		else if (ZTS.configuration.hAnchor == Configuration.HAnchor.CENTER)
		{
			x1 = (displayWidth / 2) - (textWidth / 2) - 2;
			x2 = x1 + 2 + textWidth + 2;
		}
		else
		{
			x1 = 2;
			x2 = x1 + 2 + textWidth + 2;
		}

		int displayHeight = screen.getScaledHeight();
		int textHeight = mc.textRenderer.fontHeight;

		if (ZTS.configuration.vAnchor == Configuration.VAnchor.BOTTOM)
		{
			sprinty2 = displayHeight - 2;
			sprinty1 = sprinty2 - 2 - textHeight - 2;
			sneaky2 = sprinty1 - 2;
			sneaky1 = sneaky2 - 2 - textHeight - 2;
		}
		else if (ZTS.configuration.vAnchor == Configuration.VAnchor.CENTER)
		{
			sneaky1 = (displayHeight / 2) - 1 - 2 - textHeight - 2;
			sneaky2 = sneaky1 + 2 + textHeight + 2;
			sprinty1 = sneaky2 + 2;
			sprinty2 = sprinty1 + 2 + textHeight + 2;
		} else {
			sneaky1 = 2;
			sneaky2 = sneaky1 + 2 + textHeight + 2;
			sprinty1 = sneaky2 + 2;
			sprinty2 = sprinty1 + 2 + textHeight + 2;
		}

		mcDisplayWidth = screen.getScaledWidth();
		mcDisplayHeight = screen.getScaledHeight();
	}

	public void computeTextPos(String displayTxt) {

		Window screen = mc.window;

		int displayWidth = screen.getScaledWidth();
		int textWidth = mc.textRenderer.getStringWidth(displayTxt);
		if (ZTS.configuration.hAnchor == Configuration.HAnchor.RIGHT)
		{
			x1 = displayWidth - textWidth - 2;
		}
		else if (ZTS.configuration.hAnchor == Configuration.HAnchor.CENTER)
		{
			x1 = (displayWidth / 2) - (textWidth / 2) - 2;
		} else {
			x1 = 2;
			x2 = x1 + 2 + textWidth + 2;
		}

		int displayHeight = screen.getScaledHeight();
		int textHeight = mc.textRenderer.fontHeight;
		if (ZTS.configuration.vAnchor == Configuration.VAnchor.BOTTOM)
		{
			sneaky1 = displayHeight - 12;
		}
		else if (ZTS.configuration.vAnchor == Configuration.VAnchor.CENTER)
		{
			sneaky1 = (displayHeight / 2) + textHeight/2;
		} else {
			sneaky1 = 2 + textHeight;
		}
	}

	private int colorPack (int red, int green, int blue, int alpha){
		return ((red & 255) << 16) | ((green & 255) << 8) | (blue & 255) | ((alpha & 255) << 24);
	}

}