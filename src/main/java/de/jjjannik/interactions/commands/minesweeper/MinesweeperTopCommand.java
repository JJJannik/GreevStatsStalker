package de.jjjannik.interactions.commands.minesweeper;

import de.jjjannik.classes.TopCommand;
import de.jjjannik.entities.minesweeper.MinesweeperTimeEntry;
import de.jjjannik.requests.Minesweeper.Generator;
import de.jjjannik.requests.Minesweeper.Mode;
import de.jjjannik.requests.Minesweeper.RankingCriteria;
import de.jjjannik.requests.Minesweeper.Type;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static de.jjjannik.classes.PlayerCommand.MILLIS_TO_SECONDS;

public class MinesweeperTopCommand extends TopCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleTopCommand(evt, top -> {
            Long start = evt.getOption("start-timestamp", OptionMapping::getAsLong) * 1000;
            Long end = evt.getOption("end-timestamp", OptionMapping::getAsLong) * 1000;

            Type type = Type.valueOf(evt.getOption("type", OptionMapping::getAsString));
            Generator gen = Generator.valueOf(evt.getOption("generator", OptionMapping::getAsString));

            String modeOption = evt.getOption("mode", OptionMapping::getAsString);
            String criteriaOption = evt.getOption("criteria", OptionMapping::getAsString);

            Mode mode = modeOption == null ? null : Mode.valueOf(modeOption);
            RankingCriteria criteria = criteriaOption == null ? null : RankingCriteria.valueOf(criteriaOption);

            List<MinesweeperTimeEntry> topTimes = jga.getTopMinesweeperTimes(top.amount(), top.offset(), mode, criteria, start, end, type, gen);

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN)
                    .setTitle("Best Minesweeper Times ");
            List<MessageEmbed> embeds = new ArrayList<>();

            if (topTimes.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.RED)
                        .addField("❌ **No data**", "No Minesweeper games were finished specified by your options", false)
                        .build()
                ).setEphemeral(true).queue();
                return;
            }

            for (int i = 1; i <= topTimes.size(); i++) {
                if (builder.getFields().size() == 25) {
                    embeds.add(builder.build());
                    builder.clearFields().setTitle(null);
                }
                MinesweeperTimeEntry entry = topTimes.get(i - 1);

                builder.addField(entry.getName(), MILLIS_TO_SECONDS.format(entry.getTime() / 1000L), true);
            }
            embeds.add(builder.build());
            evt.replyEmbeds(embeds).queue();
        });
    }
}