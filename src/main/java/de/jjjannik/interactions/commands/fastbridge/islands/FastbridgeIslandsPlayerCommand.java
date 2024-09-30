package de.jjjannik.interactions.commands.fastbridge.islands;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.fastbridge.FastBridgeIslandsPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FastbridgeIslandsPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            List<FastBridgeIslandsPlayer> list = jga.getFastbridgeIslandsPlayer(player.getUuid());

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setAuthor("Fastbridge Island stats of " + player.getName());

            List<MessageEmbed> embeds = new ArrayList<>();

            if (list.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No stats found**", "This player does not have stats in that game mode", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            for (int i = 0; i < list.size(); i++) {
                FastBridgeIslandsPlayer stats = list.get(i);
                if (i == 1) {
                    embeds.add(builder.setTitle("Fastbridge stats of %s".formatted(player.getName()))
                            .addField("Map", String.valueOf(stats.getMap()), false)
                            .addField("Time", String.valueOf(stats.getTime()), false)
                            .addField("Replay Id", stats.getReplayId(), false)
                            .addField("Timestamp", stats.getTimestamp(), false)
                            .build());
                    continue;
                }

                embeds.add(new EmbedBuilder().setColor(Color.GREEN).setTitle("Fastbridge stats of %s".formatted(player.getName()))
                        .addField("Map", String.valueOf(stats.getMap()), false)
                        .addField("Time", String.valueOf(stats.getTime()), false)
                        .addField("Replay Id", stats.getReplayId(), false)
                        .addField("Timestamp", stats.getTimestamp(), false)
                        .build());
            }

            evt.replyEmbeds(embeds).queue();
        });
    }
}