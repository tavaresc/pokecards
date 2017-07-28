/**
 * Created by claudia on 22/07/17.
 * Pokemon creates a model of a pokemon and render information according to the current webpage:
 * - renderSprite(): it renders the sprite of a pokemon on the initial page
 * - renderCard(): it renders the card of a pokemon on the detailed page
 */

//import React, { Component } from 'react';

class Pokemon extends React.Component {
    constructor(props) {
        super(props);
        this.state = { stats: {} };
    }

    componentDidMount() {
        var name = this.props.pokemon;
        var that = this;
        var url = "/pokemons/stats/" + name;

        fetch(url)
            .then(response =>  response.json() )
            .then(data => {
                that.setState({ stats: data });
                //console.log(name, " has ", data);
            });
    }

    getStyle(ty) {
        // `sprite` is the default CSS class for a sprite
        var color = "sprite ";

        if (ty !== undefined) {
            if (ty.length === 2) // a pokemon can have at most 2 types
            /* we have created 18x18 css class name for each combination of types named `type1-type2` */
                color += (ty[0] < ty[1])? (ty[0] + "-" + ty[1]): (ty[1] + "-" + ty[0]);
            else // ty.length === 1
                color += ty[0];
        }
        return color;
    }

    renderSprite() {
        var pk_sprite = (!this.state.stats.img)? "/assets/img/pokeball.png" : this.state.stats.img;
        return (
            <div className="circle">
                <img src={pk_sprite} className="img-responsive"
                     alt={this.props.pokemon + " picture"}/>
            </div>
        );
    }

    render() {
        // console.log("Pokemon - sprite: " + this.state.sprite);
        // console.log("Pokemon - types: " + this.state.stats.types);
        return (
            <div className={this.getStyle(this.state.stats.types)}>
                {this.renderSprite()}
            </div>
        );
    }
};
//export default Pokemon;