/**
 * Created by claudia on 21/07/17.
 * Main shows the portfolio of pokemons.
 */

import React from 'react';
import Portfolio from './Portfolio';

const Main = () => (
    <section id="portfolio">
        <div className="container">
            <div className="row">
                <div className="col-lg-12 text-center">
                    <h2>Portfolio</h2>
                    <hr className="star-primary"/>
                </div>
            </div>

            {/* Grid portfolio */}
            <div className="row">
                <Portfolio />
            </div>
        </div>
    </section>
);

export default Main;