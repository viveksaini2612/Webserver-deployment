FROM centos
RUN yum install httpd -y
RUN yum install php -y
RUN yum install /sbin/service -y
COPY index.html /var/www/html ------- Open 80 port or expose
CMD /usr/sbin/httpd -DFOREGROUND && /bin/bash
