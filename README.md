# Pokecards Alpha

This is a starter application to show Pokemons cards such as in the "Top
Trumps" game. It is build from the Play Java Starter Example (2.5.x) available at
https://github.com/playframework/play-scala-starter-example/tree/2.5.x .


## Running

Run this using [sbt](http://www.scala-sbt.org/).  If you downloaded this project from http://www.playframework.com/download then you'll find a prepackaged version of sbt in the project directory:

```
sbt run
```

And then go to http://localhost:9000 to see the running web application.

There are several demonstration files available in this template.


## Dependencies

- Slick + H2
- JQuery
- JQuery Autocomplete
- Bootstrap 3
- Play 
- Ws for Play
- Twitter4s :: a nice Scala wrapper over Twitter's API


## Miscellaneous observations

- Pokemons have at most 2 types
  Check
  https://bulbapedia.bulbagarden.net/wiki/List_of_type_combinations_by_abundance


# Why I chose XXX 
  
  In general, PokeCards does not need to be optimized. 
  The biggest latency comes from the network.
  
## JQuery

 - Pros :: easy, know it, available everywher
 - Cons :: it's JQuery (some say it's big)
 
 
## Slick

 - Nice functional (ORM-like) abstraction over all sorts of database
 - Good that we do not have to commit so 1 DB early on
 - H2 is sufficient for PokeCards (around 800 Pokemons, 18 Types) 


## Bootstrap 3

  - Because I know it
  
## Play

  - Out of curiosity, I had to choose a framework: so why not choose the one
    Zengularity is using

# Architecture

## Model of Pokedex data (`pokedex` package)

The `pokeapi` data description is very useful. It is sometimes buggy: data are defined as
integer but returned as string, they are sometimes optional but it's not
mentioned.

We have defined a Scala modelization of the data with case classes (`models`)
and a (small) set of utility function to make REST call to the `pokeapi` API (`API`).

The automatic de/serialization of Json data is at its best here.


## Models

The `models` package defines the data we are going to JSON back through the rest
API. Basically, this is a relevant subset of the fields fount in `pokeapi` or
`Twitter4s`.

These are the data we put into our database for quicker later access.

## DB (`dal` package)

There's a small DB with 3 tables (1.sql, 2.sql, 3.sql) on the server side
1. To save the opinions (lies, dislikes) on Pokemons
2. To save the relevant subset of information from a general Pokemon
   (PokemonStats)
3. To save the relevant average stats for all the types

All in all, except for 1, 2 and 3 act like a local cache for the information we
want w.r.t to the pokedex REST API.

There is a small hack in the `pokemon_stats` to store the list of types of a
Pokemon as a String. It might have been better to have two type columns since
there are at most two types for a Pokemon (this information came later on).


## REST API


Most resources are available for reading through a GET request. Here's an
extract from `conf/routes`.

```
GET     /pokemons/opinions/:name      controllers.OpinionController.getOpinion(name)

GET     /pokemons/stats/:name         controllers.StatsController.getStats(name)

GET     /types/stats/:typename        controllers.StatsController.getTypeStats(typename)

GET     /twitterfeed/:name           controllers.TwitterController.get(name)
```


Basically the UI is built upon asynchronous calls to this API


## Views

Not much going on here for now

## CSS

Nothing :-)

# Experience return

  - A lot of days lost just to install things, to check dependencies, ...
  - A lot of time lost on out-of-date or just bad documentation


## Twitter

Did not use Twitter before. Had to create an account and create the OAuth
information for the app. Then lost .5 day before finding Twitter4s


## Slick

Lost a lot of time installing Slick and making it work on a standalone
application. 

Had to start from scratch with the Slick example for 2.5.x

Not checked out how Slick can do scaffolding (automatic data generation from a
database schema), which might be nice


# Enhancements

- Caching the Twitter calls (with Play cache) to avoid going over Twitter's rate
  limits
- Better Front-end modelization (React ?)
- If there were more data, we might need to use Streaming and chunks of
  data. Play seems to be able to handle that.
- The app does not handle failures gracefully. Better uses of `Future`s would
  handle the error out of the monad easily.
- The way the models and DBs have been created is very repetitive. It's pretty
  sure there's a way to generate this code.