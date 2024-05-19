package hu.diablo.sims4.mod.checker.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import hu.diablo.sims4.mod.checker.models.SimsModDetails;

public class ModDetailsCache {
	private static ModDetailsCache instance = new ModDetailsCache();
	
	private volatile ConcurrentMap<String,SimsModDetails> cache = new ConcurrentHashMap<String, SimsModDetails>();
	
	private List<IChangeEventListener> eventListeners;
	
	private static Logger logger = Logger.getLogger(ModDetailsCache.class);
	
	private ModDetailsCache() {
		eventListeners = new ArrayList<IChangeEventListener>();
	}

	synchronized public static ModDetailsCache getInstance() {
		return instance;
	}
	
	public void addDetails(SimsModDetails modDetails) {
		String modName = modDetails.getModName();
		Pattern pattern = Pattern.compile("(.*)_V[1-9]+_?(.*)");
		
		Matcher matcher = pattern.matcher(modName);
		if(matcher.matches()) {
			modName = matcher.group(1);
			if(!matcher.group(2).isEmpty()) {
				modName += "_" + matcher.group(2);
			}
			logger.info("[MATCH]Mod name to be added and/or modified:" + modName);
			modDetails.setModName(modName);
		}
		
		if(cache.containsKey(modName)) {
			SimsModDetails oldModDetails = cache.get(modName);
			
			oldModDetails.addModFile(modDetails.getModFiles().get(0));
			
			final String modNameF = modName;
			
			logger.info("Mod name to be Modified:" + modNameF);
			
			if(eventListeners != null) {
				eventListeners.stream().forEach(item -> {
					item.objectModified(cache.get(modNameF));
				});
			}
		} else {
			modDetails.setId(cache.size()-1);
			cache.put(modDetails.getModName(),modDetails);
			if(eventListeners != null) {
				eventListeners.stream().forEach(item -> {
					item.objectAdded(modDetails);
				});
			}
		}
	}
	
	public SimsModDetails getDetails(String modName) {
		return cache.get(modName);
	}
	
	public void addChangeListener(IChangeEventListener eventListener) {
		eventListeners.add(eventListener);
	}
}
