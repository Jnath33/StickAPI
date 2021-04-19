package fr.jnath.stickAPI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Listeners implements Listener{
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack it = event.getItem();
		if(it==null||it.getItemMeta()==null||it.getItemMeta().getDisplayName()==null)return;
		if(event.getAction()==Action.RIGHT_CLICK_BLOCK||event.getAction()==Action.RIGHT_CLICK_AIR) {
			if(event.getAction()==Action.RIGHT_CLICK_AIR) {
				if(Stick.stickUse(it.getItemMeta().getDisplayName(),player,it,player.getLocation(),null,player.getInventory().getHeldItemSlot())) event.setCancelled(true);
			}else {
				if(Stick.stickUse(it.getItemMeta().getDisplayName(),player,it,player.getLocation(),event.getClickedBlock(),player.getInventory().getHeldItemSlot())) event.setCancelled(true);
			}
		}
	}
}
