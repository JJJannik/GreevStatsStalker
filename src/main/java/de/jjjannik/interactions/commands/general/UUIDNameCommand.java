package de.jjjannik.interactions.commands.general;

import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class UUIDNameCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> evt.replyEmbeds(new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle("Player")
                .addField("Name", player.getName(), false)
                .addField("UUID", String.valueOf(player.getUuid()), false)
                .build()
        ).queue());
    }
}