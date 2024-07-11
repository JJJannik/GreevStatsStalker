package de.jjjannik.interactions.commands.fastbridge.mode;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.fastbridge.FastBridgePlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FastrbidgeModeTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            String mode = evt.getOption("mode", OptionMapping::getAsString);

            List<FastBridgePlayer> topStats;

            switch (mode) {
                case "INCLINED" -> topStats = jga.getTopFastbridgeInclined(top.amount(), top.offset());
                case "INCLINED_SHORT" -> topStats = jga.getTopFastbridgeInclinedShort(top.amount(), top.offset());
                case "NORMAL" -> topStats = jga.getTopFastbridge(top.amount(), top.offset());
                case "SHORT" -> topStats = jga.getTopFastbridgeShort(top.amount(), top.offset());
                case "EXTRA_SHORT" -> topStats = jga.getTopFastbridgeExtraShort(top.amount(), top.offset());
                case "STAIRCASE" -> topStats = jga.getTopFastbridgeStaircase(top.amount(), top.offset());
                default -> topStats = null;
            }

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s Fastbridge '%s' stats starting at offset %s".formatted(
                            top.amount(),
                            mode,
                            top.offset()
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (topStats.size() == i) break;

                FastBridgePlayer stats = topStats.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field(stats.getName(), """
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