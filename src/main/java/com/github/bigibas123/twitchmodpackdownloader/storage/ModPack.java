package com.github.bigibas123.twitchmodpackdownloader.storage;

import lombok.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ModPack {
    private final String name;
    private final String version;
    private final String author;
    private final ArrayList<Mod> files;
    private final Minecraft minecraft;
    @Setter
    private String downloadfolder;

    @AllArgsConstructor
    @ToString
    @Getter
    public static class Mod {

        private final long projectID;
        private final long fileID;
        private final boolean required;
    }

    @AllArgsConstructor
    @Getter
    public static class Minecraft {
        private final String version;
        private final ArrayList<ModLoaders> modLoaders;

        @AllArgsConstructor
        @Getter
        public class ModLoaders {
            private final String id;
            private final boolean primary;
        }
    }

    public String[] toRow() {
        return new String[]{this.getName(), this.getVersion(), this.getAuthor(), this.getMinecraft().getVersion(), String.valueOf(this.getFiles().size())};
    }

    public DefaultMutableTreeNode getTree(){
        DefaultMutableTreeNode tree = new DefaultMutableTreeNode(this.name);
        tree.add(new DefaultMutableTreeNode("Author:"+this.getAuthor()));
        tree.add(new DefaultMutableTreeNode("Version:"+this.getVersion()));
        DefaultMutableTreeNode mc = new DefaultMutableTreeNode("Minecraft");
        mc.add(new DefaultMutableTreeNode("Version:"+this.getMinecraft().getVersion()));
        DefaultMutableTreeNode mls = new DefaultMutableTreeNode("Modloaders");
        for (Minecraft.ModLoaders ml:this.getMinecraft().getModLoaders()) {
            DefaultMutableTreeNode tmp = new DefaultMutableTreeNode("Modloader:"+ml.getId());
            tmp.add(new DefaultMutableTreeNode("ID:"+ml.getId()));
            tmp.add(new DefaultMutableTreeNode("Primary:"+ml.isPrimary()));
            mls.add(tmp);
        }
        mc.add(mls);
        tree.add(mc);

        DefaultMutableTreeNode mods = new DefaultMutableTreeNode("Mods");
        HashMap<Long,DefaultMutableTreeNode> modList = new HashMap<>();
        for (Mod mod:this.getFiles()){
            if(!modList.containsKey(mod.getProjectID())){
                modList.put(mod.getProjectID(),new DefaultMutableTreeNode(mod.getProjectID()));
            }
            modList.get(mod.getProjectID()).add(new DefaultMutableTreeNode("ID:"+mod.getFileID()));

        }
        for (HashMap.Entry<Long,DefaultMutableTreeNode> entry:modList.entrySet()){
            mods.add(entry.getValue());
        }
        tree.add(mods);

        tree.add(new DefaultMutableTreeNode("DownloadFolder:"+this.getDownloadfolder()));

        return tree;
    }
}
