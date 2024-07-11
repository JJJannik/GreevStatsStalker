package de.jjjannik.interactions.commands.advent;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.jumpnrun.JumpNRunPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static de.jjjannik.classes.PlayerCommand.MILLIS_TO_SECONDS;

public class AdventTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            int day = evt.getOption("day", OptionMapping::getAsInt);

            if (day < 1 || day > 24) {
                evt.replyEmbeds(new EmbedBuilder().setColor(Color.RED)
                                .addField("❌ **Invalid argument**", "The day cannot be lower than 1 or greater than 24", false)
                                .build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            int year = evt.getOption("year", OptionMapping::getAsInt);

            List<JumpNRunPlayer> runTimes = jga.getTopJumpAndRun(year, day, top.amount(), top.offset());

            if (runTimes.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder().setColor(Color.RED)
                                .addField("❌ **Not found**", "You picked an invalid year and day without open Advent door", false)
                                .build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<MessageEmbed> embeds = new ArrayList<>();

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Top %s Advent Jump&Run players of day %s in %s at offset %s".formatted(
                            top.amount(),
                            day,
                            year,
                            top.offset()
                    ));
            EmbedBuilder builder1 = new EmbedBuilder().setColor(Color.GREEN);

            for (int i = 0; i < 50; i++) {
                if (runTimes.size() == i) break;

                JumpNRunPlayer stats = runTimes.get(i);

                MessageEmbed.Field field = new MessageEmbed.Field(stats.getName(), """
                        Time: %s
                        Fails: %s
                        Checkpoints: %s
                        """.formatted(MILLIS_TO_SECONDS.format(stats.getTime()/ 1000L), stats.getFails(), stats.getCheckpoints()), true);

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