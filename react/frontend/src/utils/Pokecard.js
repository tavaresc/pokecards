/**
 * Created by claudia on 22/07/17.
 * Pokemon creates a model of a pokemon and render information according to the current webpage:
 * - renderSprite(): it renders the sprite of a pokemon on the initial page
 * - renderCard(): it renders the card of a pokemon on the detailed page
 */

import React, { Component } from 'react';

var iconMapping = {
    normal: "star-o",
    fighting: "hand-rock-o",
    flying: "paper-plane",
    poison: "flask",
    ground: "picture-o",
    rock: "diamond",
    bug: "bug",
    ghost: "snapchat-ghost",
    steel: "magnet",
    fire: "fire",
    water: "tint",
    grass: "leaf",
    electric: "flash",
    psychic: "eye",
    ice: "snowflake-o",
    dragon: "superpowers",
    dark: "circle",
    fairy: "magic",
};

class Pokecard extends Component {
    constructor(props) {
        super(props);

        this.state = {
            // model - sprite: "",
            sprite: "",
            // model types: [{url: "", name: ""}],
            types: [{url: "", name: ""}],
            //model stats: {"hp": -1, "attack": -1, "defense": -1, "speed": -1, "special-attack": -1, "special-defense": -1},
            stats: {},
            // model avg_stats: [{ty_name: "", hp: -1, atk: -1, def: -1, spd: -1, s_atk: -1, s_def: -1}],
            avg_stats: [{ty_name: "", hp: -1, atk: -1, def: -1, spd: -1, s_atk: -1, s_def: -1}],

        };
    }

    componentDidMount() {
        var axios = require('axios');

        console.log("In Pokecard mount: " + this.props.pokemon.url);
        this.serverRequest = axios.get(this.props.pokemon.url)
            .then(result => {

                var sts = {};
                result.data.stats.map(st => (sts[st.stat.name] = st.base_stat));
                //console.log("Server: " + sts);

                var tys = [];
                tys = result.data.types.map(ty => ty.type);

                var avg_sts = tys.map(ty => (
                    {ty_name: ty.name, hp: 10, atk: 20, def: 30, spd: 40, s_atk: 50, s_def: 60}
                ));

                this.setState({
                    types: tys,
                    sprite: result.data.sprites.front_default,
                    stats: sts,
                    avg_stats: avg_sts,
                });

                console.log("In Pokecard - avg_stats: " + this.state.avg_stats);
            })
            .catch(function(err){ console.log("Error found in Pokecard: " + err)});
    }

    getStyle(tys) {
        // `sprite` is the default CSS class for a sprite
        var color = "sprite ";

        if (tys !== undefined) {
            if (tys.length === 2) // a pokemon can have at most 2 types
            /* we have created 18x18 css class name for each combination of types named `type1-type2` */
                color += (tys[0].name < tys[1].name)? (tys[0].name + "-" + tys[1].name): (tys[1].name + "-" + tys[0].name);
            else // ty.length === 1
                color += tys[0].name;
        }
        return color;
    }

    renderIcons(ty_name) {
        var iconClass = "fa fa-" + iconMapping[ty_name];
        var spanClass = "btn btn-circle " + ty_name;
        return (
            <li key={ty_name}>
                <span title={ty_name} className={spanClass}><i className={iconClass}></i></span>
            </li>
        );
    }

    renderSprite() {
        // some pokemons do not have sprites
        var pk_sprite = (!(this.state.sprite))? ("./img/pokeball.png") : (this.state.sprite);
        return (
            <div className="circle">
                <img src={pk_sprite} className="img-responsive"
                     alt={this.props.pokemon.name + " picture"}/>
            </div>
        );
    }

    renderStats(pk_name) {
        // it avoids undefined states not yet set
        if (this.state.stats !== undefined && this.state.avg_stats !== undefined) {
            console.log("In renderStats: " + this.state.stats["attack"]);

            return (
                <div key={pk_name} className="name">
                    <h3>Attack: {this.state.stats["attack"]}
                        {this.state.avg_stats.map(ty_avg =>
                            <span key={ty_avg.ty_name} className={ty_avg.ty_name}>{ty_avg.atk}</span>)}
                    </h3>
                    <h3>Defense: {this.state.stats["defense"]}
                        {this.state.avg_stats.map(ty_avg =>
                            <span key={ty_avg.ty_name} className={ty_avg.ty_name}>{ty_avg.def}</span>)}
                    </h3>
                    <h3>Speed: {this.state.stats["speed"]}
                        {this.state.avg_stats.map(ty_avg =>
                            <span key={ty_avg.ty_name} className={ty_avg.ty_name}>{ty_avg.spd}</span>)}
                    </h3>
                    <h3>Sp Attak: {this.state.stats["special-attack"]}
                        {this.state.avg_stats.map(ty_avg =>
                            <span key={ty_avg.ty_name} className={ty_avg.ty_name}>{ty_avg.s_atk}</span>)}
                    </h3>
                    <h3>Sp Defense: {this.state.stats["special-defense"]}
                        {this.state.avg_stats.map(ty_avg =>
                            <span key={ty_avg.ty_name} className={ty_avg.ty_name}>{ty_avg.s_def}</span>)}
                    </h3>
                </div>
            );
        }
        return ;
    }

    renderCard() {
        // console.log("In renderCard: " + this.props.pokemon);
        // it avoids undefined props lost in the context
        if (this.props.pokemon.name !== undefined) {
            return (
                <div className="card">

                    <div className="row-fluid">
                        <div className="col-xs-2 hp">
                            <h2>{this.state.stats["hp"]}</h2>
                        </div>
                        <div className="col-xs-10">
                            <h3>{this.props.pokemon.name}</h3>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-xs-12">
                            <div className={this.getStyle(this.state.types)}>
                                {this.renderSprite()}
                            </div>
                            <ul className="list-inline text-right">
                                {this.state.types.map(ty => this.renderIcons(ty.name))}
                            </ul>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-xs-12">
                            <div className="todo"><a href="#">Like Dislike</a></div>
                            <div>
                                {this.renderStats(this.props.pokemon.name)}
                            </div>
                        </div>
                    </div>

                </div>
            );
        }
        return(<div></div>);
    }

    render() {
        console.log("Pokecard - sprite: " + this.state.sprite);
        console.log("Pokecard - types: " + this.state.types);

        return (
            <div>
                {this.renderCard()}
            </div>
        );
    }
};

export default Pokecard;