package de.jjjannik.interactions.commands.advent;

import de.jjjannik.classes.PlayerCommand;
import de.jjjannik.entities.jumpnrun.JumpNRunTime;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.List;

public class AdventDayCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
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

            List<JumpNRunTime> runTimes = jga.getDayPlayerAdventTimes(player.getUuid(), year, day);


        });
    }
}