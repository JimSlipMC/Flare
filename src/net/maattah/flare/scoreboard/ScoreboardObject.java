package net.maattah.flare.scoreboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.google.common.base.Preconditions;

public class ScoreboardObject {
	
    private List<ScoreboardInput> list;
    private Scoreboard scoreboard;
    private Objective objective;
    private String title;
    private int lastSentCount;
    
    public ScoreboardObject(Scoreboard scoreBoard) {
        this.list = new ArrayList<ScoreboardInput>();
        this.title = "DefaultTitle";
        this.lastSentCount = -1;
        this.scoreboard = scoreBoard;
        (this.objective = this.getOrCreateObjective(this.title)).setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    public ScoreboardObject(Scoreboard scoreBoard, String title) {
        this.list = new ArrayList<ScoreboardInput>();
        this.title = "PlaceHolder";
        this.lastSentCount = -1;
        Preconditions.checkState(title.length() <= 32, "Max characters for Title is 32");
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.scoreboard = scoreBoard;
        (this.objective = this.getOrCreateObjective(this.title)).setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    public void add(String input) {
        input = ChatColor.translateAlternateColorCodes('&', input);
        ScoreboardInput text = null;
        if (input.length() <= 16) {
            text = new ScoreboardInput(input, "");
        }
        else {
            String first = input.substring(0, 16);
            String second = input.substring(16, input.length());
            if (first.endsWith(String.valueOf('§'))) {
                first = first.substring(0, first.length() - 1);
                second = String.valueOf('§') + second;
            }
            String lastColors = ChatColor.getLastColors(first);
            second = String.valueOf(lastColors) + second;
            text = new ScoreboardInput(first, StringUtils.left(second, 16));
        }
        this.list.add(text);
    }
    
    public void clear() {
        this.list.clear();
    }
    
    public void remove(int index) {
        String name = this.getNameForIndex(index);
        this.scoreboard.resetScores(name);
        Team team = this.getOrCreateTeam(String.valueOf(ChatColor.stripColor(StringUtils.left(this.title, 14))) + index, index);
        team.unregister();
    }
    
    public void update(Player player) {
        player.setScoreboard(this.scoreboard);
        for (int sentCount = 0; sentCount < this.list.size(); ++sentCount) {
            Team i = this.getOrCreateTeam(String.valueOf(ChatColor.stripColor(StringUtils.left(this.title, 14))) + sentCount, sentCount);
            ScoreboardInput str = this.list.get(this.list.size() - sentCount - 1);
            i.setPrefix(str.getLeft());
            i.setSuffix(str.getRight());
            this.objective.getScore(this.getNameForIndex(sentCount)).setScore(sentCount + 1);
        }
        if (this.lastSentCount != -1) {
            for (int sentCount = this.list.size(), var4 = 0; var4 < this.lastSentCount - sentCount; ++var4) {
                this.remove(sentCount + var4);
            }
        }
        this.lastSentCount = this.list.size();
    }
    
    public Team getOrCreateTeam(String team, int i) {
        Team value = this.scoreboard.getTeam(team);
        if (value == null) {
            value = this.scoreboard.registerNewTeam(team);
            value.addEntry(this.getNameForIndex(i));
        }
        return value;
    }
    
    public Objective getOrCreateObjective(String objective) {
        Objective value = this.scoreboard.getObjective("flare");
        if (value == null) {
            value = this.scoreboard.registerNewObjective("flare", "dummy");
        }
        value.setDisplayName(objective);
        return value;
    }
    
    public String getNameForIndex(int index) {
        return String.valueOf(ChatColor.values()[index].toString()) + ChatColor.RESET;
    }
}
