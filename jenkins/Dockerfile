FROM jenkins/jenkins:lts-jdk21

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

ENV CASC_JENKINS_CONFIG=/var/jenkins_home/casc.yaml

COPY casc.yaml /var/jenkins_home/casc.yaml
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
COPY jobs/ /usr/share/jenkins/ref/job-dsl/

RUN jenkins-plugin-cli --plugin-file /usr/share/jenkins/ref/plugins.txt

