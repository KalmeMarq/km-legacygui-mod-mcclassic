package me.kalmemarq.legacygui.gui.widget;

import com.mojang.minecraft.Minecraft;
import me.kalmemarq.legacygui.gui.widget.entries.IndependentEntry;
import me.kalmemarq.legacygui.gui.widget.entries.ModListEntry;
import me.kalmemarq.legacygui.gui.screen.ModsScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.*;

public class ModListWidget extends AbstractSelectionList<ModListEntry> {
    public static final boolean DEBUG = false;
    private final ModsScreen parent;
    private List<ModContainer> mods = null;
    private final Set<ModContainer> addedMods = new HashSet<>();
    private String selectedModId = null;
    private boolean scrolling;

    public ModListWidget(Minecraft mc, int width, int height, int y0, int y1, int itemHeight, String searchTerm, ModListWidget list, ModsScreen parent) {
        super(mc, width, height, y0, y1, itemHeight);
        this.parent = parent;
        if (list != null) {
            this.mods = list.mods;
        }
        this.filter(searchTerm, false);
        setScrollAmount(parent.getScrollPercent() * Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4)));
    }

    @Override
    public int addEntry(ModListEntry entry) {
        if (addedMods.contains(entry.container)) {
            return 0;
        }
        addedMods.add(entry.container);
        int i = super.addEntry(entry);
        if (entry.getModContainer().getMetadata().getId().equals(selectedModId)) {
            setSelected(entry);
        }
        return i;
    }

    @Override
    public void setSelected(ModListEntry entry) {
        super.setSelected(entry);
        selectedModId = entry.getModContainer().getMetadata().getId();
        parent.updateSelectedEntry(getSelected());
    }

    @Override
    public void setScrollAmount(double amount) {
        super.setScrollAmount(amount);
        int denominator = Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));
        if (denominator <= 0) {
            parent.updateScrollPercent(0);
        } else {
            parent.updateScrollPercent(getScrollAmount() / Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4)));
        }
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width - 6;
    }

    @Override
    public int getRowWidth() {
        return this.width - (Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4)) > 0 ? 18 : 12);
    }

    @Override
    public int getRowLeft() {
        return this.x0 + 6;
    }

    public int getWidth() {
        return width;
    }

    public int getTop() {
        return this.y0;
    }

    public ModsScreen getParent() {
        return this.parent;
    }

    @Override
    protected int getMaxPosition() {
        return super.getMaxPosition() + 4;
    }

    public void reloadFilters() {
        filter("", true, false);
    }

    public void filter(String searchTerm, boolean refresh) {
        filter(searchTerm, refresh, true);
    }

    private void filter(String searchTerm, boolean refresh, boolean search) {
        this.clearEntries();
        addedMods.clear();
        Collection<ModContainer> mods = new HashSet<>(FabricLoader.getInstance().getAllMods());

        if (DEBUG) {
            mods = new ArrayList<>(mods);
//			mods.addAll(TestModContainer.getTestModContainers());
        }

        if (this.mods == null || refresh) {
            this.mods = new ArrayList<>();
            this.mods.addAll(mods);
//            this.mods.sort(ModMenuConfig.SORTING.getValue().getComparator());
        }

        List<ModContainer> matched = new ArrayList<>(mods); //ModSearch.search(parent, searchTerm, this.mods);

        for (ModContainer mod : matched) {
            String modId = mod.getMetadata().getId();

            //Hide parent lib mods when the config is set to hide
//            if (mod.getBadges().contains(Mod.Badge.LIBRARY) && !ModMenuConfig.SHOW_LIBRARIES.getValue()) {
//                continue;
//            }

            this.addEntry(new IndependentEntry(this.minecraft, mod, this));
        }

        if (parent.getSelectedEntry() != null && !children().isEmpty() || this.getSelected() != null && getSelected().getModContainer() != parent.getSelectedEntry().getModContainer()) {
            for (ModListEntry entry : children()) {
                if (entry.getModContainer().equals(parent.getSelectedEntry().getModContainer())) {
                    setSelected(entry);
                }
            }
        } else {
            if (getSelected() == null && !children().isEmpty() && getEntry(0) != null) {
                setSelected(getEntry(0));
            }
        }

        if (getScrollAmount() > Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4))) {
            setScrollAmount(Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4)));
        }
    }
}
