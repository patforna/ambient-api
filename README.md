# Welcome to ambient

## Workstation Setup

Here's what you need on your workstation:

* XCode and Command Line Tools
* [Java 7 JDK](http://download.oracle.com/otn-pub/java/jdk/7u11-b21/jdk-7u11-macosx-x64.dmg)
* Ruby 1.9.3

        curl -L https://get.rvm.io | bash -s stable
        rvm install 1.9.3
        rvm use 1.9.3

* Homebrew

        ruby -e "$(curl -fsSkL raw.github.com/mxcl/homebrew/go)"

* A bunch of other packages

        brew install git
        brew install scala --with-docs
        brew install mongo && mongod &
        gem install heroku-toolbelt
        gem install foreman
        gem install cocoapods && pod setup

## Building

Make sure you've got Mongo running, and just execute:

         rake

## Deploying to Heroku

In order to be able to push to Heroku, you need to add it as a remote repo to your git config:

        heroku git:remote -a ambient-api

Given you've been granted the apropriate permissions, you can then deploy the app:

        rake deploy

## Facebook SDK API Reference (optional)

In order to get access to the Facebook SDK docs from within XCode, [download and install the SDK](https://developers.facebook.com/ios/).

## JRebel (optional)

JRebel can be set up with sbt so that code changes can be reloaded without server restarts.

### Registration

1.  [Register for free Scala Plan](https://my.jrebel.com/plans/)
2.  [Download latest "no-setup" archive](http://zeroturnaround.com/software/jrebel/download/prev-releases/)
3.  Unpack and run `bin/jrebel-config.sh`
4.  Select "myJRebel" option in GUI
5.  Grab authentication token from website (via "Activate JRebel" link) and paste into GUI
6.  Add following line to your `~/.bash_profile`
        
        export SCALATRA_JREBEL="tools/jrebel/jrebel.jar"

7.  Clean up:

        rm -rf ~/.jrebel

> Note: I'm actually not sure if steps 2 -5 are really necessary.

### Usage

In order to get JRebel to automatically reload changed code, start the app like this:

    ./sbt
    container-start
    ~ copy-resources
