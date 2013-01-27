# ambient


## Workstation Setup

### JRebel

#### Registration

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

#### Usage

In order to get JRebel to automatically reload changed code, start the app like this:

    ./sbt
    container-start
    ~ copy-resources