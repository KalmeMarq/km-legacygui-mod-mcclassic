package me.kalmemarq.legacygui.mixin;

import com.mojang.minecraft.Minecraft;
import com.mojang.minecraft.ResourceDownloadThread;
import me.kalmemarq.legacygui.LegacyGUIMod;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;

@Mixin(ResourceDownloadThread.class)
public class ResourceDownloadThreadMixin {
    @Shadow private Minecraft minecraft;
    @Shadow private File workingDirectory;
    private boolean c = false;

    @Inject(method = "run", at = @At("HEAD"), cancellable = true)
    private void runIJ(CallbackInfo ci) {
//        try {
//            URL url = new URL("http://rm1.kl.com.ua/resources_ak47");
//            DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
//            Document document = documentbuilder.parse(url.openStream());
//            NodeList nodelist = document.getElementsByTagName("Contents");
//
//            for(int i = 0; i < 2; ++i) {
//                for(int j = 0; j < nodelist.getLength(); ++j) {
//                    Node node = nodelist.item(j);
//                    if (node.getNodeType() == 1) {
//                        Element element = (Element)node;
//                        String s = ((Element)element.getElementsByTagName("Key").item(0)).getChildNodes().item(0).getNodeValue();
//                        long l = Long.parseLong(((Element)element.getElementsByTagName("Size").item(0)).getChildNodes().item(0).getNodeValue());
//                        if (l > 0L) {
//                            this.downloadAndInstallResource(url, s, l, i);
//                            if (this.c) {
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception var13) {
//            this.loadResource(this.workingDirectory, "");
//            var13.printStackTrace();
//        }

//        try {
//            this.loadResource(this.workingDirectory, "");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println("Adding sound resources...");
        File var1 = new File(new File(this.workingDirectory, "sound"), "step");

        for(int var2 = 1; var2 <= 4; ++var2) {
            this.minecraft.soundManager.loadSound(new File(var1, "wood" + var2 + ".ogg"), "step/wood" + var2 + ".ogg");
            this.minecraft.soundManager.loadSound(new File(var1, "stone" + var2 + ".ogg"), "step/stone" + var2 + ".ogg");
            this.minecraft.soundManager.loadSound(new File(var1, "grass" + var2 + ".ogg"), "step/grass" + var2 + ".ogg");
            this.minecraft.soundManager.loadSound(new File(var1, "gravel" + var2 + ".ogg"), "step/gravel" + var2 + ".ogg");
        }

        ModContainer container = FabricLoader.getInstance().getModContainer(LegacyGUIMod.MOD_ID).get();
        this.minecraft.soundManager.loadSound(container.findPath("click.ogg").get().toFile(), "click.ogg");

        System.out.println("Adding music resources...");
        File var4 = new File(this.workingDirectory, "music");

        for(int var3 = 1; var3 <= 3; ++var3) {
            this.minecraft.soundManager.addMusic("calm" + var3 + ".ogg", new File(var4, "calm" + var3 + ".ogg"));
        }


        ci.cancel();
    }

    private void downloadAndInstallResource(URL url, String s, long l, int i) {
        try {
            int j = s.indexOf("/");
            String s1 = s.substring(0, j);
            if (!s1.equals("sound") && !s1.equals("newsound")) {
                if (i != 1) {
                    return;
                }
            } else if (i != 0) {
                return;
            }

            File file = new File(this.workingDirectory, s);
            if (!file.exists() || file.length() != l) {
                file.getParentFile().mkdirs();
                String s2 = s.replaceAll(" ", "%20");
                this.downloadResource(new URL(url, s2), file, l);
                if (this.c) {
                    return;
                }
            }

            this.installResource(s, file);
        } catch (Exception var10) {
            var10.printStackTrace();
        }
    }

    private void loadResource(File file, String s) {
        File[] afile = file.listFiles();

        for(int i = 0; i < afile.length; ++i) {
            if (afile[i].isDirectory()) {
                this.loadResource(afile[i], s + afile[i].getName() + "/");
            } else {
                try {
                    this.installResource(s + afile[i].getName(), afile[i]);
                } catch (Exception var6) {
                    System.out.println("Failed to add " + s + afile[i].getName());
                }
            }
        }

    }

    public void installResource(String s, File file) {
        int i = s.indexOf("/");
        String s1 = s.substring(0, i);
        s = s.substring(i + 1);
        if (s1.equalsIgnoreCase("sound")) {
            this.minecraft.soundManager.loadSound(file, s);
        } else if (s1.equalsIgnoreCase("newsound")) {
            this.minecraft.soundManager.loadSound(file, s);
        } else if (s1.equalsIgnoreCase("music")) {
            this.minecraft.soundManager.addMusic(s, file);
        }

    }

    public void reloadResources() {
        this.loadResource(this.workingDirectory, "");
    }

    private void downloadResource(URL url, File file, long l) throws IOException {
        byte[] abyte0 = new byte[4096];
        DataInputStream datainputstream = new DataInputStream(url.openStream());
        DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));

        int j;
        while((j = datainputstream.read(abyte0)) >= 0) {
            dataoutputstream.write(abyte0, 0, j);
            if (this.c) {
                return;
            }
        }

        datainputstream.close();
        dataoutputstream.close();
    }

    private void a(URL var1, File var2) throws IOException {
        byte[] var3 = new byte[4096];
        DataInputStream var5 = new DataInputStream(var1.openStream());
        DataOutputStream var6 = new DataOutputStream(new FileOutputStream(var2));

        int var4;
        while((var4 = var5.read(var3)) >= 0) {
            var6.write(var3, 0, var4);
            if (this.c) {
                return;
            }
        }

        var5.close();
        var6.close();
    }


    public final void a() {
        this.c = true;
    }
}
