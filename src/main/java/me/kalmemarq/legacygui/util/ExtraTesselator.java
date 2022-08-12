package me.kalmemarq.legacygui.util;

public class ExtraTesselator {
    private final BufferBuilder builder;
    private static final ExtraTesselator INSTANCE = new ExtraTesselator();

    public static ExtraTesselator getInstance() {
        return INSTANCE;
    }

    public ExtraTesselator(int capacity) {
        this.builder = new BufferBuilder(capacity);
    }

    public ExtraTesselator() {
        this(2097152);
    }

    public BufferBuilder getBuilder() {
        return this.builder;
    }

    public static void endAndDraw() {
        INSTANCE.builder.draw();
    }

    public void end() {
        this.builder.draw();
    }
}

