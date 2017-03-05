package net.maattah.flare.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import net.maattah.flare.Main;

public enum Lang {
    PREFIX("prefix", "&8[&6Flare&8]&7 "),
    INVALID_PLAYER("invalid-player", "The player you specified does not exist."),
    PLAYER_ONLY("player-only", "&7You must be a player to use this command."),
    NO_PERMS("no-perms", "&c&l(!) &cYou do not have permission to use this."),
    INVALID_NUMBER("invalid-number", "Please use a valid number."),
    
    FLY_ENABLED("fly.enabled", "Fly mode set to &atrue &7for %player%."),
    FLY_DISABLED("fly.disabled", "Fly mode set to &cfalse &7for %player%."),
    FLY_ENABLED_OTHER("fly.enabled-other", "Your fly mode was set to &aTrue &7by %player%."),
    FLY_DISABLED_OTHER("fly.disabled-other", "Your fly mode was set to &cFalse &7by %player%."),
    
    GAMEMODE_CREATIVE("gamemode.creative", "You have set your gamemode to &aCreative&7."),
    GAMEMODE_SURVIVAL("gamemode.survival", "You have set your gamemode to &cSurvival&7."),
	GAMEMODE_CREATIVE_OTHER("gamemode.creative-other", "You have set %player%'s gamemode to &aCreative&7."),
	GAMEMODE_SURVIVAL_OTHER("gamemode.survival-other", "You have set %player%'s gamemode to &cSurvival&7."),
	GAMEMODE_CREATIVE_OTHER2("gamemode.creative-other2", "Your gamemode was set to &aCreative&7."),
	GAMEMODE_SURVIVAL_OTHER2("gamemode.survival-other2", "Your gamemode was set to &cSurvival&7."),
	
	REQUEST_USAGE("request.usage", "Usage: /request <message>"),
	REQUEST_STAFF_MESSAGE("request.staff-message", "&6Flare &8»&7 %sender% has requested assistance.\n    &6Reason:&7 %reason%"),
	REPORT_USAGE("report.usage", "Usage: /report <player> <reason>"),
	REPORT_STAFF_MESSAGE("report.staff-message", "&6Flare &8»&7 %sender% has reported %target%.\n    &6Reason:&7 %reason%"),
	
	TELEPORT("teleport.telported", "You have been teleported to %player%."),
	TELEPORT_HERE("teleport.telported-here", "You have teleported %player% to yourself."),
	TP_USAGE("teleport.tp-usage", "Usage: /tp <player>"),
	TPHERE_USAGE("teleport.tp-usage", "Usage: /tphere <player>"),
	
	FREEZE_NOHIT("freeze.nohit", "This player is frozen, you may not hit them."),
	FREEZE_BREAK("freeze.break", "You may not break blocks while frozen."),
	FREEZE_PLACE("freeze.place", "You may not place blocks while frozen."),
	FREEZE_ALERT("freeze.alert", "&c&l%player% logged out while frozen!"),
	FREEZE("freeze.freeze", "You have just frozen %player%."),
	UNFREEZE("freeze.unfreeze", "You have just unfrozen %player%."),
	UNFREEZE_PLAYER("freeze.unfreeze-player", "You have been unfrozen."),
	
	DEATHBAN_BAN_MESSAGE("deathban.ban_message", "&cYou are currently deathbanned. You currently have <time> remaining."),
	DEATHBAN_JOIN_AGAIN_FOR_REVIVE("deathban.join_again_for_revive", "&7You have &3<amount> &7live(s). Please join again to use &3Live&7."),
	DEATHBAN_COMMAND_CHECK_USAGE("deathban.command.check_usage", "&3Usage&7: /&3deathban check [player]"),
	DEATHBAN_COMMAND_REVIVE_USAGE("deathban.command.revive_usage", "&3Usage&7: /&3deathban revive [player]"),
	DEATHBAN_COMMAND_INVENTORY_ROLLBACK_USAGE("deathban.command.inventory_rollback_usage", "&3Usage&7: /&3deathban inventory rollback [player]"),
	DEATHBAN_PLAYER_NOT_DEATHBANNED("deathban.player_not_deathbanned", "&7Player &3<player> &7is not &3DeathBanned&7."),
	DEATHBAN_SUCCESSFULLY_REVIVED_PLAYER("deathban.successfully_revived_player", "&7You successfully revived &3<player>&7."),
	DEATHBAN_SUCCESSFULLY_ROLLBACKED_INVENTORY("deathban.successfully_rollbacked_inventory", "&7Successfully rollbacked &3<player>'s &7inventory."),
	DEATHBAN_SUCCESSFULLY_ROLLBACKED_INVENTORY_PLAYER("deathban.successfully_rollbacked_inventory_player", "&7Your inventory was rollbacked by &3<player>&7."),
	DEATHBAN_INVENTORY_ALREADY_ROLLBACKED("deathban.inventory_already_rollbacked", "&7Somebody already rollbacked &3<player>'s &7inventory!"),
	DEATHBAN_PLAYER_HAS_NOT_DIED_YET("deathban.player_has_not_died_yet", "&7Player &3<player> &7has not died yet!"),
	
	LIVES_COMMAND_REVIVE_USAGE("lives.command.revive_usage", "Usage: /lives revive <player>"),
	LIVES_COMMAND_SEND_USAGE("lives.command.send_usage", "Usage: /lives send <player> <amount>"),
	LIVES_COMMAND_SUCCESSFULLY_REVIVED_PLAYER("", "You successfully revived <player>."),
	LIVES_COMMAND_SUCCESSFULLY_SENT_LIVES("lives.command.successfully_sent_lives", "&7You sent <amount> lives to <player>."),
	LIVES_COMMAND_PLAYER_NOT_IN_DATABASE("lives.command.player_not_in_database", "<player> does not exist."),
	LIVES_COMMAND_CAN_NOT_SEND_LIVES_TO_YOURSELF("lives.command.can_not_send_lives_to_yourself", "&7You cannot send lives to yourself."),
	LIVES_COMMAND_PLAYER_NOT_ONLINE("lives.command.player_not_online", "The player you specified does not exist."),
	LIVES_COMMAND_CHECK_SELF("lives.command.check_self", "&3You have <lives> live(s)."),
	LIVES_COMMAND_CHECK_OTHERS("lives.command.check_others", "<player> has <lives> live(s)."),
	LIVES_COMMAND_LIVES_ADD_RECEIVED("lives.command.lives_add_received", "&7<player> has sent you <amount> live(s)."),
	LIVES_COMMAND_LIVES_ADDED("lives.command.lives_added", "&7<player> now has <amount> live(s)."),
	LIVES_COMMAND_LIVES_SET_RECEIVED("lives.command.lives_set_received", "&7<player> has set your live(s) to <amount>"),
	LIVES_COMMAND_LIVES_SET("lives.command.lives_set", "&7<player> now has <amount> lives."),
	LIVES_COMMAND_ZERO_LIVES("lives.command.zero_lives", "&7You do not have any lives."),
	LIVES_COMMAND_NOT_ENOUGH_LIVES("lives.command.not_enough_lives", "&cYou don't have enough lives."),
	
	STAFFMODE_ENABLE("STAFFMODE.ENABLE", "&7StaffMode has been toggled &aON!"),
	STAFFMODE_DISABLE("STAFFMODE.DISABLE", "&7StaffMode has been toggled &cOFF!"),
	STAFFMODE_RANDOMTELEPORTER("STAFFMODE.ABILITIES.RTP.MESSAGE", "&eTeleported to <player>!"),
	STAFFMODE_FREEZE("STAFFMODE.ABILITIES.FREEZE.MESSAGE", "&e<target> has been successfully frozen!"),
	STAFFMODE_INVENTORY_INSPECT("STAFFMODE.ABLITITES.INSPECTOR.MESSAGE", "&6Viewing <target>'s inventory!"),
	STAFFMODE_VANISH_ON("STAFFMODE.ABILITIES.VANISH.ON.MESSAGE", "&aVanish is now enabled!"),
	STAFFMODE_VANISH_OFF("STAFFMODE.ABILITIES.VANISH.OFF.MESSAGE", "&cVanish is now disabled!"),
	STAFFMODE_BREAK("STAFFMODE.DISABLED.BREAK", "&cSince you are in staffmode breaking blocks are denied!"),
	STAFFMODE_PLACE("STAFFMODE.DISABLED.PLACE", "&cSince you are in staffmode placing blocks are denied!"),
	
	RECLAIM_CLAIMED("RECLAIM.CLAIMED.MESSAGE", "&8&l[&c-&8&l] &c<player> has reclaimed their donator perks!"),
	RECLAIM_ALREADY_CLAIMED("RECLAIM.CLAIMED.ALREADY", "&7You have already claimed your perks for the map!"),
	
	PVPTIMER_FORMAT_ENABLE("pvptimer.format_enable", "&7/pvp enable &8- &6Enable PvP for yourself"),
	PVPTIMER_FORMAT_TIME("pvptimer.format_time", "&7/pvp time &8- &6Check time left on your timer"),
	PVPTIMER_FORMAT_SET("pvptimer.format_set", "&7/pvp set <player> &8- &6Enable a players pvp timer."),
	PVPTIMER_PLAYER_INVAILD("pvptimer.player_invalid", "&7This player does not exist."),
	PVPTIMER_TIMER_NOT_ACTIVE("pvptimer.timer_not_active", "&7Your PvP Timer is not active."),
	PVPTIMER_TIMER_ENABLED("pvptimer.timer_enabled", "&7You PvP Timer has been set by an administrator."),
	PVPTIMER_TIMER_ENABLED_OTHER("pvptimer.timer_enabled_other", "&7You have set <player> PvP Timer."),
	PVPTIMER_TIMER_DISABLED("pvptimer.timer_disabled", "&7You have disabled your PvP Timer&7."),
	PVPTIMER_TIMER_STATUS("pvptimer.timer_status", "&7Your PvP Timer time is: &c<time>"),
	PVPTIMER_FROZEN_MESSAGE("pvptimer.frozen_message", "&7Your PvP Timer &7is now &afrozen&7."),
	PVPTIMER_UNFROZEN_MESSAGE("pvptimer.unfrozen_message", "&7Your PvP Timer &7is now &cunfrozen&7."),
	PVPTIMER_PVPDENY_ATTACKER("pvptimer.pvpdeny_attacker", "&7<name> currently has a PvP Timer and cannot be damaged."),
	PVPTIMER_PVPDENY_VICTIM("pvptimer.pvpdeny_victim", "&7You can't attack other players with a PvP Timer."),
	PVPTIMER_COMMAND_DENY_MESSAGE("pvptimer.command_deny_message", "&7You can't use this command with a PvP Timer."),
	PVPTIMER_ITEM_DENY_MESSAGE("pvptimer.item_deny_message", "&7You can't use &3<item> &7with active PvP Timer."),
	PVPTIMER_END_PORTAL_TELEPORT_DENY("pvptimer.end_portal_teleport_deny", "&7You can't use portals with a PvP Timer.");
	
	
    private String path;
    private String value;
    private static YamlConfiguration lang;
    
    Lang(String path, String value) {
        this.path = path;
        this.value = value;
    }
    
    public static void setFile(YamlConfiguration config) {
        lang = config;
    }
    
    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', lang.getString(this.path, value));
    }
    
    public String getValue() {
        return this.value;
    }
    
    public String getPath() {
        return this.path;
    }
    
    @SuppressWarnings("deprecation")
	public static void loadLang() {
        File lang = new File(Main.getInstance().getDataFolder(), "lang.yml");
        if (!lang.exists()) {
            try {
                Main.getInstance().getDataFolder().mkdir();
                lang.createNewFile();
                InputStream defConfigStream = Main.getInstance().getResource("lang.yml");
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    defConfig.save(lang);
                    Lang.setFile(defConfig);
                    return;
                }
            } catch(IOException e) {
                e.printStackTrace();
                Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
                Main.getInstance().getLogger().severe("[Equinox] A Fatal Error has occured.");
                Main.getInstance().getLogger().severe("[Equinox] Report this to the developer: me.Equinox.utils.Lang (IOException)");
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for(Lang item:Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getValue());
            }
        }
        Lang.setFile(conf);
        Main.lang = conf;
        Main.langFile = lang;
        try {
            conf.save(Main.getInstance().getLangFile());
        } catch(IOException e) {
            e.printStackTrace();
            Main.getInstance().getLogger().severe("[Equinox] Failed to save lang.yml.");
        }
    }
}