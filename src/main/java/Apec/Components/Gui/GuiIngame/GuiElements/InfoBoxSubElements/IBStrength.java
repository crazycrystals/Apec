package Apec.Components.Gui.GuiIngame.GuiElements.InfoBoxSubElements;

import Apec.ApecMain;
import Apec.Components.Gui.GuiIngame.GUIComponent;
import Apec.Components.Gui.GuiIngame.GUIComponentID;
import Apec.DataInterpretation.DataExtractor;
import Apec.Events.ApecSettingChangedState;
import Apec.Settings.SettingID;
import Apec.Utils.ApecUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.util.vector.Vector2f;

public class IBStrength extends GUIComponent {

    private int StrengthStringWidth = 0;

    public IBStrength() {
        super(GUIComponentID.IB_STRENGTH);
    }

    @Override
    public void init() {
        super.init();
        this.enabled = ApecMain.Instance.settingsManager.getSettingState(SettingID.SHOW_STRENGTH);
    }

    @Override
    public void drawTex(DataExtractor.PlayerStats ps, DataExtractor.ScoreBoardData sd, DataExtractor.OtherData od, DataExtractor.TabStats ts, ScaledResolution sr, boolean editingMode) {
        super.drawTex(ps, sd, od, ts, sr, editingMode);
        boolean UseIcons = ApecMain.Instance.settingsManager.getSettingState(SettingID.INFO_BOX_ICONS);
        if (!UseIcons) return;
        Vector2f pos = getCurrentAnchorPoint();
        GuiIngame gi = Minecraft.getMinecraft().ingameGUI;
        mc.renderEngine.bindTexture(new ResourceLocation(ApecMain.modId, "gui/statBars.png"));
        gi.drawTexturedModalRect((int) (pos.x * oneOverScale), pos.y * oneOverScale - 1, 50, 216, 9, 9);
    }

    @Override
    public void draw(DataExtractor.PlayerStats ps, DataExtractor.ScoreBoardData sd, DataExtractor.OtherData od, DataExtractor.TabStats ts, ScaledResolution sr, boolean editingMode) {
        super.draw(ps, sd, od, ts, sr, editingMode);

        boolean UseIcons = ApecMain.Instance.settingsManager.getSettingState(SettingID.INFO_BOX_ICONS);
        String strengthText = (UseIcons ? ts.Strength : "\u2741" + ts.Strength);

        Vector2f pos = getCurrentAnchorPoint();

        String speedText = (UseIcons ? ts.Speed : "\u2726" + ts.Speed);

        StrengthStringWidth = mc.fontRendererObj.getStringWidth(speedText);

        ApecUtils.drawStylizedString(
                strengthText,
                (int) ((pos.x + (UseIcons ? 11 : 0)) * oneOverScale),
                (int) (pos.y * oneOverScale),
                0xFF5555
        );

    }

    @SubscribeEvent
    public void onSettingChanged(ApecSettingChangedState event) {
        if (event.settingID == SettingID.SHOW_STRENGTH) {
            this.enabled = event.state;
        }
    }

    @Override
    public Vector2f getAnchorPointPosition() {
        return new Vector2f(420*scale + 20 * scale, 6*scale);
    }

    @Override
    public Vector2f getBoundingPoint() {
        boolean UseIcons = ApecMain.Instance.settingsManager.getSettingState(SettingID.INFO_BOX_ICONS);
        return new Vector2f(StrengthStringWidth + (UseIcons ? 11 : 0), 10*scale);
    }
}