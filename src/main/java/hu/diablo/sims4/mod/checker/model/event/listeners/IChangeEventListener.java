package hu.diablo.sims4.mod.checker.model.event.listeners;

public interface IChangeEventListener {
	public void objectAdded(Object added);
	public void objectModified(Object modified);
	public void objectDeleted(Object deleted);
}
