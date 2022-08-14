package me.kalmemarq.legacygui.gui;

public interface Element {
    default void mouseMoved(int mouseX, int mouseY) {
    }

    default boolean mouseClicked(int mouseX, int mouseY, int button) {
        return false;
    }

    default boolean mouseReleased(int mouseX, int mouseY, int button) {
        return false;
    }

    default boolean mouseDragged(int mouseX, int mouseY, int button, int deltaX, int deltaY) {
        return false;
    }

    default boolean mouseScrolled(int mouseX, int mouseY, int amount) {
        return false;
    }

    default boolean keyPressed(int key, int code) {
        return false;
    }

    default boolean keyReleased(int key, int code) {
        return false;
    }

    default boolean charTyped(char chr) {
        return false;
    }

    default boolean isMouseOver(int mouseX, int mouseY) {
        return false;
    }
}
