/**
 * Created by claudia on 21/07/17.
 * Main shows the portfolio of pokemons.
 */

import React from 'react';

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
            <div className="row" text-center>
                <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item">
                    <a href="#portfolioModal1" className="portfolio-link" data-toggle="modal">
                        <div className="caption">
                            <div className="caption-content">
                                <i className="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <div className="sprite">
                            <div className="circle">
                                <img src="%PUBLIC_URL%/img/poke39.png" className="img-responsive" alt="Pokemon"/>
                            </div>
                        </div>
                    </a>
                    <div className="name"><h3>Name</h3></div>
                    <div className="todo"><a href="#">Like   Dislike</a></div>
                </div>
                <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item">
                    <a href="#portfolioModal1" className="portfolio-link" data-toggle="modal">
                        <div className="caption">
                            <div className="caption-content">
                                <i className="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <div className="sprite">
                            <div className="circle">
                                <img src="%PUBLIC_URL%/img/poke39.png" className="img-responsive" alt="Pokemon"/>
                            </div>
                        </div>
                    </a>
                    <div className="name"><h3>Name</h3></div>
                    <div className="todo"><a href="#">Like   Dislike</a></div>
                </div>
                <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item">
                    <a href="#portfolioModal1" className="portfolio-link" data-toggle="modal">
                        <div className="caption">
                            <div className="caption-content">
                                <i className="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <div className="sprite">
                            <div className="circle" id="pokemon">
                                <img src="%PUBLIC_URL%/img/poke39.png" class="img-responsive" alt="Pokemon"/>
                            </div>
                        </div>
                    </a>
                    <div className="name"><h3>Name</h3></div>
                    <div className="todo"><a href="#">Like   Dislike</a></div>
                </div>
                <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item">
                    <a href="#portfolioModal1" className="portfolio-link" data-toggle="modal">
                        <div className="caption">
                            <div className="caption-content">
                                <i className="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <div className="sprite">
                            <div className="circle">
                                <img src="%PUBLIC_URL%/img/poke39.png" className="img-responsive" alt="Pokemon"/>
                            </div>
                        </div>
                    </a>
                    <div className="name"><h3>Name</h3></div>
                    <div className="todo"><a href="#">Like   Dislike</a></div>
                </div>
                <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item">
                    <a href="#portfolioModal1" className="portfolio-link" data-toggle="modal">
                        <div className="caption">
                            <div className="caption-content">
                                <i className="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <div className="sprite">
                            <div className="circle">
                                <img src="%PUBLIC_URL%/img/poke39.png" className="img-responsive" alt="Pokemon"/>
                            </div>
                        </div>
                    </a>
                    <div className="name"><h3>Name</h3></div>
                    <div className="todo"><a href="#">Like   Dislike</a></div>
                </div>
                <div className="col-xs-6 col-sm-3 col-md-2 portfolio-item">
                    <a href="#portfolioModal1" className="portfolio-link" data-toggle="modal">
                        <div className="caption">
                            <div className="caption-content">
                                <i className="fa fa-search-plus fa-3x"></i>
                            </div>
                        </div>
                        <div className="sprite">
                            <div className="circle">
                                <img src="%PUBLIC_URL%/img/poke39.png" className="img-responsive" alt="Pokemon"/>
                            </div>
                        </div>
                    </a>
                    <div className="name">
                        <h3>Name</h3>
                    </div>
                    <div className="todo">
                        <a href="#">Like   Dislike</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
);

export default Main;