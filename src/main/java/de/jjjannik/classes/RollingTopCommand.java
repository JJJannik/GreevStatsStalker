package de.jjjannik.classes;

import de.jjjannik.Main;
import de.jjjannik.api.JGA;
import de.jjjannik.classes.entities.RollingOptions;
import de.jjjannik.classes.entities.TopOptions;
import de.jjjannik.interactions.Interaction;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.function.BiConsumer;

public abstract class RollingTopCommand implements Interaction {
    protected final JGA jga = Main.getJga();

    protected void handleRollingTopCommand(SlashCommandInteractionEvent evt, BiConsumer<TopOptions, RollingOptions> args) {
        int amount = evt.getOption("amount", OptionMapping::getAsInt);
        int offset = evt.getOption("offset", OptionMapping::getAsInt);

        if (amount < 1 || amount > 50) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **Incorrect option**", "Option 'amount' must lies between 1 and 50", false).build())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        if (offset < 0) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **Incorrect option**", "Option 'offset' can't be negative", false).build())
                    .setEphemeral(true)
                    .queue();
            return;
        }

        RollingOptions rolling = null;
        OptionMapping start = evt.getOption("start-timestamp");
        OptionMapping end = evt.getOption("end-timestamp");

        if (start != null && end != null) {
            long startTime;
            long endTime;

            try {
                startTime = evt.getOption("start-timestamp").getAsInt();
                endTime = evt.getOption("end-timestamp").getAsInt();
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

            rolling = new RollingOptions(startTime, endTime);
        }

        args.accept(new TopOptions(amount, offset), rolling);
    }
}