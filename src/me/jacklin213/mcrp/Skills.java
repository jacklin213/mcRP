package me.jacklin213.mcrp;

import me.jacklin213.mcrp.mcRP;
import net.milkycraft.Scheduler.Scheduler;
import net.milkycraft.Utility.PlayerTimer;
import net.milkycraft.Utility.Time;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Skills {
	public static mcRP plugin;
	
	public Skills (mcRP instance){
		plugin = instance;
	}
	//SUPERSPEED
	public static void SuperSpeed(Player p){

	if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
		p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
	}
	else{
        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
        p.sendMessage(ChatColor.GRAY + "You have activated your SuperSpeed ability");
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
	}
	
	
}
	
	//BLESS
	public static void Bless(Player p, String args[]){
		if(args.length == 1){
		if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
			p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
		}
		else{
	        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
	        p.sendMessage(ChatColor.GRAY + "You have activated your Bless ability");
	        p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
		}
		}
		else if(args.length == 2){
			Player target = Bukkit.getPlayerExact(args[1]);
			if(!target.isOnline()){
				p.sendMessage(ChatColor.RED + "Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
			}
			else if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
				p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
			}
			else{
		        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
		        p.sendMessage(ChatColor.GRAY + "You have activated your Bless ability on " + target.getName());
		        target.sendMessage(ChatColor.GRAY + "You have been healed by " + p.getName());
		        target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200, 1));
			}
		}
	}
	
	//MIGHT
	public static void Might(Player p){
		
		if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
			p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
		}
		else{
	        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
	        p.sendMessage(ChatColor.GRAY + "You have activated your Might ability");
	        p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
		}
		
	}
	
	//GILLS
	public static void Gills(Player p){
		
		if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
			p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
		}
		else{
	        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
	        p.sendMessage(ChatColor.GRAY + "You have activated your Gills ability");
	        p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 1));
		}
		
	}
	
	//SuperJump
	public static void SuperJump(Player p){
		
		if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
			p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
		}
		else{
	        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
	        p.sendMessage(ChatColor.GRAY + "You have activated your SuperJump ability");
	        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
		}
		
	}
	
	//Martyboom
	public static void Martyboom(Player p){
		Location l = p.getLocation();
		if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
			p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
		}
		else{
	        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
	        p.sendMessage(ChatColor.GRAY + "You have activated your martyboom ability");
	        p.getWorld().createExplosion(l, 4 );
	        if(p.getHealth() > 0){
	        p.setHealth(0);
		}
		}
		
		
	}
	

	//SuperPunch
	public static void SuperPunch(Player p, String args[]){

		Player target = Bukkit.getPlayerExact(args[1]);
		if(args.length == 1){
			
			p.sendMessage(ChatColor.RED + "You NEED to define a player to use this skill");
			
		}
		else if(args.length == 2){
		if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
			p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
		}
		else if(!target.isOnline()){
			p.sendMessage(ChatColor.RED + "Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
		}
		else{
			target.setHealth(target.getHealth() - 5);
	        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
	        p.sendMessage(ChatColor.GRAY + "You have activated your SuperPunch ability on " + target.getName());
	        if(p.getHealth() > 0){
	        p.setHealth(0);
		}
		}
		}
		
	}
	
	//Confuse
	public static void Confuse(Player p, String args[]){
		Player target = Bukkit.getPlayerExact(args[1]);
		if(args.length == 1){
			
			p.sendMessage(ChatColor.RED + "You NEED to define a player to use this skill");
			
		}
		else if(args.length == 2){
		if(PlayerTimer.isCoolingDown(p.getName(), Time.EXONE)){
			p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.EXONE) + " second cooldown");
		}
		else if(!target.isOnline()){
			p.sendMessage(ChatColor.RED + "Player" + ChatColor.GRAY + args[0] + ChatColor.RED + "is not online!");
		}
		else{
	        Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.EXONE));
	        target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1));
	        p.sendMessage(ChatColor.GRAY + "You have activated your Confuse ability on " + target.getName());
	        target.sendMessage(ChatColor.GRAY + "You were confused by " + p.getName());
		      
		      
	}
		}
	}

}
