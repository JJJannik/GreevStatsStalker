package de.jjjannik;

import de.jjjannik.api.JGA;
import de.jjjannik.interactions.Interaction;
import de.jjjannik.interactions.commands.advent.AdventDayCommand;
import de.jjjannik.interactions.commands.advent.AdventYearCommand;
import de.jjjannik.interactions.commands.fastbridge.islands.FastbridgeIslandsPlayerCommand;
import de.jjjannik.interactions.commands.fastbridge.islands.FastrbidgeIslandsTopCommand;
import de.jjjannik.interactions.commands.fastbridge.mode.FastbridgeModePlayerCommand;
import de.jjjannik.interactions.commands.fastbridge.mode.FastrbidgeModeTopCommand;
import de.jjjannik.interactions.commands.general.*;
import de.jjjannik.interactions.commands.general.clans.ClanDetailsCommand;
import de.jjjannik.interactions.commands.general.clans.ClanMembersCommand;
import de.jjjannik.interactions.commands.general.clans.ClanTopCommand;
import de.jjjannik.interactions.commands.general.loginstreak.LoginstreakPlayerCommand;
import de.jjjannik.interactions.commands.general.performance.PerfomanceTopCommand;
import de.jjjannik.interactions.commands.general.performance.PerformancePlayerCommand;
import de.jjjannik.interactions.commands.general.tokens.TokensTopCommand;
import de.jjjannik.interactions.commands.knockpvp.*;
import de.jjjannik.interactions.commands.knockpvp.lab.KnockLabPlayerCommand;
import de.jjjannik.interactions.commands.knockpvp.lab.KnockLabRollingPlayerCommand;
import de.jjjannik.interactions.commands.knockpvp.lab.KnockLabRollingTopCommand;
import de.jjjannik.interactions.commands.knockpvp.lab.KnockLabTopCommand;
import de.jjjannik.interactions.commands.minesweeper.*;
import de.jjjannik.interactions.commands.minigames.bedwars.BedwarsPlayerCommand;
import de.jjjannik.interactions.commands.minigames.bedwars.BedwarsTopCommand;
import de.jjjannik.interactions.commands.minigames.bow_spleef.BowSpleefPlayerCommand;
import de.jjjannik.interactions.commands.minigames.cores.CoresPlayerCommand;
import de.jjjannik.interactions.commands.minigames.cores.CoresTopCommand;
import de.jjjannik.interactions.commands.minigames.jump_league.JumpLeaguePlayerCommand;
import de.jjjannik.interactions.commands.minigames.jump_league.JumpLeagueTopCommand;
import de.jjjannik.interactions.commands.minigames.knockffa.KnockFFAPlayerCommand;
import de.jjjannik.interactions.commands.minigames.knockffa.KnockFFATopCommand;
import de.jjjannik.interactions.commands.minigames.one_vs_one.OneVsOnePlayerCommand;
import de.jjjannik.interactions.commands.minigames.one_vs_one.OneVsOneTopCommand;
import de.jjjannik.interactions.commands.minigames.oneline.OnelinePlayerCommand;
import de.jjjannik.interactions.commands.minigames.oneline.OnelineTopCommand;
import de.jjjannik.interactions.commands.minigames.qsg.QsgPlayerCommand;
import de.jjjannik.interactions.commands.minigames.qsg.QsgTopCommand;
import de.jjjannik.interactions.commands.minigames.quake.QuakePlayerCommand;
import de.jjjannik.interactions.commands.minigames.quake.QuakeTopCommand;
import de.jjjannik.interactions.commands.minigames.rush.RushPlayerCommand;
import de.jjjannik.interactions.commands.minigames.rush.RushTopCommand;
import de.jjjannik.interactions.commands.minigames.skywars.SkywarsPlayerCommand;
import de.jjjannik.interactions.commands.minigames.skywars.SkywarsTopCommand;
import de.jjjannik.interactions.commands.minigames.snowball_fight.SnowballFightPlayerCommand;
import de.jjjannik.interactions.commands.minigames.snowball_fight.SnowballFightTopCommand;
import de.jjjannik.interactions.commands.minigames.spleef.SpleefPlayerCommand;
import de.jjjannik.interactions.commands.minigames.spleef.SpleefTopCommand;
import de.jjjannik.interactions.commands.minigames.sumo.SumoPlayerCommand;
import de.jjjannik.interactions.commands.minigames.sumo.SumoTopCommand;
import de.jjjannik.interactions.commands.minigames.tnt_run.TNTRunPlayerCommand;
import de.jjjannik.interactions.commands.minigames.tnt_run.TNTRunTopCommand;
import de.jjjannik.interactions.commands.minigames.uhc.UhcPlayerCommand;
import de.jjjannik.interactions.commands.minigames.uhc.UhcTopCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushPlayerCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushRollingPlayerCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushRollingTopCommand;
import de.jjjannik.interactions.commands.mlgrush.MLGRushTopCommand;
import de.jjjannik.listener.SlashCommandListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
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
                    .setActivity(Activity.watching(" your Greev Stats."))
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
                                new SubcommandData("metadata", "Get all MetaData of player"),
                                new SubcommandData("badges", "Get all Badges of player"),
                                new SubcommandData("uuid", "Get player UUID of player"),
                                new SubcommandData("name", "Get player Name of player"),
                                new SubcommandData("name-history", "Get Namehistory of player"),
                                new SubcommandData("all-player-stats", "Get all player stats combined")
                        )
                        .addSubcommandGroups(
                                new SubcommandGroupData("performance", "Get player Performance")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get player Performance of player"),
                                                new SubcommandData("top", "Get top Performance players")
                                        ),
                                new SubcommandGroupData("tokens", "Get player Tokens")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Tokens of player"),
                                                new SubcommandData("top", "Get top Tokens players")
                                        ),
                                new SubcommandGroupData("loginstreak", "Get player Loginstreak")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get the Loginstreak of player"),
                                                new SubcommandData("top", "Get top Loginstreak players")
                                        ),
                                new SubcommandGroupData("clans", "Get Clan information")
                                        .addSubcommands(
                                                new SubcommandData("details", "Get Clan details"),
                                                new SubcommandData("top", "Get top Clans"),
                                                new SubcommandData("members", "Get Clan members")
                                        )
                        ),
                Commands.slash("minigame","Standardised minigame stats")
                        .addSubcommandGroups(
                                new SubcommandGroupData("bedwars", "Get Bedwars stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Bedwars stats of player"),
                                                new SubcommandData("top", "Get top Bedwars players")
                                        ),
                                new SubcommandGroupData("bow-spleef", "Get BowSpleef stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get BowSpleef stats of player"),
                                                new SubcommandData("top", "Get top BowSpleef players")
                                        ),
                                new SubcommandGroupData("cores", "Get Cores stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Cores stats of player"),
                                                new SubcommandData("top", "Get top Cores players")
                                        ),
                                new SubcommandGroupData("jump-league", "Get JumpLeague stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get JumpLeague stats of player"),
                                                new SubcommandData("top", "Get top JumpLeague players")
                                        ),
                                new SubcommandGroupData("knockffa", "Get KnockFFA stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get KnockFFA stats of player"),
                                                new SubcommandData("top", "Get top KnockFFA players")
                                        ),
                                new SubcommandGroupData("oneline", "Get Oneline stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Oneline stats of player"),
                                                new SubcommandData("top", "Get top Oneline players")
                                        ),
                                new SubcommandGroupData("1vs1", "Get 1vs1 stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get 1vs1 stats of player"),
                                                new SubcommandData("top", "Get top 1vs1 players")
                                        ),
                                new SubcommandGroupData("qsg", "Get QSG stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get QSG stats of player"),
                                                new SubcommandData("top", "Get top QSG players")
                                        ),
                                new SubcommandGroupData("quake", "Get Quake stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Quake stats of player"),
                                                new SubcommandData("top", "Get top Quake players")
                                        ),
                                new SubcommandGroupData("rush", "Get Rush stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Rush stats of player"),
                                                new SubcommandData("top", "Get top Rush players")
                                        ),
                                new SubcommandGroupData("skywars", "Get Skywars stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Skywars stats of player"),
                                                new SubcommandData("top", "Get top Skywars players")
                                        ),
                                new SubcommandGroupData("snowball-fight", "Get SnowballFight stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get SnowballFight stats of player"),
                                                new SubcommandData("top", "Get top SnowballFight players")
                                        ),
                                new SubcommandGroupData("spleef", "Get Spleef stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Spleef stats of player"),
                                                new SubcommandData("top", "Get top Spleef players")
                                        ),
                                new SubcommandGroupData("sumo", "Get Sumo stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Sumo stats of player"),
                                                new SubcommandData("top", "Get top Sumo players")
                                        ),
                                new SubcommandGroupData("tnt-run", "Get TNTRun stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get TNTRun stats of player"),
                                                new SubcommandData("top", "Get top TNTRun players")
                                        ),
                                new SubcommandGroupData("uhc", "Get UHC stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get UHC stats of player"),
                                                new SubcommandData("top", "Get top UHC players")
                                        )
                        ),
                Commands.slash("minesweeper", "Get Minesweeper stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top Minesweeper player"),
                                new SubcommandData("player", "Get Minesweeper stats of player"),
                                new SubcommandData("best", "Get best Minesweeper stats of player"),
                                new SubcommandData("best-filtered", "Get best Minesweeper stats of player with filter"),
                                new SubcommandData("game", "Get Minesweeper game info")
                        ),
                Commands.slash("mlgrush", "Get MLGRush stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top MLGRush players"),
                                new SubcommandData("rolling-top", "Get top rolling MLGRush players"),
                                new SubcommandData("player", "Get MLGRush stats of player"),
                                new SubcommandData("rolling-player", "Get rolling MLGRush stats of player")
                        ),
                Commands.slash("fastbridge", "Get Fastbridge stats")
                        .addSubcommandGroups(
                                new SubcommandGroupData("islands", "Get Fastbridge Island stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Fastbridge Island stats of player"),
                                                new SubcommandData("top", "Get top Fastbridge Island players")
                                        ),
                                new SubcommandGroupData("mode", "Get other Fastbridge mode stats")
                                        .addSubcommands(
                                                new SubcommandData("player", "Get Fastbridge mode stats of player"),
                                                new SubcommandData("top", "Get top Fastbridge mode players")
                                        )
                        ),
                Commands.slash("advent", "Get Advent Jump and Run stats")
                        .addSubcommands(
                                new SubcommandData("year", "Get Advent Jump&Run times of player of year"),
                                new SubcommandData("day", "Get Advent Jump&Run times of player of day")
                        ),
                Commands.slash("knockpvp", "Get KnockPVP stats")
                        .addSubcommands(
                                new SubcommandData("top", "Get top KnockPVP players"),
                                new SubcommandData("rolling-top", "Get top rolling KnockPVP players"),
                                new SubcommandData("player", "Get KnockPVP stats of player"),
                                new SubcommandData("rolling-player", "Get rolling KnockPVP stats of player"),
                                new SubcommandData("yearly", "Get yearly KnockPVP stats of player"),
                                new SubcommandData("monthly", "Get monthly KnockPVP stats of player"),
                                new SubcommandData("weekly", "Get weekly KnockPVP stats of player"),
                                new SubcommandData("daily", "Get daily KnockPVP stats of player"),
                                new SubcommandData("days", "Get KnockPVP stats of player of last N days")
                        ),
                Commands.slash("knockpvp-lab", "Get KnockPVP-Lab stats from experiment")
                        .addSubcommands(
                                new SubcommandData("top", "Get top KnockPVP-Lab players"),
                                new SubcommandData("rolling-top", "Get top rolling KnockPVP-Lab players"),
                                new SubcommandData("player", "Get KnockPVP-Lab stats of player"),
                                new SubcommandData("rolling-player", "Get rolling KnockPVP-Lab stats of player")
                        )
        ).queue();

        // Commands registry

        registerInteraction("general metadata", new MetaDataCommand());
        registerInteraction("general badges", new BadgesCommand());
        registerInteraction("general uuid", new UUIDCommand());
        registerInteraction("general name", new NameCommand());
        registerInteraction("general name-history", new NamehistoryCommand());
        registerInteraction("general all-player-stats", new AllPlayerStatsCommand());

        registerInteraction("general performance player", new PerformancePlayerCommand());
        registerInteraction("general performance top", new PerfomanceTopCommand());

        registerInteraction("general tokens player", new TokensTopCommand());
        registerInteraction("general tokens top", new TokensTopCommand());

        registerInteraction("general loginstreak player", new LoginstreakPlayerCommand());
        registerInteraction("general loginstreak top", new LoginstreakPlayerCommand());

        registerInteraction("general clans details", new ClanDetailsCommand());
        registerInteraction("general clans top", new ClanTopCommand());
        registerInteraction("general clans members", new ClanMembersCommand());


        registerInteraction("minigame bedwars player", new BedwarsPlayerCommand());
        registerInteraction("minigame bedwars top", new BedwarsTopCommand());

        registerInteraction("minigame bow-spleef player", new BowSpleefPlayerCommand());
        registerInteraction("minigame bow-spleef top", new BedwarsTopCommand());

        registerInteraction("minigame cores player", new CoresPlayerCommand());
        registerInteraction("minigame cores top", new CoresTopCommand());

        registerInteraction("minigame jump-league player", new JumpLeaguePlayerCommand());
        registerInteraction("minigame jump-league top", new JumpLeagueTopCommand());

        registerInteraction("minigame knockffa player", new KnockFFAPlayerCommand());
        registerInteraction("minigame knockffa top", new KnockFFATopCommand());

        registerInteraction("minigame oneline player", new OnelinePlayerCommand());
        registerInteraction("minigame oneline top", new OnelineTopCommand());

        registerInteraction("minigame 1vs1 player", new OneVsOnePlayerCommand());
        registerInteraction("minigame 1vs1 top", new OneVsOneTopCommand());

        registerInteraction("minigame qsg player", new QsgPlayerCommand());
        registerInteraction("minigame qsg top", new QsgTopCommand());

        registerInteraction("minigame quake player", new QuakePlayerCommand());
        registerInteraction("minigame quake top", new QuakeTopCommand());

        registerInteraction("minigame rush player", new RushPlayerCommand());
        registerInteraction("minigame rush top", new RushTopCommand());

        registerInteraction("minigame skywars player", new SkywarsPlayerCommand());
        registerInteraction("minigame skywars top", new SkywarsTopCommand());

        registerInteraction("minigame snowball-fight player", new SnowballFightPlayerCommand());
        registerInteraction("minigame snowball-fight top", new SnowballFightTopCommand());

        registerInteraction("minigame spleef player", new SpleefPlayerCommand());
        registerInteraction("minigame spleef top", new SpleefTopCommand());

        registerInteraction("minigame sumo player", new SumoPlayerCommand());
        registerInteraction("minigame sumo top", new SumoTopCommand());

        registerInteraction("minigame tnt-run player", new TNTRunPlayerCommand());
        registerInteraction("minigame tnt-run top", new TNTRunTopCommand());

        registerInteraction("minigame uhc player", new UhcPlayerCommand());
        registerInteraction("minigame uhc top", new UhcTopCommand());


        registerInteraction("minesweeper player", new MinesweeperPlayerCommand());
        registerInteraction("minesweeper top", new MinesweeperTopCommand());
        registerInteraction("minesweeper best", new MinesweeperBestCommand());
        registerInteraction("minesweeper best-filtered", new MinesweeperBestFilteredCommand());
        registerInteraction("minesweeper game", new MinesweeperGameCommand());


        registerInteraction("mlgrush top", new MLGRushTopCommand());
        registerInteraction("mlgrush rolling-top", new MLGRushRollingTopCommand());
        registerInteraction("mlgrush player", new MLGRushPlayerCommand());
        registerInteraction("mlgrush rolling-player", new MLGRushRollingPlayerCommand());


        registerInteraction("fastbridge islands player", new FastbridgeIslandsPlayerCommand());
        registerInteraction("fastbridge islands top", new FastrbidgeIslandsTopCommand());
        registerInteraction("fastbridge mode player", new FastbridgeModePlayerCommand());
        registerInteraction("fastbridge mode top", new FastrbidgeModeTopCommand());


        registerInteraction("advent year", new AdventYearCommand());
        registerInteraction("advent day", new AdventDayCommand());


        registerInteraction("knockpvp top", new KnockPVPTopCommand());
        registerInteraction("knockpvp rolling-top", new KnockPVPRollingTopCommand());
        registerInteraction("knockpvp player", new KnockPVPPlayerCommand());
        registerInteraction("knockpvp rolling-player", new KnockPVPRollingPlayerCommand());
        registerInteraction("knockpvp yearly", new KnockPVPYearlyCommand());
        registerInteraction("knockpvp monthly", new KnockPVPMonthlyCommand());
        registerInteraction("knockpvp weekly", new KnockPVPWeeklyCommand());
        registerInteraction("knockpvp daily", new KnockPVPDailyCommand());
        registerInteraction("knockpvp days", new KnockPVPDaysCommand());


        registerInteraction("knockpvp-lab top", new KnockLabTopCommand());
        registerInteraction("knockpvp-lab rolling-top", new KnockLabRollingTopCommand());
        registerInteraction("knockpvp-lab player", new KnockLabPlayerCommand());
        registerInteraction("knockpvp-lab rolling-player", new KnockLabRollingPlayerCommand());

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