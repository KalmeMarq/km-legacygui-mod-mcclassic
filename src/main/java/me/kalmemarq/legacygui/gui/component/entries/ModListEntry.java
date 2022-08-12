package me.kalmemarq.legacygui.gui.component.entries;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.gui.component.ModListWidget;
import me.kalmemarq.legacygui.gui.component.ObjectSelectionList;
import me.kalmemarq.legacygui.util.GlConst;
import me.kalmemarq.legacygui.util.TextUtil;
import net.fabricmc.loader.api.ModContainer;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ModListEntry extends ObjectSelectionList.Entry<ModListEntry> {
    protected final Minecraft mc;
    public final ModContainer container;
    protected final ModListWidget list;
    protected static final int FULL_ICON_SIZE = 32;
    protected static final int COMPACT_ICON_SIZE = 19;

    public ModListEntry(Minecraft mc, ModContainer container, ModListWidget list) {
        this.container = container;
        this.list = list;
        this.mc = mc;
    }

    @Override
    public void render(int index, int y, int x, int rowWidth, int var6, int mouseX, int mouseY, boolean isHovered) {
        x += getXOffset();
        rowWidth -= getXOffset();
        int iconSize = 0;

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

//        GL11.glEnable(GlConst.GL_BLEND);
//        int id = mc.textures.getTextureId("/gui/gui.png");
//        GL11.glBindTexture(GlConst.GL_TEXTURE_2D, id);
//
//        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
//        GL11.glDisable(GlConst.GL_BLEND);

        String name = this.container.getMetadata().getName();
        this.mc.font.drawShadow(name, x + iconSize + 3, y + 1, 0xFFFFFF);

        String summary = this.container.getMetadata().getDescription();

        List<String> sumList = TextUtil.splitToFit(summary, this.mc.font, rowWidth - iconSize - 7);

        int fontHeight = 9;
        int sumLY = (y + fontHeight + 2);
        for (String sumL : sumList) {
            this.mc.font.drawShadow(sumL, (x + iconSize + 3 + 4), sumLY, 0x808080);
            sumLY += 9;
        }
    }

    public ModContainer getModContainer() {
        return container;
    }

    public int getXOffset() {
        return 0;
    }
}
