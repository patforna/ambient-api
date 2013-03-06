#!/usr/bin/env rake
task :default => [:all]

desc "Deletes all generated artifacts"
task 'clean:radical' do
  rm_rf ['target', 'project/target', 'project/project', '.lib']
end

desc "Builds, tests and bundles"
task :all do
  sbt 'clean', 'compile', 'test', 'bundle'
end

namespace 'mongo' do
  desc "Configures Mongo for the app"
  task :evolve do
    current_dir = File.expand_path(File.dirname(__FILE__))
    evolutions_dir = File.join(current_dir, 'ops', 'mongo', 'evolve')
    evolutions = Dir.glob(File.join(evolutions_dir, '*.js')).sort
    evolutions.each do |evolution|
      puts "About to apply: #{File.basename(evolution)}"
      `mongo #{evolution}`
      raise "Failed to apply #{File.basename(evolution)}." if $?.exitstatus != 0
    end
  end
end

namespace 'app' do
  desc "Starts the app"	
  task :start do
    fork { sh 'foreman', 'start' }
  end

  desc "Stops the app"
  task :stop do
    sh 'pkill', '-f', 'foreman'
  end

  desc "Opens the app in a browser"
  task :browse do
    sh 'open', 'http://localhost:5000'
  end
end

desc "Deploys to target environment"
task :deploy do
  # TODO call mongo:evolve on remote server
  sh 'git', 'push', 'heroku', 'master'
end

desc "Sets up a new target environment"
task :setup do
  `heroku apps:create ambient-api`
  `heroku addons:add newrelic:standard`
  `heroku config:add JAVA_OPTS='-Xmx384m -Xss512k -XX:+UseCompressedOops -javaagent:tools/newrelic/newrelic.jar'`
  # if using zerigo, make sure that registrar points at their nameservers (a.ns.zerio.net, b..., ...)
  `heroku addons:add zerigo_dns:basic`
  `heroku domains:add api.discoverambient.com`
  `heroku addons:add mongohq`
  # go to heroku dashboard and then to mongo addon to create a new user / password for the mongo console
end

def sbt(*targets)
  sh *['./sbt'] + targets
end
