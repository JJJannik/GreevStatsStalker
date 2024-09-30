package de.jjjannik.classes;

import de.jjjannik.Main;
import de.jjjannik.api.JGA;
import de.jjjannik.classes.entities.RollingOptions;
import de.jjjannik.entities.basic.Player;
import de.jjjannik.interactions.Interaction;
import de.jjjannik.utils.exceptions.APICallException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.util.UUID;
import java.util.function.BiConsumer;

import static de.jjjannik.classes.PlayerCommand.UUID_REGEX;

public abstract class RollingPlayerCommand implements Interaction {
    protected final JGA jga = Main.getJga();

    protected void handleRollingPlayerCommand(SlashCommandInteractionEvent evt, BiConsumer<Player, RollingOptions> args) {
        String option = evt.getOption("player", OptionMapping::getAsString);
        UUID uuid;

        if (UUID_REGEX.matcher(option).matches()) {
            uuid = UUID.fromString(option);
        } else {
            try {
                uuid = jga.getPlayerUUID(option).getUuid();
            } catch (Exception e) {
                evt.replyEmbeds(new EmbedBuilder()
                                .setColor(Color.RED)
                                .addField("❌ **No player found**", "The player with this uuid does not exist", false).build())
                        .setEphemeral(true)
                        .queue();
                return;
            }
        }

        Player player;
        try {
            player = new Player(jga.getPlayerMetaData(uuid).getJsonObject()); // don't question this, jga.getPlayerName(UUID) just times out if UUID is valid but there's no player with that uuid
        } catch (APICallException e) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **No player found**", "This player either does not exist, never joined Greev.eu or doesn't have any stats in that gamemode", false).build())
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

        args.accept(player, rolling);
    }
}