package de.jjjannik.classes;

import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Marker;
import org.knowm.xchart.style.theme.XChartTheme;

import java.awt.*;

public class DarkTheme extends XChartTheme {
    private final Color discordGray = new Color(49,51,56,255);

    @Override
    public Font getBaseFont() {
        return new Font("Times New Roman", Font.BOLD, 20);
    }

    @Override
    public Color getChartBackgroundColor() {
        return discordGray;
    }

    @Override
    public Font getAxisTitleFont() {
        return new Font("Times New Roman", Font.PLAIN, 17);
    }

    @Override
    public Color getChartFontColor() {
        return Color.WHITE;
    }

    @Override
    public Font getChartTitleFont() {
        return getBaseFont();
    }

    @Override
    public Font getAxisTickLabelsFont() {
        return new Font("Times New Roman", Font.PLAIN, 15);
    }

    @Override
    public Color getAxisTickLabelsColor() {
        return Color.WHITE;
    }

    @Override
    public boolean isChartTitleBoxVisible() {
        return true;
    }

    @Override
    public Color getChartTitleBoxBackgroundColor() {
        return discordGray;
    }

    @Override
    public Color getChartTitleBoxBorderColor() {
        return discordGray;
    }

    @Override
    public int getChartTitlePadding() {
        return 10;
    }

    @Override
    public Color getLegendBackgroundColor() {
        return discordGray;
    }

    @Override
    public Color getLegendBorderColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public int getLegendSeriesLineLength() {
        return 20;
    }

    @Override
    public Styler.LegendPosition getLegendPosition() {
        return Styler.LegendPosition.InsideNW;
    }

    @Override
    public boolean isPlotGridLinesVisible() {
        return false;
    }

    @Override
    public boolean isPlotGridVerticalLinesVisible() {
        return false;
    }

    @Override
    public boolean isPlotGridHorizontalLinesVisible() {
        return false;
    }

    @Override
    public Color getPlotBackgroundColor() {
        return Color.GRAY;
    }

    @Override
    public Color getPlotGridLinesColor() {
        return Color.BLACK;
    }

    @Override
    public Color getPlotBorderColor() {
        return Color.BLACK;
    }

    @Override
    public Color[] getSeriesColors() {
        return new Color[]{ Color.BLUE };
    }

    @Override
    public BasicStroke[] getSeriesLines() {
        return new BasicStroke[] { SOLID };
    }

    @Override
    public Marker[] getSeriesMarkers() {
        return new Marker[] { CROSS };
    }
}