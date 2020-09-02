package me.stringdev.h4nexus.objects;

import me.stringdev.h4nexus.enums.Teams;
import org.bukkit.entity.Entity;

public class NexusBoss {

    private Teams colorTeam;
    private Entity boss;
    private int life;

    public NexusBoss(Teams colorTeam, Entity boss, int life) {
        this.colorTeam = colorTeam;
        this.boss = boss;
        this.life = life;
    }

    public Entity getBoss() {
        return boss;
    }

    public void setBoss(Entity boss) {
        this.boss = boss;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Teams getColorTeam() {
        return colorTeam;
    }

    public void setColorTeam(Teams colorTeam) {
        this.colorTeam = colorTeam;
    }
}
