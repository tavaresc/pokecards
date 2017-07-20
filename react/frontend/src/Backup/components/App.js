/**
 * Created by claudia on 18/07/17.
 * Creates a router that handles all the routes in the application.
 * See https://medium.com/@harinilabs/day-2-react-router-state-and-props-301ca55c5aaf
 */
import React, { Component } from 'react';

class App extends Component {
    constructor() {
        super();
        this.state = { pokemons: [{url: "", name: ""}], sprites: [""]};
    }

    componentDidMount() {
        // Is there a React-y way to avoid rebinding `this`? fat arrow?
        var axios = require('axios');

        var th = this;
        this.serverRequest =
            axios.get(this.props.source)
                .then(function(result) {
                    th.setState({
                        pokemons: result.data.results,
                    });
                });

        var sps = [];
        for (var i = 0; i < this.state.pokemons.length; i++) {
            sps[i] = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (i+1) + ".png";
        }
        th.setState({ sprites: sps });
    }

    componentWillUnmount() {
        this.serverRequest.abort();
    }

    render() {
        //new Test();
        console.log(this.state.pokemons);
        //this.state.pokemons;
        var path = this.state.pokemons[0].url;
        console.log(path);
        return (
            <div className="test">
                <img src={this.state.sprites[0]} alt={this.state.pokemons[0].name}></img>
            </div>
        );
    }
}

export default App;

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