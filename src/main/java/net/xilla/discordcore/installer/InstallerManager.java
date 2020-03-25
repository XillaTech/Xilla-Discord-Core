package net.xilla.discordcore.installer;

import net.xilla.discordcore.api.manager.ManagerObject;
import net.xilla.discordcore.api.manager.ManagerParent;

public class InstallerManager extends ManagerParent {

    public void addInstaller(InstallerObject installerObject) {
        addObject(installerObject);
    }

    public void removeInstaller(String key) {
        removeObject(key);
    }

    public void install() {
        for(ManagerObject object : getList()) {
            InstallerObject installerObject = (InstallerObject)object;
            installerObject.install();
        }
        reload();
    }

}
