<h1 align="center">TRUSTED NETWORK REST API</h1>

<div>
    <h3>Overview</h3>
    <p>This api suggests sending messages and CRUD pattern endpoints to user.</p>
    <p>To start and try the api you just need to Start this Spring project on your IDE. The trusted network rest api has been created as a task during participation in DevChallenge XII contest at the online stage</p>
</div>

<div>
    <h3>Content list</h3>
    <ul>
        <li><p>Overview</p></li>
        <li><p>Technologies used</p></li>
        <li><p>Database structure</p></li>
        <li><p>Endpoints description</p></li>
        <li><p>Postman demonstration</p></li>
        <li><p>Swagger</p></li>
        <li><p>Work principles description</p></li>
        <li><p>Conclusion</p></li>
    </ul>
    <p style="margin-bottom: 30px"></p>
</div>

<div>
    <h3>Technologies used</h3>
    <p></p>
    <ul>
        <li><p>Spring boot framework (as a base of this project)</p></li>
        <li><p>Mapstruct (DTO - Object - DTO converter for increasing data safety and usability)</p></li>
        <li><p>PostgreSQL</p></li>
        <li><p>JUnit</p></li>
        <li><p>Mockito</p></li>
        <li><p>Swagger</p></li>
    </ul>
</div>

<div>
    <h3>Files structure</h3>
</div>

<div>
    <h3>Database structure</h3>
    <p>As you can see on the image below, 3 tables have been created for this project. </p>
    <div>
        <h5>The 'persons' table contains Person entities with</h5>
        <ul>
            <li>'id' varchar column (as required in the task)</li>
            <li>'topics' jsonb column: the list of topic names that is followed by this Person</li>
            <li>'connections' jsonb column: the map of pairs (Person id -> Trust level) that is trusted by this Person</li>
        </ul>
    </div>
    <div>
        <h5>The 'messages' table contains Message entities with</h5>
        <ul>
            <li>'id' integer column</li>
            <li>'text' varchar column: the text of the message</li>
            <li>'topics' jsonb column: the list of topic names that this Message is related to</li>
            <li>'from_person_id' varchar column: id of sender</li>
            <li>'min_trust_level' integer column: trust level that is needed to receive this message</li>
            <li>'destinations' jsonb column: list of receivers' (Person) ids</li>
        </ul>
    </div>
    <div>
        <h5>The 'messages_directed' table contains MessageDirected entities with</h5>
        <ul>
            <li>'id' integer column</li>
            <li>'text' varchar column: the text of the message</li>
            <li>'topics' jsonb column: the list of topic names that this Message is related to</li>
            <li>'from_person_id' varchar column: id of sender</li>
            <li>'min_trust_level' integer column: trust level that is needed to receive this message</li>
            <li>'path' jsonb column: LinkedList of receivers' (Person) ids that participated in trust delivery of the message</li>
        </ul>
    </div>
    <img src="demonstration_images/database.jpg" alt="database structure diagram">
</div>

<div> 
    <h3>Endpoints description</h3>
    <p>The localhost is used with port 8080</p>
    <h5>There are the following endpoints: </h5>
    <ul>
        <li>
            <p style="color: #d7d779">/api/people/{id}/trust_connections</p>
            <p>(POST request)</p>
            <p>For updating/adding new trust connections set in Request Body to requested Person by id parameter</p>
        </li>
        <li>
            <p style="color: #d7d779">/api/people/{id}/trust_connections</p>
            <p>(DELETE request)</p>
            <p>For deleting trust connections set in Request Body from requested Person by id parameter</p>
        </li>
        <li>
            <p style="color: #d7d779">/api/people</p>
            <p>(POST request)</p>
            <p>To add a new Person with id and topics set in Request Body</p>
        </li>
        <li>
            <p style="color: #d7d779">/api/people{id}</p>
            <p>(GET request)</p>
            <p>To get a Person by id parameter</p>
        </li>
        <li>
            <p style="color: #d7d779">/api/messages</p>
            <p>(POST request)</p>
            <p>To send a message with text, topics, from_person_id, min_trust_level 
                parameters set in Request Body to all persons from connections field 
                of sender that have all included topics and have trust level equal or 
                higher than min_trust_level of the message. All receivers will transfer 
                this message further through their connections according to the same 
                conditions and so on and so on (broadcasting)</p>
        </li>
        <li>
            <p style="color: #d7d779">/api/path</p>
            <p>(POST request)</p>
            <p>To send a message with text, topics, from_person_id, min_trust_level 
                parameters set in Request Body to one the nearest to sender person  
                with appropriate topics. Intermediary persons in chain can not have 
                topic condition as true but should have trust level equal or higher
</p>            than min_trust_level.
        </li>
        <li>
            <p style="color: #d7d779">/api/trusted/messages</p>
            <p>(POST request)</p>
            <p>To send a message with text, topics, from_person_id, min_trust_level 
                parameters set in Request Body to all persons from connections field 
                of sender that have trust level equal or higher than min_trust_level
                of the message.
            </p>
        </li>
    </ul>
</div>

<div>
    <h3>Postman demonstration</h3>
    <div>
        <h5>The current structure of the connections and persons in database:</h5>
        <img src="demonstration_images/network.jpg" alt="network structure diagram">
    </div>
    <div>
        <h5>POST request to <span style="color: #d7d779">/api/people/{id}/trust_connections</span></h5>
        <img src="demonstration_images/trust_connections_post.jpg" alt="/api/people/{id}/trust_connections">
    </div>
    <div>
        <h5>POST request to <span style="color: #d7d779">/api/people</span></h5>
        <img src="demonstration_images/people_post.jpg" alt="/api/people">
    </div>
    <div>
        <h5>GET request to <span style="color: #d7d779">/api/people</span></h5>
        <img src="demonstration_images/people_get.jpg" alt="/api/people{id}">
    </div>
    <div>
        <h5>POST request to <span style="color: #d7d779">/api/messages</span></h5>
        <img src="demonstration_images/messages.jpg" alt="/api/messages">
    </div>
    <div>
        <h5>POST request to <span style="color: #d7d779">/api/path</span></h5>
        <img src="demonstration_images/path.jpg" alt="/api/path">
    </div>
    <div>
        <h5>POST request to <span style="color: #d7d779">/api/trusted/messages</span></h5>
        <img src="demonstration_images/trusted_messages.jpg" alt="/api/trusted/messages">
    </div>
    
</div>

<div>
    <h3>Swagger</h3>
    <p>After you run this application in you IDE, the swagger will be available by the address: </p>
    <p><a href=""></a></p>
</div>

<div>
    <h3>Work principles description</h3>
    <p>The create, read, update, delete methods are the standard CRUD methods from JPA Repository that are implemented in Service classes</p>
    <p>Endpoint "api/messages" calls for broadcasting() method that is implemented in MessageServiceBean class. 
        This method calls another recursive method and returns the final value from the recursion. 
        The recursive method is broadcastToChainOfPeople() method that defines the Persons connections and check them for 
        message requirements. In case of fulfillment, sends message to the allowed Person objects (saves message in database)
        and step into recursion but with new Person object as a sender. This new Person object is a receiver of previous message. 
        The chain of calls will be stopped when there are no "unchecked" Person objects or when Person as a sender will
        not have connections that are allowed to receive the message according to topics and trust level condition.</p>
    <p>Endpoint "api/path" calls for directBroadcasting() method that is implemented in MessageServiceBean class. 
        This method calls another method findTheShortestPathInGraph() to find the shortest path to Person with topics included 
        all the specified in message. After receiving the shortest path, the method creates the response map body with "from" and
        "path" keys. 
        findTheShortestPathInGraph() method that defines the shortest path to the Person in the network that have topic as in message. 
        However, this person should be found according to the trust level condition. This means that if sender has people with trust levels
        more or equals to the minimum in message, the message can use this connection in chain of sending. In other words, 
        even if a person who is trust have no all required topics, it can be used to pass the message further in network 
        among this person's trusted connections.<br>
        After the sender sends the message, the network is reviewed as a Graph object. To find the shortest path, method starts 
        go through the levels of this Graph, one by one, and when the Person with appropriate topics found, the path from sender
        to this winner person is saved and displayed.</p>
    <p>Endpoint "api/trusted/messages" calls for broadcastToTrusted() method that is implemented in MessageServiceBean class that finds all the 
        connections of the current Person that are fulfill requirements of the topic (has included in message object topics)
        and has trusted level more or equals to the minimum specified in the message field. </p>
</div>
<div>
    <h3>Conclusion</h3>
    <p></p>
    <p>As a conclusion I would like to assume that I managed to complete this task using some part of "functional" programming and I'm sure that 
        the methods could be refactored even more and simplified. However, the task is really liked and interesting and I suppose, 
        useful in social media development. </p>
</div>