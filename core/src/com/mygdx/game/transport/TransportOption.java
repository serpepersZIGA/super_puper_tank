package com.mygdx.game.transport;

public class TransportOption extends Transport{
    public TransportOption(Transport tr){
        this.speed_inert = tr.speed_inert;this.speed = tr.speed;
        this.max_speed = tr.max_speed;this.min_speed = tr.min_speed;
        this.max_hp = tr.max_hp;
        this.damage = tr.damage;
        this.allyList = tr.allyList;
        this.armor = tr.armor;
        this.penetration = tr.penetration;
        this.acceleration = tr.acceleration;
        this.team = tr.team;
        this.tower_x_const = tr.tower_x_const;
        this.tower_y_const = tr.tower_y_const;
        this.reload_max = tr.reload_max;
        this.height = tr.height;
        this.tower_img = tr.tower_img;
        this.corpus_img = tr.corpus_img;
        this.t = tr.t;
        this.t_damage = tr.t_damage;
        this.x_tower = tr.x_tower;
        this.y_tower = tr.y_tower;
        this.difference = tr.difference;

        this.corpus_width = tr.corpus_width;
        this.corpus_height = tr.corpus_height;
        this.width_tower = tr.width_tower;
        this.height_tower = tr.height_tower;
        this.speed_tower = tr.speed_tower;this.speed_rotation = tr.speed_rotation;
        this.sound_fire = tr.sound_fire;
        data();
        this.id_unit = tr.id_unit;
        this.tower_obj = tr.tower_obj;
        const_tower_x = tr.const_tower_x;
        const_tower_y = tr.const_tower_y;


    }
}
