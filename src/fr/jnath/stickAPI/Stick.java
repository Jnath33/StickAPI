package fr.jnath.stickAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

public class Stick {
	private String _name;
	private String _description;
	private int _defaultDurability;
	private BiConsumer<Player, ImmutablePair<Location, Block>> _fonctionStickUse;
	private static List<Stick> listOfStick = new ArrayList<Stick>();
	private List<PotionEffect> _potionEffect;
	private static HashMap<String, Stick> nameToStick=new HashMap<String, Stick>();
	private static HashMap<Integer, String> idToName=new HashMap<Integer, String>();
	public Stick(String name, String description, int defaultDurability, BiConsumer<Player, ImmutablePair<Location, Block>> consumer, int id) {
		_name=name;
		_description=description;
		_defaultDurability=defaultDurability;
		_fonctionStickUse= consumer;
		listOfStick.add(this);
		_potionEffect=null;
		nameToStick.put(name, this);
		idToName.put(id, name);
	}
	public Stick(String name, String description, int defaultDurability, List<PotionEffect> potionEffect, int id) {
		_name=name;
		_description=description;
		_defaultDurability=defaultDurability;
		_fonctionStickUse= (player, pair) -> addPotionEffect(player);
		listOfStick.add(this);
		_potionEffect=potionEffect;
		nameToStick.put(name, this);
		idToName.put(id, name);
	}
	public void addPotionEffect(Player player) {
		for(PotionEffect potion : _potionEffect) {
			player.addPotionEffect(potion);
		}
	}
	public static ItemStack createStick(String name) {
		ItemStack item =new ItemStack(Material.STICK);
		ItemMeta itemM = item.getItemMeta();
		itemM.setDisplayName(name);
		itemM.addEnchant(Enchantment.DURABILITY, 1, true);
		itemM.setLore(Arrays.asList(nameToStick.get(name).getDescription(), "", "§c"+String.valueOf(nameToStick.get(name).getDefaultDurability())
								+"/"+String.valueOf(nameToStick.get(name).getDefaultDurability())));
		itemM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemM);
		return item;
	}
	public static String getNameById(Integer inte) {
		return idToName.get(inte);
	}
	public static void removeDurability(int durabilityRemove, ItemStack stickItem, Stick stick, int slot, Player player) {
		List<String> list = stickItem.getItemMeta().getLore();
		String dura = list.get(2).split("/")[0];
		Integer duraInt=0;
		for(char chr:dura.toCharArray()) {
			if(chr=='0'||chr=='1'||chr=='2'||chr=='3'||chr=='4'||chr=='5'||chr=='6'||chr=='7'||chr=='8'||chr=='9') {
				duraInt*=10;
				duraInt+=Integer.valueOf(String.valueOf(chr));
			}
		}
		duraInt--;
		if(duraInt<=0){
			ItemMeta im=stickItem.getItemMeta();
			player.getInventory().clear(slot);
			stickItem.setItemMeta(im);
		}
		ItemMeta im=stickItem.getItemMeta();
		list.set(2, "§c"+String.valueOf(duraInt)+"/"+String.valueOf(stick.getDefaultDurability()));
		im.setLore(list);
		stickItem.setItemMeta(im);
	}
	public int getDefaultDurability() {
		return _defaultDurability;
	}
	public String getDescription() {
		return _description;
	}
	public String getName() {
		return _name;
	}
	public static boolean stickUse(String name, Player player, ItemStack stickItem, Location loc, Block block, int slot) {
		for(Stick stick : listOfStick) {
			if(name.contains(stick.getName())) {
				stick._fonctionStickUse.accept(player, new ImmutablePair<Location, Block>(loc, block));
				removeDurability(1, stickItem, stick, slot, player);
				return true;
			}
		}
		return false;
	}
}