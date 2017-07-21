/**
 * Created by claudia on 21/07/17.
 */
/**
 * Created by claudia on 21/07/17.
 * A simple data API that will be used to get the data for our pokemons.
 */
import { Component } from 'react';


class PokeAPI extends Component {
    constructor() {
        super();
        this.state = { pokemons: [{url: "", name: "", sprite_url: ""}] }
    };

    componentDidMount() {
        // Is there a React-y way to avoid rebinding `this`? fat arrow?
        var axios = require('axios');
        var pokeapi_url = this.props.source;

        var th = this;
        this.serverRequest =
            axios.get(pokeapi_url)
                .then(function(result) {
                    th.setState({ pokemons: result.data.results })
                });
        /*
         poke_list.map((u, n) =>
         {
         this.pokemons.url = u;
         this.pokemons.name = n;
         this.pokemons.sprite = this.pokemons.length;
         })
         */

        console.log("Pokemons" + this.pokemons);

        var sprite_repo = "";
        this.serverRequest =
            axios.get(this.pokemons[0].url)
                .then(function(result) {
                    sprite_repo = result.sprites.front_default;
                });

        console.log("Sprite before: " + sprite_repo);
        sprite_repo.replace(/\/(\d*).png/,"/");
        console.log("Sprite after: " + sprite_repo);
        //for (var i = 0; i < pokemons.length; i++) {
        //    pokemons[i].sprite_url = sprite_repo + (i + 1) + ".png";
        //}
    };

    componentWillUnmount() {
        this.serverRequest.abort();
    };

    getAll() {
        return this.state.pokemons;
    };

    getPokemon(pk_name) {
        const isPokemon = pk => pk.name === pk_name;
        return this.state.pokemons.find(isPokemon);
    }
};

export default PokeAPI;