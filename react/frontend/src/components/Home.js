/**
 * Created by claudia on 20/07/17.
 * Home builds the structure of the initial homepage.
 */
import React from 'react';
import Header from './Header';
import Main from './Main';
import About from './About';
import Footer from './Footer';

const Home = () => (
    <div>
        <Header />
        <Main />
        <About />
        <Footer />
    </div>
);

export default Home;