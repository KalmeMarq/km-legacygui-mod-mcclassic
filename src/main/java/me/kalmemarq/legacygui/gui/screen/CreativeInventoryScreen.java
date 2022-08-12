package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.User;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.util.*;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class CreativeInventoryScreen extends ExtraScreen {
    public static class Slot {
        public final static int SLOT_SIZE = 22;
        public int id = 0;
        public int x = 0;
        public int y = 0;
        public TileID tileid;
        public Tile tile;

        public Slot(int id, int x, int y, Tile tile, TileID tileid) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.tile = tile;
            this.tileid = tileid;
        }

        public void render(BufferBuilder builder, int mouseX, int mouseY) {
            if (this.tile != null && this.tileid != null) {
                ItemRenderer.renderGuiTile(tileid, builder, x, y);
            }
        }
    }

    private final List<Slot> slots = new ArrayList<>();

    @Override
    public void init() {
        int rows = (int)Math.ceil(TileID.CREATIVE_LIST.size() / 9.0f);

        int invX = this.width / 2 - (int) ((9.0f * Slot.SLOT_SIZE) / 2.0f);
        int invY = this.height / 2 - (int) ((rows * Slot.SLOT_SIZE) / 2.0f);

        for (int i = 0; i < TileID.CREATIVE_LIST.size(); i++) {
            int sX = invX + ((i % 9) * Slot.SLOT_SIZE);
            int sY = invY + ((int)Math.floor(i / 9.0f) * Slot.SLOT_SIZE);

            slots.add(new Slot(i, sX, sY, TileID.CREATIVE_LIST.get(i).getTile(), TileID.CREATIVE_LIST.get(i)));
        }
    }

    public Slot getSlot(int mouseX, int mouseY) {
        Slot slot = null;

        for (int i = 0; i < this.slots.size(); i++) {
            Slot slot0 = this.slots.get(i);
            if (mouseX > slot0.x && mouseX < slot0.x + Slot.SLOT_SIZE && mouseY > slot0.y && mouseY < slot0.y + Slot.SLOT_SIZE) {
                slot = slot0;
                break;
            }
        }

        return slot;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        this.renderGradientBackground();
        int rows = (int)Math.ceil((TileID.CREATIVE_LIST.size() / 9.0f));
        ExtraDrawableHelper.fillX(this.width / 2 - (9 * Slot.SLOT_SIZE / 2), this.height / 2 - (rows * Slot.SLOT_SIZE  /2), this.width / 2 + (9 * Slot.SLOT_SIZE / 2), this.height / 2 + (rows * Slot.SLOT_SIZE  /2) - 4, 0xFF333333);

        drawCenteredString(this.font, "Select block", this.width / 2, 30, 16777215);

        RenderHelper.bindTexture("/terrain.png");
        BufferBuilder builder = ExtraTesselator.getInstance().getBuilder();

        for (int i = 0; i < this.slots.size(); i++) {
            Slot slot = this.slots.get(i);

            slot.render(builder, mouseX, mouseY);
        }

        Slot s = getSlot(mouseX, mouseY);

        if (s != null) {
            fillGradient(s.x + 1, s.y + 1, s.x + 16 + 1, s.y + 16 + 1, -1862270977, -1056964609);
            this.drawTooltip(Language.translate(s.tileid.getTranslationKey()), mouseX, mouseY);
        }

    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        if (button == 0) {
            Slot slot = this.getSlot(x, y);

            if (slot != null) {
                System.out.println(x + " - " + y + " - " + slot.tileid.getName() + " - " + slot.tileid.getId() + " - " + (slot.tile.id));
                this.minecraft.player.inventory.replaceSlot(slot.tile);
                this.minecraft.openScreen((Screen)null);
                return;
            }
        }

        super.mouseClicked(x, y, button);
    }
}
