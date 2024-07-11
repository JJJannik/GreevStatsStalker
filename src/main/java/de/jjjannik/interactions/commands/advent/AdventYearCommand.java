package de.jjjannik.interactions.commands.advent;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.jumpnrun.JumpNRunTime;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdventYearCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            int year = evt.getOption("year", OptionMapping::getAsInt);

            java.util.List<JumpNRunTime> runTimes = jga.getYearPlayerAdventTimes(player.getUuid(), year);

            if (runTimes.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder().setColor(Color.RED)
                                .addField("❌ **Not found**", "This player either didn't tried the Jump&Run that year or you picked an invalid year", false)
                                .build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Advent Jump&Run times of %s on in %s".formatted(player.getName(), year));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (runTimes.size() == i) break;

                JumpNRunTime stats = runTimes.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field(MILLIS_TO_SECONDS.format(stats.getTime()/1000L), """
                        Fails: %s
                        Checkpoints: %s
                        """.formatted(stats.getFails(), stats.getCheckpoints()), true);

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