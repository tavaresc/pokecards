/**
 * Created by claudia on 20/07/17.
 * About only shows an info section.
 */

//import React from 'react';

const About = () => (
    <section className="success" id="about">
        <div className="container">
            <div className="row">
                <div className="col-lg-12 text-center">
                    <h2>About</h2>
                    <hr className="star-light"/>
                </div>
            </div>
            <div className="row">
                <div className="col-lg-4 col-lg-offset-2">
                    <p>PokeCards is an application to display and visualize basic statistics about Pokemons.</p>
                </div>
                <div className="col-lg-4 t">
                    <p>Furthermore, it is able to register likes and dislikes about each and every Pokemon and display
                        them. When you click on a Pokemon, you can access its card where it is compared to the Pokemons
                        of his types.</p>
                </div>
                <div className="col-lg-8 col-lg-offset-2 text-center">
                    <a href="https://github.com/tavaresc/pokecards" className="btn btn-lg btn-outline">
                        <i className="fa fa-github"></i> Pokecards on GitHub
                    </a>
                </div>
            </div>
        </div>
    </section>
);

//export default About;