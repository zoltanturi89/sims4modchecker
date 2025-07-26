package hu.diablo.sims4.mod.checker.script;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ScriptModPackage {
    ZipFile scriptArcive;
    
    public ScriptModPackage(String modLoc) throws ZipException, IOException {
	this.scriptArcive = new ZipFile(new File(modLoc));
    }
}
