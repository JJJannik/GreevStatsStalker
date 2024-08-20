package de.jjjannik.classes;

import de.jjjannik.classes.entities.RollingOptions;
import de.jjjannik.classes.entities.TopOptions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.function.Consumer;

public abstract class RollingTopCommand extends TopCommand {

    protected void handleRollingPlayerCommand(SlashCommandInteractionEvent evt, Consumer<TopOptions> topConsumer, Consumer<RollingOptions> rollingConsumer) {
        handleTopCommand(evt, topConsumer);

        long startTime;
        long endTime;

        try {
            startTime = evt.getOption("start-timestamp", OptionMapping::getAsInt);
            endTime = evt.getOption("end-timestamp", OptionMapping::getAsInt);
        } catch (ArithmeticException e) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **Invalid Timestamp input**", "The timestamp inputs need to be provided in the unix seconds format. Click [here](https://www.unixtimestamp.com/) to read more.", false).build())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (startTime < 0 || endTime < 0 || endTime < startTime) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **Invalid Timestamp input**", "The timestamp inputs need to be greater 0 and the endTime has to be greater than the startTime", false).build())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        rollingConsumer.accept(new RollingOptions(startTime, endTime));
    }
}