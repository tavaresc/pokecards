/**
 * Created by claudia on 22/07/17.
 */


import React, { Component } from 'react';

class Pokemons extends Component {
    constructor() {
        super();
        //TODO: put everything into `pokemons` to keep infos linked by its index
        this.state = {
            pokemons: [{url: "", name: ""}],
            sprites: [""],
            types: [[{slot: -1, types: {url: "", ty_name: ""}}]],
        };
    }

    componentDidMount() {
        var axios = require('axios');
        var pk_url = "http://pokeapi.co/api/v2/pokemon/";
        var pk_github = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/";

        this.serverRequest =
            axios.get(pk_url)
                .then(result => {
                    this.setState({
                        pokemons: result.data.results,
                    });

                    // TODO: Find a way to make sps immutable
                    var sps = [];
                    var tys = [];
                    for (var i = 0; i < this.state.pokemons.length; i++) {
                        sps.push(pk_github + (i+1) + ".png");
                        //console.log("In FOR - sp:" + this.state.sprites);
                        //this.setState({ sprites: [this.state.sprites, (pk_github + (i+1) + ".png")] });

                        // find the type fo each pokemon
                        axios.get(result.data.results[i].url)
                            .then(ty_result => {
                                tys.push(ty_result.data.types)
                            });

                    }
                    this.setState({ sprites: sps });
                    this.setState({
                        types: tys,
                    });

                })
    }

    /* This method throws an Error when a link is called. It is then necessary to click twice to go the the linked page.
     * The error message: `Uncaught TypeError: this.serverRequest.abort is not a function`
     */
    /*
     componentWillUnmount() {
     this.serverRequest.abort();
     } */

    getStyles(index) {
        console.log("style: " + this.state.types[index]);
        // Set background colors based on the pokemon type
        const spriteStyle = {
            //background: "white linear-gradient(90deg, #EE99AC 50%, #A8A878 50%) repeat scroll 0% 0%"
            background: "red"
        };
        return spriteStyle;
    }

    render() {
        console.log(this.state.pokemons);
        console.log(this.state.sprites);
        console.log(this.state.types);
        console.log("types length: " + this.state.types.length);

        // pk => <Pokemon key={pk.name} name={pk.name} url={pk.url} />
        return (
            <div>
                {this.state.pokemons.map(pk => (
                    <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item" key={pk.name}>
                        <Link to='/pokecard' className="portfolio-link">
                            <div className="caption">
                                <div className="caption-content">
                                    <i className="fa fa-search-plus fa-3x"></i>
                                </div>
                            </div>
                            <div className="sprite" style={this.getStyles(this.state.pokemons.indexOf(pk))}>
                                <div className="circle">
                                    <img src={this.state.sprites[this.state.pokemons.indexOf(pk)]} className="img-responsive"
                                         alt={pk.name + " picture"}/>
                                </div>
                            </div>
                        </Link>
                        <div className="name"><h3>{pk.name}</h3></div>
                        <div className="todo"><a href="#">Like   Dislike</a></div>
                    </div>
                ))}
            </div>
        );
    }
}

export default Pokemons;