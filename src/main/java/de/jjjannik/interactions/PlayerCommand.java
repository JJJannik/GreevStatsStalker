package de.jjjannik.interactions;

import de.jjjannik.Main;
import de.jjjannik.api.JGA;
import de.jjjannik.entities.basic.Player;
import de.jjjannik.utils.exceptions.APICallException;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Pattern;

@Slf4j
public abstract class PlayerCommand implements Interaction {
    private final Pattern uuidRegex = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    protected final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    protected final JGA jga = Main.getJga();

    protected void handlePlayerCommand(SlashCommandInteractionEvent evt, Consumer<Player> playerConsumer) {
        String option = evt.getOption("player", OptionMapping::getAsString);
        UUID uuid;

        if (uuidRegex.matcher(option).matches()) {
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

        try {
            Player p = new Player(jga.getKnockPvPPlayer(uuid).getJsonObject()); // don't question this, jga.getPlayerName(UUID) just times out if UUID is valid but there's no player with that uuid
            playerConsumer.accept(p);
        } catch (APICallException e) {
            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.RED)
                            .addField("❌ **No player found**", "This player either does not exist or never joined Greev.eu", false).build())
                    .setEphemeral(true)
                    .queue();
        }
    }

    protected static String formatTimestamp(long timestamp, boolean isMillis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(Date.from(isMillis ? Instant.ofEpochMilli(timestamp) : Instant.ofEpochSecond(timestamp)));
    }
}