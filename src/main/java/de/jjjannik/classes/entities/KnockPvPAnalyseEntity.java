package de.jjjannik.classes.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class KnockPvPAnalyseEntity {
    private final Date date;
    private long kills;
    private final float kd;

    @Override
    public KnockPvPAnalyseEntity clone() {
        return new KnockPvPAnalyseEntity(date, kills, kd);
    }
}