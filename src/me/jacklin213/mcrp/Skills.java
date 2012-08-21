package me.jacklin213.mcrp;

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

	public Skills(mcRP instance) {
		plugin = instance;
	}

	public static void Yolo() {

	}

	// SUPERSPEED
	public static void SuperSpeed(Player p) {

		if (PlayerTimer.isCoolingDown(p.getName(), Time.THREEMINCD)) {
			p.sendMessage(ChatColor.GRAY
					+ "You still have a "
					+ PlayerTimer.getRemainingTime(p.getName(), Time.THREEMINCD)
					+ " second cooldown");
		} else {
			Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin,
					p.getName(), Time.THREEMINCD));
			p.sendMessage(ChatColor.GRAY
					+ "You have activated your super speed ability");
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
		}

	}

	// BLESS
	public static void Bless(Player p, String args[]) {
		if (args.length == 0) {
			if (PlayerTimer.isCoolingDown(p.getName(), Time.THREEMINCD)) {
				p.sendMessage(ChatColor.GRAY
						+ "You still have a "
						+ PlayerTimer.getRemainingTime(p.getName(),
								Time.THREEMINCD) + " second cooldown");
			} else {
				Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin,
						p.getName(), Time.THREEMINCD));
				p.sendMessage(ChatColor.GRAY
						+ "You have activated your Bless ability");
				p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 200,
						1));
			}
		} else if (args.length == 1) {
			Player target = Bukkit.getPlayerExact(args[0]);
			if (!target.isOnline()) {
				p.sendMessage(ChatColor.RED + "Player" + ChatColor.GRAY
						+ args[0] + ChatColor.RED + "is not online!");
			} else if (PlayerTimer.isCoolingDown(p.getName(), Time.THREEMINCD)) {
				p.sendMessage(ChatColor.GRAY
						+ "You still have a "
						+ PlayerTimer.getRemainingTime(p.getName(),
								Time.THREEMINCD) + " second cooldown");
			} else {
				Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin,
						p.getName(), Time.THREEMINCD));
				p.sendMessage(ChatColor.GRAY
						+ "You have activated your Bless ability on " + target);
				target.sendMessage(ChatColor.GRAY + "You have been healed by "
						+ p.getName());
				target.addPotionEffect(new PotionEffect(PotionEffectType.HEAL,
						200, 1));
			}
		}
	}
	
	//MIGHT
    public static void Might(Player p){
           
            if(PlayerTimer.isCoolingDown(p.getName(), Time.THREEMINCD)){
                    p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.THREEMINCD) + " second cooldown");
            }
            else{
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.THREEMINCD));
            p.sendMessage(ChatColor.GRAY + "You have activated your Might ability");
            p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 1));
            }
           
    }
   
    //GILLS
    public static void Gills(Player p){
           
            if(PlayerTimer.isCoolingDown(p.getName(), Time.THREEMINCD)){
                    p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.THREEMINCD) + " second cooldown");
            }
            else{
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.THREEMINCD));
            p.sendMessage(ChatColor.GRAY + "You have activated your Gills ability");
            p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 1));
            }
           
    }
    
    //SuperJump
    public static void SuperJump(Player p){
           
            if(PlayerTimer.isCoolingDown(p.getName(), Time.THREEMINCD)){
                    p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.THREEMINCD) + " second cooldown");
            }
            else{
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.THREEMINCD));
            p.sendMessage(ChatColor.GRAY + "You have activated your SuperJump ability");
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 200, 1));
            }
           
    }
   
    //Martyboom
    public static void Martyboom(Player p){
            Location l = p.getLocation();
            if(PlayerTimer.isCoolingDown(p.getName(), Time.THREEMINCD)){
                    p.sendMessage(ChatColor.GRAY + "You still have a " + PlayerTimer.getRemainingTime(p.getName(), Time.THREEMINCD) + " second cooldown");
            }
            else{
            Scheduler.schedulePlayerCooldown(Scheduler.schedule(plugin, p.getName(), Time.THREEMINCD));
            p.sendMessage(ChatColor.GRAY + "You have activated your martyboom ability");
            p.getWorld().createExplosion(l, 4 );
            if(p.getHealth() > 0){
            p.setHealth(0);
            }
            }
           
    }

	// end of skills class
}
