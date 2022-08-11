package me.kalmemarq.legacygui.gui.screen;

import com.mojang.minecraft.User;
import com.mojang.minecraft.gui.Screen;
import com.mojang.minecraft.level.tile.Tile;
import com.mojang.minecraft.renderer.Tesselator;
import me.kalmemarq.legacygui.gui.ExtraDrawableHelper;
import me.kalmemarq.legacygui.util.Language;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class CreativeInventoryScreen extends ExtraScreen {
    public static class TileID {
        public static Map<Integer, TileID> TILE_IDS = new HashMap<>();
        public static List<TileID> CREATIVE_LIST = new ArrayList<>();

        public static TileID UNKNOWN = new TileID(-1, "UNKNOWN", "UNKWONW");
        public static TileID OBDISIAN = new TileID(49, "Obdisian", "minecraft.tile.obsidian", true);
        public static TileID MOSSY = new TileID(48, "Mossy Cobblestone", "minecraft.tile.mossy_cobblestone", true);
        public static TileID BOOKSHELF = new TileID(47, "Bookshelf", "minecraft.tile.bookshelf", true);
        public static TileID TNT = new TileID(46, "TNT", "minecraft.tile.tnt", true);
        public static TileID BRICKS = new TileID(45, "Bricks", "minecraft.tile.bricks", true);
        public static TileID SLAB = new TileID(44, "Slab", "minecraft.tile.slab", true);
        public static TileID DOUBLE_SLAB = new TileID(43, "Double Slab", "minecraft.tile.double_slab", true);
        public static TileID IRON_BLOCK = new TileID(42, "Iron Block", "minecraft.tile.iron_block", true);
        public static TileID GOLD_BLOCK = new TileID(41, "Gold Block", "minecraft.tile.gold_block", true);
        public static TileID RED_MUSHROOM = new TileID(40, "Red Mushroom", "minecraft.tile.red_mushroom", true);
        public static TileID BROWN_MUSHROOM = new TileID(39, "Brown Mushroom", "minecraft.tile.brown_mushroom", true);
        public static TileID ROSE = new TileID(38, "Rose", "minecraft.tile.rose", true);
        public static TileID DANDELION = new TileID(37, "Dandelion", "minecraft.tile.dandelion", true);
        public static TileID WHITE_CLOTH = new TileID(36, "White Cloth", "minecraft.tile.white_cloth", true);
        public static TileID LIGHT_CLOTH = new TileID(35, "Light Gray Cloth", "minecraft.tile.light_cloth", true);
        public static TileID DARK_CLOTH = new TileID(34, "Dark Gray Cloth", "minecraft.tile.dark_cloth", true);
        public static TileID ROSE_CLOTH = new TileID(33, "Rose Cloth", "minecraft.tile.rose_cloth", true);
        public static TileID MAGENTA_CLOTH = new TileID(32, "Magenta Cloth", "minecraft.tile.magenta_cloth", true);
        public static TileID PURPLE_CLOTH = new TileID(31, "Purple Cloth", "minecraft.tile.purple_cloth", true);
        public static TileID VIOLET_CLOTH = new TileID(30, "Violet Cloth", "minecraft.tile.violet_cloth", true);
        public static TileID ULTRAMARINE_CLOTH = new TileID(29, "Ultramarine Cloth", "minecraft.tile.ultramarine_cloth", true);
        public static TileID CAPRI_CLOTH = new TileID(28, "Capri Cloth", "minecraft.tile.capri_cloth", true);
        public static TileID CYAN_CLOTH = new TileID(27, "Cyan Cloth", "minecraft.tile.cyan_cloth", true);
        public static TileID SPRING_CLOTH = new TileID(26, "Spring Green Cloth", "minecraft.tile.spring_cloth", true);
        public static TileID GREEN_CLOTH = new TileID(25, "Green Cloth", "minecraft.tile.green_cloth", true);
        public static TileID CHARTREUSE_CLOTH = new TileID(24, "Chartreuse Cloth", "minecraft.tile.chartreuse_cloth", true);
        public static TileID YELLOW_CLOTH = new TileID(23, "Yellow Cloth", "minecraft.tile.yellow_cloth", true);
        public static TileID ORANGE_CLOTH = new TileID(22, "Orange Cloth", "minecraft.tile.orange_cloth", true);
        public static TileID RED_CLOTH = new TileID(21, "Red Cloth", "minecraft.tile.red_cloth", true);
        public static TileID GLASS = new TileID(20, "Glass", "minecraft.tile.glass", true);
        public static TileID SPONGE = new TileID(19, "Sponge", "minecraft.tile.sponge", true);
        public static TileID LEAVES = new TileID(18, "Leaves", "minecraft.tile.leaves", true);
        public static TileID LOG = new TileID(17, "Log", "minecraft.tile.log", true);
        public static TileID COAL_ORE = new TileID(16, "Coal Ore", "minecraft.tile.coal_ore", true);
        public static TileID IRON_ORE = new TileID(15, "Iron Ore", "minecraft.tile.iron_ore", true);
        public static TileID GOLD_ORE = new TileID(14, "Gold Ore", "minecraft.tile.gold_ore", true);
        public static TileID GRAVEL = new TileID(13, "Gravel", "minecraft.tile.gravel", true);
        public static TileID SAND = new TileID(12, "Sand", "minecraft.tile.sand", true);
        public static TileID CALM_LAVA = new TileID(11, "Calm Lava", "minecraft.tile.calm_lava", true);
        public static TileID LAVA = new TileID(10, "Lava", "minecraft.tile.lava", true);
        public static TileID CALM_WATER = new TileID(9, "Calm Water", "minecraft.tile.calm_water", true);
        public static TileID WATER = new TileID(8, "Water", "minecraft.tile.water", true);
        public static TileID UNBREAKABLE = new TileID(7, "UNBREAKABLE", "minecraft.tile.unbreakable", true);
        public static TileID BUSH = new TileID(6, "Bush", "minecraft.tile.bush", true);
        public static TileID WOOD = new TileID(5, "Wood", "minecraft.tile.wood", true);
        public static TileID STONE_BRICK = new TileID(4, "Stone Brick", "minecraft.tile.stone_brick", true);
        public static TileID DIRT = new TileID(3, "Dirt", "minecraft.tile.dirt", true);
        public static TileID GRASS = new TileID(2, "Grass", "minecraft.tile.grass", true);
        public static TileID ROCK = new TileID(1, "Rock", "minecraft.tile.rock", true);
        public static TileID AIR = new TileID(0, "Air", "minecraft.tile.air", false);

        private final int id;
        private final String name;
        private final String idName;
        private final boolean onCreative;

        private TileID(int id, String name, String idName) {
            this.id = id;
            this.name = name;
            this.idName = idName;
            this.onCreative = false;
            TileID.TILE_IDS.put(id, this);
        }

        private TileID(int id, String name, String idName, boolean onCreative) {
            this.id = id;
            this.name = name;
            this.idName = idName;
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

        public String getTranslationKey() {
            return this.idName;
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

                GL11.glTranslatef(-1.6F, 0.9F, 0.5F);
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
        ExtraDrawableHelper.fillX(this.width / 2 - (9 * Slot.SLOT_SIZE / 2), this.height / 2 - (rows * Slot.SLOT_SIZE  /2), this.width / 2 + (9 * Slot.SLOT_SIZE / 2), this.height / 2 + (rows * Slot.SLOT_SIZE  /2) - 4, 0xFF333333);

        drawCenteredString(this.font, "Select block", this.width / 2, 30, 16777215);

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
