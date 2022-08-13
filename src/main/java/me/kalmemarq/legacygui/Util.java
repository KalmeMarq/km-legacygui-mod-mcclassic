package me.kalmemarq.legacygui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

public class Util {
    private static File gameDir;

    public enum OperatingSystem {
        LINUX,
        SOLARIS,
        WINDOWS {
            protected String[] getOpenUrlArguments(URL url) {
                return new String[]{"rundll32", "url.dll,FileProtocolHandler", url.toString()};
            }
        },
        MACOS {
            protected String[] getOpenUrlArguments(URL url) {
                return new String[]{"open", url.toString()};
            }
        },
        UNKNOWN;

        public void openUrl(URL var1) {
            try {
                Process var2 = (Process)AccessController.doPrivileged((PrivilegedExceptionAction<Process>) () -> {
                    return Runtime.getRuntime().exec(this.getOpenUrlArguments(var1));
                });
//                Iterator var3 = IOUtils.readLines(var2.getErrorStream()).iterator();
//
//                while(var3.hasNext()) {
//                    String var4 = (String)var3.next();
//                    LegacyGUIMod.LOGGER.info(var4);
//                }

                var2.getInputStream().close();
                var2.getErrorStream().close();
                var2.getOutputStream().close();
            } catch (IOException | PrivilegedActionException var5) {
                LegacyGUIMod.LOGGER.info("Couldn't open url '" + var1 + "'");
            }
        }

        public void openUri(URI uri) {
            CompletableFuture.runAsync(() -> {
                try {
                    this.openUrl(uri.toURL());
                } catch (MalformedURLException e) {
                    LegacyGUIMod.LOGGER.info("Couldn't open uri '" + uri + "'");
                    e.printStackTrace();
                }
            });
        }

        public void openFile(File file) {
            CompletableFuture.runAsync(() -> {
                try {
                    this.openUrl(file.toURI().toURL());
                } catch (MalformedURLException e) {
                    LegacyGUIMod.LOGGER.info("Couldn't open file '" + file +"'");
                    e.printStackTrace();
                }
            });
        }

        protected String[] getOpenUrlArguments(URL url) {
            String urlS = url.toString();
            if ("file".equals(url.getProtocol())) {
                urlS = urlS.replace("file:", "file://");
            }

            return new String[]{"xdg-open", urlS};
        }
    }

    public static OperatingSystem getOperatingSystem() {
        String var0 = System.getProperty("os.name").toLowerCase();
        if (var0.contains("win")) {
            return OperatingSystem.WINDOWS;
        } else if (var0.contains("mac")) {
            return OperatingSystem.MACOS;
        } else if (var0.contains("solaris")) {
            return OperatingSystem.SOLARIS;
        } else if (var0.contains("sunos")) {
            return OperatingSystem.SOLARIS;
        } else if (var0.contains("linux")) {
            return OperatingSystem.LINUX;
        } else {
            return var0.contains("unix") ? OperatingSystem.LINUX : OperatingSystem.UNKNOWN;
        }
    }

    public static File getGameDirectory() {
        if (gameDir != null) {
            return gameDir;
        }

        String string = "minecraft";
        String var1 = System.getProperty("user.home", ".");
        File var2;
        switch(getOperatingSystem()) {
            case LINUX:
            case SOLARIS:
                var2 = new File(var1, '.' + string + '/');
                break;
            case WINDOWS:
                String var3 = System.getenv("APPDATA");
                if (var3 != null) {
                    var2 = new File(var3, "." + string + '/');
                } else {
                    var2 = new File(var1, '.' + string + '/');
                }
                break;
            case MACOS:
                var2 = new File(var1, "Library/Application Support/" + string);
                break;
            default:
                var2 = new File(var1, string + '/');
        }


        if (!var2.exists() && !var2.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + var2);
        } else {
            gameDir = var2;
            return var2;
        }
    }
}
