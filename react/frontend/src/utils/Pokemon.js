/**
 * Created by claudia on 22/07/17.
 * Pokemon creates a model of a pokemon and render information according to the current webpage:
 * - renderSprite(): it renders the sprite of a pokemon on the initial page
 * - renderCard(): it renders the card of a pokemon on the detailed page
 */

import React, { Component } from 'react';

class Pokemon extends Component {
    constructor(props) {
        super(props);

        this.state = {
            sprite: "",
            types: [{url: "", name: ""}],
        };
    }

    componentDidMount() {
        var axios = require('axios');

        console.log("In Pokemon mount: " + this.props.pokemon.url);
        this.serverRequest = axios.get(this.props.pokemon.url)
            .then(result => {
                this.setState({
                    types: result.data.types.map(ty => ty.type),
                    // NOT sure it works
                    sprite: result.data.sprites.front_default,
                });
            })
            .catch(function(err){ console.log("Error found in Pokemon: " + err)});
    }

    componentWillUnmount() {}

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

    render() {
        console.log("Pokemon - sprite: " + this.state.sprite);
        console.log("Pokemon - types: " + this.state.types);

        return (
            <div className={this.getStyle(this.state.types)}>
                {this.renderSprite()}
            </div>
        )
    }
};
export default Pokemon;