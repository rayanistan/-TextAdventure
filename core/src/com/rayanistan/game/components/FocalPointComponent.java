package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class FocalPointComponent implements Component {
    public Vector2 position = new Vector2();
    public boolean current = false;
}
