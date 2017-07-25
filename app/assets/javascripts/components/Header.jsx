/**
 * Created by claudia on 20/07/17.
 * Header only shows the big Pokecards logo.
 */
//import React from 'react';

const Header = () => (
    <header>
        <div className="container" id="maincontent" tabIndex="-1">
            <div className="row">
                <div className="col-lg-12">
                    <img className="img-responsive" src="./img/logo.png" alt="Pokecards logo"/>
                   </div>
            </div>
        </div>
    </header>
);

//export default Header;