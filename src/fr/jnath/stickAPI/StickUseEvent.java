package fr.jnath.stickAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StickUseEvent extends Event {
	private Player player;
	private Stick stick;
	
	public StickUseEvent(Player player, Stick stick) {
		 this.stick=stick;
		 this.player=player;
	}
	@Override
	public String getEventName() {
		// TODO Auto-generated method stub
		return super.getEventName();
	}
	public Player getPlayer() {
		return player;
	}
	public Stick getStick() {
		return stick;
	}
	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return null;
	}

}
