package me.kalmemarq.legacygui.resource;

import java.io.InputStream;
import java.io.Reader;

public interface Resource {
    String getPath();
    InputStream getInputStream();
    Reader getReader();
    String getTexturePackName();
}
