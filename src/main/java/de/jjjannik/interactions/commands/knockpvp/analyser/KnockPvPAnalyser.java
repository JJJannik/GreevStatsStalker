package de.jjjannik.interactions.commands.knockpvp.analyser;

import de.jjjannik.JGAInitializer;
import de.jjjannik.api.JGA;
import de.jjjannik.classes.DarkTheme;
import de.jjjannik.classes.entities.KDStatsEntity;
import de.jjjannik.entities.basic.KillsDeathsPlayer;
import de.jjjannik.entities.basic.Player;
import de.jjjannik.utils.exceptions.APICallException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.*;
import org.knowm.xchart.style.XYStyler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class KnockPvPAnalyser {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final Player player;
    private final JGA jga;

    public static void main(String[] args) {
        JGAInitializer.init();
        JGA jga = JGAInitializer.getJGA();

        KnockPvPAnalyser analyser = new KnockPvPAnalyser(jga.getPlayerUUID(""), jga);

        List<KDStatsEntity> data = analyser.retrieveStats(365*2, 7);

        analyser.createKDStatsChart(data);
    }

    public List<KDStatsEntity> retrieveStats(int sinceDays, int periodDays) {
        List<KDStatsEntity> data = new ArrayList<>();

        Instant now = Instant.now(Clock.systemUTC());

        data.add(new KDStatsEntity(Date.from(now.minusSeconds(TimeUnit.DAYS.toSeconds(sinceDays))), 0));

        for (long i = now.minusSeconds(TimeUnit.DAYS.toSeconds(sinceDays)).getEpochSecond(); (i+TimeUnit.DAYS.toSeconds(periodDays)) < now.getEpochSecond(); i += TimeUnit.DAYS.toSeconds(periodDays)) {

            KDStatsEntity statsEntity;
            try {
                KillsDeathsPlayer stats = jga.getRollingKnockPvPPlayer(player.getUuid(), i, i + TimeUnit.DAYS.toSeconds(periodDays));

                statsEntity = new KDStatsEntity(Date.from(Instant.ofEpochSecond(i)), (float) stats.getKills() / (stats.getDeaths() == 0 ? 1 : stats.getDeaths()));
            } catch (APICallException e) {
                statsEntity = data.get(data.size() - 1);
            }

            data.add(statsEntity);

            long sysMillis = System.currentTimeMillis();
            while (System.currentTimeMillis() - sysMillis < 1000) {
                // -> delay to avoid API rate limit
            }
        }
        return data;
    }

    public void createKDStatsChart(List<KDStatsEntity> data) {
        final XYChart chart = new XYChartBuilder()
                .width(700)
                .height(600)
                .title("Trend of the K/D ratio of %s from %s to %s".formatted(
                        this.player.getName(),
                        dateFormat.format(data.get(0).date()),
                        dateFormat.format(data.get(data.size()-1).date())
                ))
                .xAxisTitle("Date")
                .yAxisTitle("K/D Ratio")
                .build();

        XYStyler styler = chart.getStyler();
        styler.setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        styler.setTheme(new DarkTheme());
        styler.setDecimalPattern("#.##");
        styler.setLocale(Locale.US);
        styler.setTimezone(TimeZone.getTimeZone("UTC"));

        chart.addSeries("K/D",
                data.stream().map(KDStatsEntity::date).toList(),
                data.stream().map(KDStatsEntity::kd).toList()
        );

        try {
            BitmapEncoder.saveBitmapWithDPI(chart, "./temp-unique-name", BitmapEncoder.BitmapFormat.PNG, 300);
        } catch (IOException e) {
            log.error("Could not create image from chart", e);
        }

        new SwingWrapper<>(chart).displayChart();
    }
}