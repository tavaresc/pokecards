/**
 * Created by claudia on 21/07/17.
 * Portfolio display each pokemon item of the portfolio on the initial page.
 */

import React, { Component } from 'react';
import { Link } from 'react-router-dom';

class Portfolio extends Component {
    constructor() {
        super();
        this.state = { pokemons: [{url: "", name: ""}], sprites: [""]};
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

                    // Find a way to make sps immutable
                    var sps = [];
                    for (var i = 0; i < this.state.pokemons.length; i++) {
                        sps.push(pk_github + (i+1) + ".png");
                        //console.log("In FOR - sp:" + this.state.sprites);
                        //this.setState({ sprites: [this.state.sprites, (pk_github + (i+1) + ".png")] });
                    }
                    this.setState({ sprites: sps });
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
        console.log(this.state.pokemons);
        console.log(this.state.sprites);

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
                            <div className="sprite">
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