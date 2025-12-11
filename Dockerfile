FROM tomcat:9-jdk17

# Remove default ROOT application
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy your WAR file and deploy it as ROOT
COPY jobportal.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]