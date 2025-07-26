package hu.diablo.sims4.mod.checker.script;

import java.io.BufferedInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ScriptModPackage {
    ZipFile scriptArcive;

    public ScriptModPackage(String modLoc) {
	try {
	    
	   
	    
	    this.scriptArcive = new ZipFile(new File(modLoc));
	    
	    
	    this.scriptArcive.stream().forEach(zipE -> {
		try {
		    BufferedInputStream bis = new BufferedInputStream(
			    this.scriptArcive.getInputStream(zipE));
		    String fileData = IOUtils.toString(bis, StandardCharsets.UTF_8);

		    log.info("FileName:{}, fileData:{}",zipE.getName(),fileData);
		} catch (Exception ex) {
		    log.error(ex);
		}
	    });

	} catch (Exception e) {
	    log.error(e);
	}
    }
}
