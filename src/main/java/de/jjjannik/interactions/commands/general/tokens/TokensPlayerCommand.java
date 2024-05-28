package de.jjjannik.interactions.commands.general.tokens;

import de.jjjannik.entities.TokensPlayer;
import de.jjjannik.interactions.PlayerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;

public class TokensPlayerCommand extends PlayerCommand {
    @Override
    public void execute(SlashCommandInteractionEvent evt) {
        handlePlayerCommand(evt, player -> {
            TokensPlayer tokens = jga.getTokensPlayer(player.getUuid());

            evt.replyEmbeds(new EmbedBuilder()
                            .setColor(Color.GREEN)
                            .setTitle("Tokens of " + player.getName())
                            .addField("Tokens", String.valueOf(tokens.getTokens()), false)
                            .build())
                    .queue();
        });
    }
}