package org.dayaway.crazytoaster.levelApi;

import java.util.List;

public interface ILevel {

    boolean isOpen();

    boolean isEnding();

    List<ELevel> get();

    int getId();

    void add(ELevel eLevel);
}
