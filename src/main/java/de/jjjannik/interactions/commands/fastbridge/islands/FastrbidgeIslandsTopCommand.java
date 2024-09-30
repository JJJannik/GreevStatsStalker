package de.jjjannik.interactions.commands.fastbridge.islands;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.fastbridge.FastBridgeIslandsPlayer;
import de.jjjannik.requests.FastbridgeIslands;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FastrbidgeIslandsTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            String map = evt.getOption("map", OptionMapping::getAsString);

            List<FastBridgeIslandsPlayer> topStats = jga.getTopFastbridgeIslands(FastbridgeIslands.Map.valueOf(map), top.amount(), top.offset());

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s Fastbridge Island '%s' stats starting with #%s".formatted(
                            top.amount(),
                            map,
                            top.offset()+1
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                FastBridgeIslandsPlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field("#%s %s".formatted(top.offset()+i+1, stats.getName()), """
                        Time: %s
                        Replay Id: %s
                        Timestamp: %s
                        """.formatted(stats.getTime(), stats.getReplayId(), stats.getTimestamp()), true);

                if (i < 25) {
                    builder.addField(field);
                } else {
                    builder1.addField(field);
                }
            }
            embeds.add(builder.build());
            if (!builder1.getFields().isEmpty()) {
                embeds.add(builder1.build());
            }

            evt.replyEmbeds(embeds).queue();
        });
    }
}