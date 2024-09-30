package de.jjjannik.interactions.commands.advent;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.jumpnrun.JumpNRunTime;
import de.jjjannik.requests.AdventJnRs;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
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

            List<JumpNRunTime> runTimes = jga.getYearPlayerAdventTimes(player.getUuid(), year);

            if (runTimes.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder().setColor(Color.RED)
                                .addField("❌ **Not found**", "This player either didn't tried the Jump&Run that year or you picked an invalid year", false)
                                .build())
                        .setEphemeral(true)
                        .queue();
                return;
            }

            List<MessageEmbed> embeds = new ArrayList<>();
            boolean done = false;

            for (int e = 0; e < 5; e++) {
                if (done) break;

                EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN);

                if (e == 0) {
                    builder.setTitle("Advent Jump&Run times of %s in %s".formatted(player.getName(), year));
                }

                for (int f = 0; f < 25; f++) {
                    int index = e * 10 + f;
                    if (runTimes.size() == index) {
                        done = true;
                        break;
                    }

                    JumpNRunTime stats = runTimes.get(index);

                    MessageEmbed.Field field = new MessageEmbed.Field("Door %s".formatted(stats.getJumpAndRunId()), """
                        Time: %s
                        Fails: %s
                        Checkpoints: %s
                        """.formatted(MILLIS_TO_SECONDS.format(stats.getTime()/1000d), stats.getFails(), stats.getCheckpoints()), true);

                    builder.addField(field);
                }

                embeds.add(builder.build());
            }

            evt.replyEmbeds(embeds).queue(s -> ((TextChannel) s.getInteraction().getChannel()).sendMessage("""
                    **Note:** Due to Discord limitations, this message can only show up to 125 times.
                    Click [here](%s) to see all of the player times of this year.
                    """.formatted(AdventJnRs.GET_ALL_PLAYER.getUrl().formatted(player.getUuid(), year))).queue());
        });
    }
}