/**
 * Created by claudia on 22/07/17.
 * Pokemon creates a model of a pokemon and render information according to the current webpage:
 * - renderSprite(): it renders the sprite of a pokemon on the initial page
 * - renderCard(): it renders the card of a pokemon on the detailed page
 */

//import React, { Component } from 'react';

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

class Pokecard extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            stats: { types: [] }, // Just to avoid a map on undefined below in renderCard
            // model avg_stats: [{ty_name: "", hp: -1, atk: -1, def: -1, spd: -1, s_atk: -1, s_def: -1}],
            avg_stats: [],

        };
    }

    componentDidMount() {
        console.log("In Pokecard mount: " + this.props.pokemon.url);
        fetch(this.props.pokemon.url).then(r => r.json()).then(
            d => {
                d.types.map(ty => {
                    // {ty_name: ty, hp: 10, atk: 20, def: 30, spd: 40, s_atk: 50, s_def: 60}
                    fetch("/types/stats/" + ty)
                        .then(r => { console.log(d); return r.json()})
                        .then(typestats => {
                            console.log("TYPESTATS: ", this.props.pokemon.name, typestats);
                            this.setState((prevState, props) => ({
                                avg_stats: prevState.avg_stats.concat([typestats])
                            }));
                        });
                });
                console.log(d);
                this.setState({ stats: d});
            }
        );
    }

    getStyle(tys) {
        // `sprite` is the default CSS class for a sprite
        var color = "sprite ";

        if (tys !== undefined) {
            if (tys.length === 2) // a pokemon can have at most 2 types
            /* we have created 18x18 css class name for each combination of types named `type1-type2` */
                color += (tys[0] < tys[1])? (tys[0] + "-" + tys[1]): (tys[1] + "-" + tys[0]);
            else // ty.length === 1
                color += tys[0];
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
        var pk_sprite = (!this.state.stats.img)? "/assets/img/pokeball.png" : this.state.stats.img;
        return (
            <div className="circle">
                <img src={pk_sprite} className="img-responsive"
                     alt={this.props.pokemon.name + " picture"}/>
            </div>
        );
    }

    getWidth(num, dom_elem) {
        var w =  100 * num / 255; // get width of div since w will be rendered in `px`
        console.log("WIDTH: ", w);
        return { width: w + '%'};

    }

    renderOneStat(stat_name) {
        return (
            <div className="row">
                <div className="col-xs-2">
                    <h4>{stat_name}</h4>
                </div>

                <div className="col-xs-10">
                    <div className="progress">
                        <div className="progress-bar main-bar" role="progressbar"
                             style={this.getWidth(this.state.stats[stat_name])}
                             aria-valuenow={this.state.stats[stat_name]} aria-valuemin="0" aria-valuemax="100">
                            <span className="stat_value">{this.state.stats[stat_name] }</span>
                        </div>
                    </div>
                    {this.state.avg_stats.map(
                        (ty_avg,id) => {
                            var pclass = "progress-bar " + ty_avg.name;
                            return (
                                <div className="progress">
                                    <div className={pclass} role="progressbar"
                                         style={this.getWidth(ty_avg[stat_name])}
                                         aria-valuenow={ty_avg[stat_name]} aria-valuemin="0" aria-valuemax="100">
                                        <span className="stat_value"> {ty_avg[stat_name] }</span>
                                    </div>
                                </div>
                            );
                        })
                    }
                </div>
            </div>
        );
    }

    renderStats(pk_name) {
        // it avoids undefined states not yet set
        if (this.state.stats !== undefined && this.state.avg_stats !== undefined) {
            console.log("In renderStats: " + this.state.stats["attack"]);
            //console.log("avg_stats: " + this.state.avg_stats);

            var bar = {width: 83}
            return (
                <div key={pk_name} className="name">
                    {this.renderOneStat("attack")}
                    {this.renderOneStat("defense")}
                    {this.renderOneStat("speed")}
                    {this.renderOneStat("sp_attack")}
                    {this.renderOneStat("sp_defense")}
                </div>
            );
        }
        return (<div></div>);
    }

    renderCard() {
        // console.log("In renderCard: " + this.props.pokemon);
        // it avoids undefined props lost in the context
        if (this.props.pokemon.name !== undefined) {
            return (
                <div className="card">

                    <div className="row-fluid">
                        <div className="col-xs-2 hp">
                            <h2>{this.state.stats.hp}</h2>
                        </div>
                        <div className="col-xs-10">
                            <h3>{this.props.pokemon.name}</h3>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-xs-12">
                            <div className={this.getStyle(this.state.stats.types)}>
                                {this.renderSprite()}
                            </div>
                            <ul className="list-inline text-right">
                                {this.state.stats.types.map(this.renderIcons)}
                            </ul>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-xs-12">
                            <div className="opinion">
                                <Opinion name={this.props.pokemon.name}/>
                            </div>
                            <div>
                                {this.renderStats(this.props.pokemon.name)}
                            </div>
                        </div>
                    </div>

                </div>
            );
        }
        return (<div></div>);
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

//export default Pokecard;