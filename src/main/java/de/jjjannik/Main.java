package de.jjjannik;

import de.jjjannik.api.JGA;
import de.jjjannik.interactions.Interaction;
import de.jjjannik.listener.SlashCommandListener;
import de.jjjannik.requests.Standard;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.log4j.PropertyConfigurator;
import org.apache.logging.log4j.util.Strings;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Main {
    public static final Map<String, Interaction> INTERACTIONS = new HashMap<>();
    @Getter private static JGA jga;

    public static void main(String[] args) throws IOException, InterruptedException {
        PropertyConfigurator.configure(Main.class.getClassLoader().getResourceAsStream("log4j2.properties"));
        JDA jda = null;

        File file = new File("./GreevStatsStalker/config.yml");
        new File("./GreevStatsStalker").mkdirs();
        if (!file.exists()) {
            getResourceAsFile("config.yml").renameTo(file);
        }
        YamlFile config = YamlFile.loadConfiguration(file);

        if (!config.isSet("botToken") || Strings.isEmpty(config.get("botToken").toString())) {
            log.error("No valid config provided! Please add a correct property with the path `botToken`\nIf you feel unsure, take a look into the default config");
            System.exit(1);
        }

        try {
            jda = JDABuilder.create(config.getString("botToken"),
                            List.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES))
                    .disableCache(CacheFlag.ACTIVITY, CacheFlag.VOICE_STATE, CacheFlag.EMOJI, CacheFlag.STICKER, CacheFlag.CLIENT_STATUS, CacheFlag.ONLINE_STATUS, CacheFlag.SCHEDULED_EVENTS)
                    .setActivity(Activity.watching(" Greev Stats."))
                    .setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.NONE)
                    .setStatus(OnlineStatus.ONLINE)
                    .build();
        } catch (InvalidTokenException e) {
            log.error("Bot could not be initialized", e);
            System.exit(1);
        }

        JGAInitializer.init();
        jga = JGAInitializer.getJGA();

        jda.awaitReady();
        jda.addEventListener(new SlashCommandListener());
        jda.updateCommands().addCommands(
                Commands.slash("general", "Command for all general stats")
                        .addSubcommands(
                                new SubcommandData("metadata", "1"),
                                new SubcommandData("badges", "1"),
                                new SubcommandData("uuid", "1"),
                                new SubcommandData("name", "1"),
                                new SubcommandData("name-history", "1"),
                                new SubcommandData("all-player-stats", "1")
                        )
                        .addSubcommandGroups(
                                new SubcommandGroupData("playerperformance", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("tokens", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("loginstreak", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("clans", "1")
                                        .addSubcommands()
                        ),
                Commands.slash("minigame","Standardised minigame stats")
                        .addSubcommandGroups(
                                new SubcommandGroupData("bedwars", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("bow-spleef", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("cores", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("jump-league", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("knockffa", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("oneline", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("1vs1", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("qsg", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("quake", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("rush", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("skywars", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("snowball-fight", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("spleef", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("sumo", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("tnt-run", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        ),
                                new SubcommandGroupData("uhc", "1")
                                        .addSubcommands(
                                                new SubcommandData("player", "1"),
                                                new SubcommandData("top", "1")
                                        )
                        ),
                Commands.slash("minesweeper", "1")
                        .addSubcommands(),
                Commands.slash("mlgrush", "1")
                        .addSubcommands(),
                Commands.slash("fastbridge", "1")
                        .addSubcommandGroups(),
                Commands.slash("advent", "1")
                        .addSubcommands(),
                Commands.slash("knockpvp", "1")
                        .addSubcommands(),
                Commands.slash("knockpvp-lab", "1")
                        .addSubcommands()
        ).queue();

        log.info("Started: {}", OffsetDateTime.now(ZoneId.systemDefault()));
    }

    private static File getResourceAsFile(String resourcePath) {
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath)) {
            if (in == null) return null;
            File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) out.write(buffer, 0, bytesRead);
            }
            return tempFile;
        } catch (IOException e) {
            log.error("Could not get resource as file", e);
            return null;
        }
    }

    private static void registerInteraction(String identifier, Interaction interaction) {
        INTERACTIONS.put(identifier, interaction);
    }
}