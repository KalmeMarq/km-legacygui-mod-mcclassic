package me.kalmemarq.legacygui.gui.hud;

public class ChatHudLine {
    public String content;
    public int creationTick;

    public ChatHudLine(int creationTick, String string) {
        this.content = string;
        this.creationTick = creationTick;
    }
}
