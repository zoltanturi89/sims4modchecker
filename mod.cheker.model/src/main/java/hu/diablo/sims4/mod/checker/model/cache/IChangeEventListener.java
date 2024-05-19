package hu.diablo.sims4.mod.checker.model.cache;

public interface IChangeEventListener {
	public void objectAdded(Object added);
	public void objectModified(Object modified);
}
