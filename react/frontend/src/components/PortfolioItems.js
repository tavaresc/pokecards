/**
 * Created by claudia on 21/07/17.
 * PortfolioItems calls the PokeAPI and instantiates a list of pokemons. They are all displayed on the initial page.
 */

import React, { Component } from 'react';
import PokemonList from './../utils/PokemonList';

class PortfolioItems extends Component {
    constructor() {
        super();
        //TODO: put everything into `pokemons` to keep infos linked by its index
        this.state = {
            pokemons: [],
        };
    }

    componentDidMount() {
        var axios = require('axios');
        var pk_url = "http://pokeapi.co/api/v2/pokemon/";

        this.serverRequest =
            axios.get(pk_url)
                .then(result => {
                    this.setState({
                        pokemons: result.data.results,
                    });
                })
                .catch(function(err){ console.log("Error found in PortfolioItems: " + err)});
    }

    /* This method throws an Error when a link is called. It is then necessary to click twice to go the the linked page.
     * The error message: `Uncaught TypeError: this.serverRequest.abort is not a function`
     */
    /*
    componentWillUnmount() {
        this.serverRequest.abort();
    } */

    render() {
        //console.log("Portfolio: " + this.state.pokemons);
        return (
            <div>
                <PokemonList pokemons={this.state.pokemons}/>
            </div>
        );
    }
}

export default PortfolioItems;