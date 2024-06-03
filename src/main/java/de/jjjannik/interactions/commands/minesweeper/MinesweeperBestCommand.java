package de.jjjannik.interactions.commands.minesweeper;

import de.jjjannik.entities.minesweeper.MinesweeperTimePlayer;
import de.jjjannik.interactions.PlayerCommand;
import de.jjjannik.requests.Minesweeper.Mode;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MinesweeperBestCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            String option = evt.getOption("mode", OptionMapping::getAsString);
            Mode mode = Mode.DEFAULT;

            if (option != null) {
                mode = Mode.valueOf(evt.getOption("mode", OptionMapping::getAsString));
            }

            List<MinesweeperTimePlayer> timePlayerList = jga.getBestMinesweeperTimes(player.getUuid(), mode);

            EmbedBuilder builder = new EmbedBuilder().setColor(Color.GREEN).setTitle("Best Minesweeper times of " + player.getName());
            List<MessageEmbed> embeds = new ArrayList<>();

            if (timePlayerList.isEmpty()) {
                evt.replyEmbeds(new EmbedBuilder()
                        .setColor(Color.RED)
                        .addField("❌ **No data**", "This player never completed a Minesweeper game in that mode", false)
                        .build()
                ).setEphemeral(true).queue();
                return;
            }

            for (int i = 1; i <= timePlayerList.size(); i++) {
                if (builder.getFields().size() == 25) {
                    embeds.add(builder.build());
                    builder.clearFields().setTitle(null);
                }
                MinesweeperTimePlayer entry = timePlayerList.get(i - 1);
                builder.addField("Time: " + MILLIS_TO_SECONDS.format(entry.getTime() / 1000D),
                        """
                        Mode: %s
                        Type: %s
                        Generator: %s
                        """.formatted(mode, entry.getType(), entry.getGenerator()),
                        false
                );
            }
            embeds.add(builder.build());
            evt.replyEmbeds(embeds).queue();
        });
    }
}