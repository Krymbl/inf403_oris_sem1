package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class GameState {
    private Boolean gameOver = false;
    private List<Row> table =
            List.of(new Row(), new Row(), new Row());

}
