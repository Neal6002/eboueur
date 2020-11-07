package fr.neal.plugineboueur;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID,Integer> cooldowns = new HashMap<>();

    public static final int DEFAULTCOOLDOWN = 300;

    public void setCooldowns(UUID player,int timer)
    {
        if (timer < 1)
        {
          cooldowns.remove(player);
        }

        else
        {
            cooldowns.put(player,timer);
        }
    }

    public int getCooldown(UUID player)
    {
        return cooldowns.getOrDefault(player,0);
    }







}
