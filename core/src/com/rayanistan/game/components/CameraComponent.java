package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.rayanistan.game.utils.GameCamera;

public class CameraComponent implements Component {
    public GameCamera camera;
    public Entity target;
}
