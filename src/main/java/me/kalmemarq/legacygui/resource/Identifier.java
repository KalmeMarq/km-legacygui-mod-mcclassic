package me.kalmemarq.legacygui.resource;

import me.kalmemarq.legacygui.LegacyGUIMod;

import javax.naming.InvalidNameException;
import java.util.Objects;

public class Identifier {
    private final String namespace;
    private final String path;

    public Identifier(String namespace, String path) {
        this(new String[] { namespace, path });
    }

    public Identifier(String path) {
        this(split(path));
    }

    private Identifier(String[] identifier) {
        this.namespace = identifier[0];
        this.path = identifier[1];

        if (isValidNamespace(this.namespace)) {
            LegacyGUIMod.LOGGER.info("Namespace can only have [a-zA-Z0-9_]: " + this);
        }

        if (isValidPath(this.path)) {
            LegacyGUIMod.LOGGER.info("Path can only have [a-zA-Z0-9_.]: " + this);
        }
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getPath() {
        return this.path;
    }

    public String toString() {
        return this.namespace + ":" + this.path;
    }

    private static String[] split(String path) {
        String[] id = { "minecraft", path };

        int sepIdx = path.indexOf(":");

        if (sepIdx >= 0) {
            id[1] = path.substring(sepIdx + 1);

            if (sepIdx >= 1) {
                id[0] = path.substring(0, sepIdx);
            }
        }

        return id;
    }

    private static boolean isValidNamespace(String namespace) {
        for (int i = 0; i < namespace.length(); i++) {
            if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".indexOf(namespace.charAt(i)) < 0) {
               return false;
            }
        }

        return true;
    }

    private static boolean isValidPath(String path) {
        for (int i = 0; i < path.length(); i++) {
            if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_.".indexOf(path.charAt(i)) < 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Identifier) {
            Identifier id = (Identifier) obj;
            return id.namespace.equals(this.namespace) && id.path.equals(this.path);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.namespace + ":" + this.path);
    }
}
