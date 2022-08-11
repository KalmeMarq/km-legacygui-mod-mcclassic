package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.User;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class CreativeInventoryScreen extends ExtraScreen {
    public static class TileID {
        public static Map<Integer, TileID> TILE_IDS = new HashMap<>();
        public static List<TileID> CREATIVE_LIST = new ArrayList<>();

        public static TileID UNKNOWN = new TileID(-1, "UNKNOWN");
        public static TileID OBDISIAN = new TileID(49, "Obdisian", true);
        public static TileID MOSSY = new TileID(48, "Mossy Cobblestone", true);
        public static TileID BOOKSHELF = new TileID(47, "Bookshelf", true);
        public static TileID TNT = new TileID(46, "TNT", true);
        public static TileID BRICKS = new TileID(45, "Bricks", true);
        public static TileID SLAB = new TileID(44, "Slab", true);
        public static TileID DOUBLE_SLAB = new TileID(43, "Double Slab", true);
        public static TileID IRON_BLOCK = new TileID(42, "Iron Block", true);
        public static TileID GOLD_BLOCK = new TileID(41, "Gold Block", true);
        public static TileID RED_MUSHROOM = new TileID(40, "Red Mushroom", true);
        public static TileID BROWN_MUSHROOM = new TileID(39, "Brown Mushroom", true);
        public static TileID ROSE = new TileID(38, "Rose", true);
        public static TileID DANDELION = new TileID(37, "Dandelion", true);
        public static TileID WHITE_CLOTH = new TileID(36, "White Cloth", true);
        public static TileID LIGHT_CLOTH = new TileID(35, "Light Gray Cloth", true);
        public static TileID DARK_CLOTH = new TileID(34, "Dark Gray Cloth", true);
        public static TileID ROSE_CLOTH = new TileID(33, "Rose Cloth", true);
        public static TileID MAGENTA_CLOTH = new TileID(32, "Magenta Cloth", true);
        public static TileID PURPLE_CLOTH = new TileID(31, "Purple Cloth", true);
        public static TileID VIOLET_CLOTH = new TileID(30, "Violet Cloth", true);
        public static TileID ULTRAMARINE_CLOTH = new TileID(29, "Ultramarine Cloth", true);
        public static TileID CAPRI_CLOTH = new TileID(28, "Capri Cloth", true);
        public static TileID CYAN_CLOTH = new TileID(27, "Cyan Cloth", true);
        public static TileID SPRING_CLOTH = new TileID(26, "Spring Green Cloth", true);
        public static TileID GREEN_CLOTH = new TileID(25, "Green Cloth", true);
        public static TileID CHARTREUSE_CLOTH = new TileID(24, "Chartreuse Cloth", true);
        public static TileID YELLOW_CLOTH = new TileID(23, "Yellow Cloth", true);
        public static TileID ORANGE_CLOTH = new TileID(22, "Orange Cloth", true);
        public static TileID RED_CLOTH = new TileID(21, "Red Cloth", true);
        public static TileID GLASS = new TileID(20, "Glass", true);
        public static TileID SPONGE = new TileID(19, "Sponge", true);
        public static TileID LEAVES = new TileID(18, "Leaves", true);
        public static TileID LOG = new TileID(17, "Log", true);
        public static TileID COAL_ORE = new TileID(16, "Coal Ore", true);
        public static TileID IRON_ORE = new TileID(15, "Iron Ore", true);
        public static TileID GOLD_ORE = new TileID(14, "Gold Ore", true);
        public static TileID GRAVEL = new TileID(13, "Gravel", true);
        public static TileID SAND = new TileID(12, "Sand", true);
        public static TileID CALM_LAVA = new TileID(11, "Calm Lava", true);
        public static TileID LAVA = new TileID(10, "Lava", true);
        public static TileID CALM_WATER = new TileID(9, "Calm Water", true);
        public static TileID WATER = new TileID(8, "Water", true);
        public static TileID UNBREAKABLE = new TileID(7, "Unbreakable");
        public static TileID BUSH = new TileID(6, "Bush", true);
        public static TileID WOOD = new TileID(5, "Wood", true);
        public static TileID STONE_BRICK = new TileID(4, "Stone Brick", true);
        public static TileID DIRT = new TileID(3, "Dirt", true);
        public static TileID GRASS = new TileID(2, "Grass", true);
        public static TileID ROCK = new TileID(1, "Rock", true);
        public static TileID AIR = new TileID(0, "Air");

        private final int id;
        private final String name;
        private final boolean onCreative;

        private TileID(int id, String name) {
            this.id = id;
            this.name = name;
            this.onCreative = false;
            TileID.TILE_IDS.put(id, this);
        }

        private TileID(int id, String name, boolean onCreative) {
            this.id = id;
            this.name = name;
            this.onCreative = onCreative;
            TileID.TILE_IDS.put(id, this);
            if (onCreative) {
                TileID.CREATIVE_LIST.add(this);
                TileID.CREATIVE_LIST.sort(Comparator.comparingInt(TileID::getId));
            }
        }

        public static TileID get(int id) {
            if (id == -1 || id > 49) {
                return TileID.UNKNOWN;
            } else {
                return TILE_IDS.get(id);
            }
        }

        public String getName() {
            return this.name;
        }

        public Tile getTile() {
            return Tile.tiles[this.id];
        }

        public int getId() {
            return this.id;
        }
    }

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

        public void render(Tesselator tesselator, int mouseX, int mouseY) {
            if (this.tile != null && this.tileid != null) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)x, (float)y, 0.0F);
                GL11.glScalef(10.0F, 10.0F, 10.0F);
                GL11.glTranslatef(1.0F, 0.5F, 8.0F);
                GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

                GL11.glTranslatef(-1.5F, 0.5F, 0.5F);
                GL11.glScalef(-1.0F, -1.0F, -1.0F);
                tesselator.begin();
                tile.renderAsIcon(tesselator);
                tesselator.end();
                GL11.glPopMatrix();
            }
        }
    }

    private List<Slot> slots = new ArrayList<>();

    private int invX;
    private int invY;
    @Override
    public void init() {
        int rows = (int)Math.ceil(TileID.CREATIVE_LIST.size() / 9.0f);

        invX = this.width / 2 - (int)((9.0f * Slot.SLOT_SIZE) / 2.0f);
        invY = this.height / 2 - (int)((rows * Slot.SLOT_SIZE) / 2.0f);

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
        ExtraDrawableHelper.fillX(this.width / 2 - (9 * Slot.SLOT_SIZE / 2), this.height / 2 - (rows * Slot.SLOT_SIZE  /2), this.width / 2 + (9 * Slot.SLOT_SIZE / 2), this.height / 2 + (rows * Slot.SLOT_SIZE  /2), 0xFF333333);

        int txrId = this.minecraft.textures.getTextureId("/terrain.png");
        Tesselator tesselator = Tesselator.instance;
        GL11.glBindTexture(3553, txrId);

        for (int i = 0; i < this.slots.size(); i++) {
            Slot slot = this.slots.get(i);

            slot.render(tesselator, mouseX, mouseY);
        }

        Slot s = getSlot(mouseX, mouseY);

        if (s != null) {
            fillGradient(s.x + 1, s.y + 1, s.x + 16 + 1, s.y + 16 + 1, -1862270977, -1056964609);
            this.drawTooltip(s.tileid.getName(), mouseX, mouseY);
        }

//        mouseX = this.getHoveredTile(mouseX, mouseY);
//        ExtraDrawableHelper.fillX(this.width / 2 - 120, 30, this.width / 2 + 120, 180, 0xFF333333);
//
//        if (mouseX >= 0) {
//            mouseY = this.width / 2 + mouseX % 9 * 24 + -108;
//            int var3 = this.height / 2 + mouseX / 9 * 24 + -60;
//            fillGradient(mouseY - 3 + 3, var3 - 8 + 3, mouseY + 23 - 3, var3 + 24 - 6- 3, -1862270977, -1056964609);
//
////            this.drawTooltip(TileID.CREATIVE_LIST.get(mouseX).getName(), mX, mY);
//        }
//
//        drawCenteredString(this.font, "Select block", this.width / 2, 40, 16777215);
//        int txrId = this.minecraft.textures.getTextureId("/terrain.png");
//        Tesselator var8 = Tesselator.instance;
//        GL11.glBindTexture(3553, txrId);
//
//        for(mouseY = 0; mouseY < TileID.CREATIVE_LIST.size(); ++mouseY) {
//            Tile var4 = (Tile)TileID.CREATIVE_LIST.get(mouseY).getTile();
//            GL11.glPushMatrix();
//            int var5 = this.width / 2 + mouseY % 9 * 24 + -108;
//            int var6 = this.height / 2 + mouseY / 9 * 24 + -60;
//            GL11.glTranslatef((float)var5, (float)var6, 0.0F);
//            GL11.glScalef(10.0F, 10.0F, 10.0F);
//            GL11.glTranslatef(1.0F, 0.5F, 8.0F);
//            GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
//            GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
////            if (mouseX == mouseY) {
////                GL11.glScalef(1.6F, 1.6F, 1.6F);
////            }
//
//            GL11.glTranslatef(-1.5F, 0.5F, 0.5F);
//            GL11.glScalef(-1.0F, -1.0F, -1.0F);
//            var8.begin();
//            var4.renderAsIcon(var8);
//            var8.end();
//            GL11.glPopMatrix();
//        }
    }

    private int getHoveredTile(int mouseX, int mouseY) {
        for(int var3 = 0; var3 < TileID.CREATIVE_LIST.size(); ++var3) {
            int var4 = this.width / 2 + var3 % 9 * 24 + -108 - 3;
            int var5 = this.height / 2 + var3 / 9 * 24 + -60 + 3;
            if (mouseX >= var4 && mouseX <= var4 + 24 && mouseY >= var5 - 12 && mouseY <= var5 + 12) {
                return var3;
            }
        }

        return -1;
    }

    @Override
    protected void mouseClicked(int x, int y, int button) {
        if (button == 0) {
            Slot slot = this.getSlot(x, y);

            if (slot != null) {
                System.out.println(x + " - " + y + " - " + slot.tileid.getName() + " - " + slot.tileid.getId());
                this.minecraft.player.inventory.replaceSlot(slot.tile.id);
                this.minecraft.openScreen((Screen)null);
                return;
            }
        }

        super.mouseClicked(x, y, button);
    }

    private void drawTooltip(String text, int x, int y) {
        drawTooltip(new String[]{text}, x, y);
    }

    private void drawTooltip(String[] text, int x, int y) {
        if (text.length > 0) {
            int fontHeight = 10;

            int maxWidth = 0;
            int var6 = text.length == 1 ? -2 : 0;

            for (int i = 0; i < text.length; i++) {
                int lineWidth = this.minecraft.font.width(text[i]);
                var6 += fontHeight;
                if (lineWidth > maxWidth) {
                    maxWidth = lineWidth;
                }
            }

            int var23 = x + 12;
            int var24 = y - 12;

            if (var23 + maxWidth > this.width) {
                var23 -= 28 + maxWidth;
            }

            if (var24 + var6 + 6 > this.height) {
                var24 = this.height - var6 - 6;
            }

            if (y - var6 - 8 < 0) {
                var24 = y + 8;
            }

            int z = 100;
            GL11.glTranslatef(0, 0, z);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glBegin(GL11.GL_QUADS);
            fillGradient(var23 - 3, var24 - 4, var23 + maxWidth + 3, var24 - 3, 0,-267386864, -267386864);
            fillGradient(var23 - 3, var24 + var6 + 3, var23 + maxWidth + 3, var24 + var6 + 4, 0, -267386864, -267386864);
            fillGradient(var23 - 3, var24 - 3, var23 + maxWidth + 3, var24 + var6 + 3, 0,-267386864, -267386864);
            fillGradient(var23 - 4, var24 - 3, var23 - 3, var24 + var6 + 3, 0,-267386864, -267386864);
            fillGradient(var23 + maxWidth + 3, var24 - 3, var23 + maxWidth + 4, var24 + var6 + 3, 0,-267386864, -267386864);
            fillGradient(var23 - 3, var24 - 3 + 1, var23 - 3 + 1, var24 + var6 + 3 - 1, 0,1347420415, 1344798847);
            fillGradient(var23 + maxWidth + 2, var24 - 3 + 1, var23 + maxWidth + 3, var24 + var6 + 3 - 1, 0,1347420415, 1344798847);
            fillGradient(var23 - 3, var24 - 3, var23 + maxWidth + 3, var24 - 3 + 1, 0,1347420415, 1347420415);
            fillGradient(var23 - 3, var24 + var6 + 2, var23 + maxWidth + 3, var24 + var6 + 3, 0,1344798847, 1344798847);
            GL11.glEnd();
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            int x00 = var23;
            int y00 = var24;
            for (int i = 0; i < text.length; i++) {
                this.minecraft.font.drawShadow(text[i], x00, y00, 0xFFFFFF);
                y00 += fontHeight;
            }

            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(0, 0, -z);
        }
    }

    private static void fillGradient(int x0, int y0, int x1, int y1, int z, int color1, int color2) {
        float var6 = (float)(color1 >>> 24) / 255.0F;
        float var7 = (float)(color1 >> 16 & 255) / 255.0F;
        float var8 = (float)(color1 >> 8 & 255) / 255.0F;
        int col1 = (int) ((float)(color1 & 255) / 255.0F);
        float var9 = (float)(color2 >>> 24) / 255.0F;
        float var10 = (float)(color2 >> 16 & 255) / 255.0F;
        float var11 = (float)(color2 >> 8 & 255) / 255.0F;
        int col2 = (int) ((float)(color2 & 255) / 255.0F);
        GL11.glColor4f(var7, var8, col1, var6);
        GL11.glVertex3f((float)x1, (float)y0, z);
        GL11.glVertex3f((float)x0, (float)y0, z);
        GL11.glColor4f(var10, var11, col2, var9);
        GL11.glVertex3f((float)x0, (float)y1, z);
        GL11.glVertex3f((float)x1, (float)y1, z);
    }
}
