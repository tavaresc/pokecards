/**
 * Created by claudia on 20/07/17.
 * Home builds the structure of the initial homepage.
 */
/** Imports useful for ReactJS
import React from 'react';
import Header from './Header';
import Navbar from './Navbar';
import Portfolio from './Portfolio';
import About from './About';
import Footer from './Footer';
 */

const Home = () => (
    <div>
        <Navbar />
        <Header />
        <Portfolio />
        <About />
        <Footer />
    </div>
);


/**
 * This code comes originally from index.js (in ReactJS)
 */
ReactDOM.render(
    <Home />,
    document.getElementById('root')
);

//export default Home;