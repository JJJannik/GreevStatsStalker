package de.jjjannik.interactions.commands.fastbridge.mode;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.fastbridge.FastBridgePlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class FastbridgeModePlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
             String mode = evt.getOption("mode", OptionMapping::getAsString);
             FastBridgePlayer fastBridgePlayer;

             switch (mode) {
                 case "INCLINED" -> fastBridgePlayer = jga.getFastbridgeInclinedPlayer(player.getUuid());
                 case "INCLINED_SHORT" -> fastBridgePlayer = jga.getFastbridgeInclinedShortPlayer(player.getUuid());
                 case "NORMAL" -> fastBridgePlayer = jga.getFastbridgePlayer(player.getUuid());
                 case "SHORT" -> fastBridgePlayer = jga.getFastbridgeShortPlayer(player.getUuid());
                 case "EXTRA_SHORT" -> fastBridgePlayer = jga.getFastbridgeExtraShortPlayer(player.getUuid());
                 case "STAIRCASE" -> fastBridgePlayer = jga.getFastbridgeStaircasePlayer(player.getUuid());
                 default -> fastBridgePlayer = null;
             }

             evt.replyEmbeds(new EmbedBuilder()
                             .setColor(Color.GREEN)
                             .setTitle("Fastbridge '%s' stats of %s".formatted(mode, player.getName()))
                             .addField("Time", String.valueOf(fastBridgePlayer.getTime()), false)
                             .addField("Replay Id", fastBridgePlayer.getReplayId(), false)
                             .addField("Timestamp", fastBridgePlayer.getTimestamp(), false)
                             .build())
                     .queue();
        });
    }
}