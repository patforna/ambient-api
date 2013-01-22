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
end

def sbt(*targets)
  sh *['./sbt'] + targets
end
