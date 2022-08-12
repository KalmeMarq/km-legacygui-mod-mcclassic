package me.kalmemarq.legacygui.util;

import com.mojang.minecraft.level.tile.Tile;

import java.util.*;
import java.util.function.Consumer;

public class TileID {
    public static TextureSideSupplier DEFAULT_SIDE_SUPPLIER = (tile, side) -> tile.tex;

    public static Map<Integer, TileID> TILE_IDS = new HashMap<>();
    public static List<TileID> CREATIVE_LIST = new ArrayList<>();

    public static TileID UNKNOWN = new TileID(-1, "UNKNOWN", "UNKWONW");
    public static TileID OBDISIAN = new TileID(49, "Obdisian", "minecraft.tile.obsidian", true);
    public static TileID MOSSY = new TileID(48, "Mossy Cobblestone", "minecraft.tile.mossy_cobblestone", true);
    public static TileID BOOKSHELF = new TileID(47, "Bookshelf", "minecraft.tile.bookshelf", true, (tile, side) -> side <= 1 ? 4 : tile.tex);
    public static TileID TNT = new TileID(46, "TNT", "minecraft.tile.tnt", true, (tile, side) -> {
        if (side == 0) {
            return tile.tex + 2;
        } else {
            return side == 1 ? tile.tex + 1 : tile.tex;
        }
    });
    public static TileID BRICKS = new TileID(45, "Bricks", "minecraft.tile.bricks", true);
    public static TileID SLAB = new TileID(44, "Slab", "minecraft.tile.slab", true, (tile, side) -> side <= 1 ? 6 : 5);
    public static TileID DOUBLE_SLAB = new TileID(43, "Double Slab", "minecraft.tile.double_slab", true);
    public static TileID IRON_BLOCK = new TileID(42, "Iron Block", "minecraft.tile.iron_block", true, (tile, side) -> {
        if (side == 1) {
            return tile.tex - 16;
        } else {
            return side == 0 ? tile.tex + 16 : tile.tex;
        }
    });
    public static TileID GOLD_BLOCK = new TileID(41, "Gold Block", "minecraft.tile.gold_block", true, (tile, side) -> {
        if (side == 1) {
            return tile.tex - 16;
        } else {
            return side == 0 ? tile.tex + 16 : tile.tex;
        }
    });
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
    public static TileID LOG = new TileID(17, "Log", "minecraft.tile.log", true, (tile, side) -> {
        if (side == 1) {
            return 21;
        } else {
            return side == 0 ? 21 : 20;
        }
    });
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
    public static TileID GRASS = new TileID(2, "Grass", "minecraft.tile.grass", true, (tile, side) -> {
        if (side == 1) {
            return 0;
        } else {
            return side == 0 ? 2 : 3;
        }
    });
    public static TileID ROCK = new TileID(1, "Rock", "minecraft.tile.rock", true);
    public static TileID AIR = new TileID(0, "Air", "minecraft.tile.air", false);

    private final int id;
    private Tile tile;
    private final String name;
    private final String idName;
    private final boolean onCreative;
    private final TextureSideSupplier textureSideSupplier;

    private TileID(int id, String name, String idName) {
        this(id, name, idName, false, DEFAULT_SIDE_SUPPLIER);
    }

    private TileID(int id, String name, String idName, TextureSideSupplier textureSideSupplier) {
        this(id, name, idName, false, textureSideSupplier);
    }

    private TileID(int id, String name, String idName, boolean onCreative) {
        this(id, name, idName, onCreative, DEFAULT_SIDE_SUPPLIER);
    }

    private TileID(int id, String name, String idName, boolean onCreative, TextureSideSupplier textureSideSupplier) {
        this.id = id;
        this.name = name;
        this.idName = idName;
        this.onCreative = onCreative;
        this.textureSideSupplier = textureSideSupplier;
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
        if (this.tile == null) {
            this.tile = Tile.tiles[this.id];
        }
        return this.tile;
    }

    public int getId() {
        return this.id;
    }

    public int getTextureSide(int side) {
        return this.textureSideSupplier.get(this.getTile(), side);
    }

    @FunctionalInterface
    public interface TextureSideSupplier {
        int get(Tile tile, int side);
    }
}
