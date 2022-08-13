package me.kalmemarq.legacygui.resource;

public abstract class TexturePack {
    private String name;
    private String description;

    public abstract Resource getResource(Identifier identifier);

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }
}
