javaee7
=======
My JavaEE7 samples, proof of concepts and other things I can think of :)

* jax-rs contains the jax-rs webservices demo/test applications
    * Ftl is a restfull webservice using ftl as an output templating engine.
    * Ftl Provider is also a restfull webservice using ftl as an output engine but implemented differently.
    * fileupload shows howto do a multipart file upload.
    * jpa2json2xml Web application using JPA/JSON/JAXB entities backend and an angular.js as a frontend.
    * csrf using a servlet filter to create a CSRF token and check if the token is available during POST,PUT and DELETE methods

* jax-ws contains the jax-ws webservices demo/test applications
    * webservice implements some features I found and collected on the internet. Currently it implements 
xsd validation using a Handler and implements both JUnit tests and Integration testing

* jpa contains the jpa demo/test applications
    * collection uses an @Embeddable as the collection in a OneToMany relation.

* web contains the web demo/test applications
    * websocket shows haw a websocket java/javascript works. Uses a chat like mechanism and a delayed echo

* cdi CDI related demo/test
    * event CDI events between JAX-RS, EJB and WebSockets
    * jms verry simple jms sample, send message from JAX-RS to an MDB
    * aop a @Interceptor sample. TODO: @InterceptorBinding with the interceptor in beans.xml
    * spring Shows how to integrated Spring managed beans into your CDI manged beans. Bridging between Spring and CDI.

* jaxb JAXB related stuff, not really JavaEE but it is related
    * xsd2java Shows how to generated java classes from an xsd and vice verse
    * refid Shows how to handle refid/id constructs using an xsd and annotations, Work in progrss!