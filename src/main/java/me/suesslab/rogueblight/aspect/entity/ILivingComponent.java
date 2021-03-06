/*
 * Copyright (C) 2020 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package me.suesslab.rogueblight.aspect.entity;

import me.suesslab.rogueblight.aspect.Alignment;
import me.suesslab.rogueblight.aspect.Emotion;
import me.suesslab.rogueblight.aspect.IComponent;
import me.suesslab.rogueblight.entity.Entity;
import me.suesslab.rogueblight.interact.ThrownTooledInteraction;
import me.suesslab.rogueblight.interact.TooledInteraction;

/**
 *
 * @author justin
 */
public interface ILivingComponent extends IComponent {

    public Emotion getEmotion();

    public Alignment getAlignment();

    public double getHealthRemaining();

    public double getMaxHealth();

    public int getMovementSpeed();

    public double getThrowingStrength();


    /**
     * @return A number representing the angle the entity is facing
     */
    public double getOrientationAngle();


    
}
