package de.jjjannik.interactions.commands.general.loginstreak;

import de.jjjannik.entities.LoginstreakPlayer;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class LoginstreakPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            LoginstreakPlayer loginstreak = jga.getLoginstreakPlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Loginstreak of " + player.getName())
                    .addField("Current Loginstreak", String.valueOf(loginstreak.getCurrentStreak()), false)
                    .addField("Max Loginstreak", String.valueOf(loginstreak.getMaxStreak()), false)
                    .build())
                    .queue();
        });
    }
}