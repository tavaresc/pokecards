/**
 * Created by claudia on 21/07/17.
 * Portfolio calls the PokeAPI and instantiates a list of pokemons. They are all displayed on the initial page.
 */

import React, { Component } from 'react';
import PokemonList from './../utils/PokemonList';

class Portfolio extends Component {
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
                });
    }

    /* This method throws an Error when a link is called. It is then necessary to click twice to go the the linked page.
     * The error message: `Uncaught TypeError: this.serverRequest.abort is not a function`
     */
    /*
    componentWillUnmount() {
        this.serverRequest.abort();
    } */

    render() {
        console.log("Portfolio: " + this.state.pokemons);

        // pk => <Pokemon key={pk.name} name={pk.name} url={pk.url} />
        return (
            <div>
                <PokemonList pokemons={this.state.pokemons}/>
            </div>
        );
    }
}

export default Portfolio;

// See https://github.com/facebookincubator/create-react-app/blob/master/packages/react-scripts/template/README.md#adding-a-css-preprocessor-sass-less-etc
//import './less/freelancer.less';
/*
 class Test extends React.Component {
 render() {
 return (
 <div className="test">
 <p>Whether you're a student looking to showcase your work, a professional looking to attract clients, or a
 graphic artist looking to share your projects, this template is the perfect starting point!</p>
 </div>
 );
 }
 }

 ReactDOM.render(
 <Test />,
 document.getElementById('root')
 );
 */