package net.maattah.flare.utils;

import net.maattah.flare.Main;

public class Handler {

	private Main plugin;
    
    public Handler(Main plugin) {
        this.plugin = plugin;
    }
    
    public void enable() { }
    
    public void disable() { }
    
    public Main getInstance() {
        return this.plugin;
    }
}
