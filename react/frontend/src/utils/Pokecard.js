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
            sprite: "",
            types: [{url: "", name: ""}],
        };
    }

    componentDidMount() {
        var axios = require('axios');

        console.log("In Pokecard mount: " + this.props.pokemon.url);
        this.serverRequest = axios.get(this.props.pokemon.url)
            .then(result => {
                console.log("Server: " + result.data.types.map(ty => ty.type));
                this.setState({
                    types: result.data.types.map(ty => ty.type),
                    // NOT sure it works
                    sprite: result.data.sprites.front_default,
                });
            })
            .catch(function(err){ console.log("Error found in Pokecard: " + err)});
    }

    getStyle(ty) {
        // `sprite` is the default CSS class for a sprite
        var color = "sprite ";

        if (ty !== undefined) {
            if (ty.length === 2) // a pokemon can have at most 2 types
            /* we have created 18x18 css class name for each combination of types named `type1-type2` */
                color += (ty[0].name < ty[1].name)? (ty[0].name + "-" + ty[1].name): (ty[1].name + "-" + ty[0].name);
            else // ty.length === 1
                color += ty[0].name;
        }
        return color;
    }

    renderIcons(ty_name) {
        var iconClass = "fa fa-" + iconMapping[ty_name];
        var spanClass = "btn btn-circle text-white " + ty_name;
        return (
            <li>
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

    renderCard() {
        // it avoids undefined props lost in the context
        if (this.props.pokemon.name !== undefined) {
            return (
                <div className="card">

                    <div className="row">
                        <div className="col-xs-9">
                            <h3>{this.props.pokemon.name}</h3>
                        </div>
                        <div className="col-xs-3 text-right">
                            <h2>HP</h2>
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
                            <div className="name"><h3>Stat 1</h3></div>
                            <div className="name"><h3>Stat 2</h3></div>
                        </div>
                    </div>

                </div>
            );
        }
        return ;
    }

    render() {
        console.log("Pokecard - sprite: " + this.state.sprite);
        console.log("Pokecard - types: " + this.state.types);

        return (
            <div>
                {this.renderCard()}
            </div>
        )
    }
};
export default Pokecard;