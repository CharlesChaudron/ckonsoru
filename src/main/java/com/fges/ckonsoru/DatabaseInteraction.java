package com.fges.ckonsoru;

import java.time.LocalDate;
import java.util.Collection;

public interface DatabaseInteraction {
    public Collection findCrenauxDate(LocalDate date);
}
