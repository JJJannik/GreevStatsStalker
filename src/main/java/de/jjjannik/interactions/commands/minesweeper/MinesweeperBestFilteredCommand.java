package de.jjjannik.interactions.commands.minesweeper;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.minesweeper.MinesweeperBestPlayer;
import de.jjjannik.requests.Minesweeper.Generator;
import de.jjjannik.requests.Minesweeper.Mode;
import de.jjjannik.requests.Minesweeper.Type;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class MinesweeperBestFilteredCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            String modeOption = evt.getOption("mode", OptionMapping::getAsString);
            String typeOption = evt.getOption("type", OptionMapping::getAsString);
            String genOption = evt.getOption("generator", OptionMapping::getAsString);

            Mode mode = modeOption == null ? null : Mode.valueOf(modeOption);
            Type type = Type.valueOf(typeOption);
            Generator gen = Generator.valueOf(genOption);

            MinesweeperBestPlayer timePlayer = jga.getFilteredBestMinesweeperTime(player.getUuid(), type, gen, mode);

            evt.replyEmbeds(new EmbedBuilder()
                    .setColor(Color.GREEN)
                    .setTitle("Best Minesweeper time of %s in %s mode %s and %s generator:".formatted(
                            player.getName(),
                            modeOption,
                            typeOption,
                            genOption
                    ))
                    .addField("Time", MILLIS_TO_SECONDS.format(timePlayer.getTime() / 1000L), false).build()).queue();
        });
    }
}