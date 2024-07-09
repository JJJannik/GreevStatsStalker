package de.jjjannik.interactions.commands.fastbridge.islands;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.fastbridge.FastBridgeIslandsPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class FastbridgeIslandsPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            FastBridgeIslandsPlayer fastBridgePlayer = jga.getFastbridgeIslandsPlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Fastbridge stats of %s".formatted(player.getName()))
                            .addField("Map", String.valueOf(fastBridgePlayer.getMap()), false)
                            .addField("Time", String.valueOf(fastBridgePlayer.getTime()), false)
                            .addField("Replay Id", fastBridgePlayer.getReplayId(), false)
                            .addField("Timestamp", fastBridgePlayer.getTimestamp(), false)
                            .build())
                    .queue();
        });
    }
}