package de.jjjannik.interactions.commands.minigames.knockffa;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class KnockFFAPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            KillsDeathsPlayer stats = jga.getKnockFFAPlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("KnockFFA stats of " + player.getName())
                            .addField("Kills", String.valueOf(stats.getKills()), true)
                            .addField("Deaths", String.valueOf(stats.getDeaths()), true)
                            .addField("K/D", TWO_DECIMALS.format(stats.getKills() * 1D / stats.getDeaths()), true)
                            .build())
                    .queue();
        });
    }
}