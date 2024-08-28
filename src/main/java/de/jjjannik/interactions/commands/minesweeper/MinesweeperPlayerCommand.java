package de.jjjannik.interactions.commands.minesweeper;

import de.jjjannik.classes.RollingPlayerCommand;
import de.jjjannik.entities.minesweeper.MinesweeperPlayer;
import de.jjjannik.requests.Minesweeper.Generator;
import de.jjjannik.requests.Minesweeper.Mode;
import de.jjjannik.requests.Minesweeper.Type;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;

public class MinesweeperPlayerCommand extends RollingPlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handleRollingPlayerCommand(evt, (player, rolling) -> {
            String modeOption = evt.getOption("mode", OptionMapping::getAsString);
            String typeOption = evt.getOption("type", OptionMapping::getAsString);
            String genOption = evt.getOption("generator", OptionMapping::getAsString);

            Mode mode = modeOption == null ? null : Mode.valueOf(modeOption);
            Type type = typeOption == null ? null : Type.valueOf(typeOption);
            Generator gen = genOption == null ? null : Generator.valueOf(genOption);

            MinesweeperPlayer msPlayer = jga.getMinesweeperPlayer(player.getUuid(), type, gen, mode, rolling.startTime(), rolling.endTime());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Minesweeper stats of " + player.getName())
                            .addField("Rounds", String.valueOf(msPlayer.getRounds()), true)
                            .addField("Correct flags", String.valueOf(msPlayer.getCorrectPlacedFlags()), true)
                            .addField("Incorrect flags", String.valueOf(msPlayer.getIncorrectPlacedFlags()), true)
                            .addField("Mastery", String.valueOf(msPlayer.getMastery()), true)
                            .build())
                    .queue();
        });
    }
}