.. _sysadmin.caching.seeding.rest:

Seeding a tile cache through the REST API
=========================================

.. warning:: Document status: **Requires technical review (MP)**

The tile caching system has a REST API that can start, stop, and monitor seed tasks.

.. note:: Read more about the `GeoWebCache REST API <../../geowebcache/rest/seed.html>`_. 

The examples below use the `cURL <http://curl.haxx.se>`_ utility, though any HTTP-capable tool or library can be used.

Starting a seeding task
-----------------------

Request parameters in an XML file
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#. Construct an XML tag structure with the following format:

   .. code-block:: xml

      <seedRequest>
        <name>LAYERNAME</name>
        <bounds>
          <coords>
            <double>MINX</double>
            <double>MINY</double>
            <double>MAXX</double>
            <double>MAXY</double>
          </coords>
        </bounds>
        <srs><number>SRS</number></srs>
        <zoomStart>START_ZOOM_LEVEL</zoomStart>
        <zoomStop>STOP_ZOOM_LEVEL</zoomStop>
        <format>IMAGE_FORMAT</format>
        <type>OPERATION</type>
        <threadCount>THREADS</threadCount>
      </seedRequest>

   where:

   * ``LAYERNAME``—Fully qualified layer name, including namespace prefix.
   * ``MINX``, ``MINY``, ``MAXX``, ``MAXY``—Minimum and maximum X and Y values for extent. Leave this section out if seeding the entire extent.
   * ``SRS``—SRS code for the layer. Must match a given grid set. Should be a bare SRID number (``4326``) not a full EPSG code or URL.
   * ``START_ZOOM_LEVEL``—Lowest zoom level to generate tiles. Smallest allowed value is ``0``.
   * ``STOP_ZOOM_LEVEL``—Highest zoom level to generate tiles. See :ref:`sysadmin.caching.seeding.considerations` for advice on determining which zoom levels to seed.
   * ``IMAGE_FORMAT``—Image format for tiles. Must be a MIME type such as ``image/png``.
   * ``OPERATION``—Determines the operation. One of ``seed``, ``reseed``, or ``truncate``. Select :guilabel:`Seed` in most cases.
   * ``THREADS``—Number of concurrent threads to use in the seeding process. Value to use is system-dependent, though to minimize the chance of a task getting blocked, it is a good idea to use a value of at least 2.

#. Save this file and run the following command:

   .. code-block:: console

      curl -v -u ADMINUSER:PASSWORD -XPOST -H "Content-type: text/xml" -T "FILE.xml" "http://GEOSERVER_URL/gwc/rest/seed/LAYERNAME.xml"

   where:

   * ``ADMINUSER``—User name with administrator role
   * ``PASSWORD``—Password for the admin user
   * ``FILE.xml``—File of the XML file created above with optional path
   * ``GEOSERVER_URL``—Location of GeoServer instance (such as `http://localhost:8080/geoserver/``)
   * ``LAYERNAME``—Fully qualified layer name (must match layer name in XML)

The result, if successful should look something like this:

.. code-block:: console

   * About to connect() to localhost port 8080 (#0)
   *   Trying 127.0.0.1...
   * connected
   * Connected to localhost (127.0.0.1) port 8080 (#0)
   * Server auth using Basic with user 'admin'
   > POST /geoserver/gwc/rest/seed/usa:states.xml HTTP/1.1
   > Authorization: Basic YWRtaW46Z2Vvc2VydmVy
   > User-Agent: curl/7.28.0
   > Host: localhost:8080
   > Accept: */*
   > Content-tpye: text/xml
   > Content-Length: 406
   > Expect: 100-continue
   >
   < HTTP/1.1 100 Continue
   * We are completely uploaded and fine
   < HTTP/1.1 200 OK

.. warning:: If any part of your request is malformed (for example, an incorrect image MIME type), the request will not succeed, even though the response will not show an error.

Request parameters in request body
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

It is also possible, if unwieldy, to embed the XML POST request directly in the command. The results should be identical.  The single command below is wrapped over multiple lines.

.. code-block:: console
   
   curl -v -u ADMINUSER:PASSWORD -XPOST -H "Content-type: text/xml"
     -d "<seedRequest><name>LAYERNAME</name><bounds><coords><double>MINX</double>
     <double>MINY</double><double>MAXX</double><double>MAXY</double></coords>
     </bounds><srs><number>SRS</number></srs><zoomStart>START_ZOOM_LEVEL
     </zoomStart><zoomStop>STOP_ZOOM_LEVEL</zoomStop><format>IMAGE_FORMAT
     </format><type>OPERATION</type><threadCount>THREADS</threadCount>
     </seedRequest>" "http://GEOSERVER_URL/gwc/rest/seed/LAYER_NAME.xml"

where all of the variables are defined as above. Note that ``-d`` is used here to denote data inside the request , not ``-T`` as used above that denotes a file to be transferred.


Monitoring task status
----------------------

Currently running seed tasks can be monitored from an HTTP request as well.

#. To see what seed tasks are running for a particular layer, execute the following request::

     curl -u ADMINUSER:PASSWORD -XGET "http://GEOSERVER_URL/gwc/rest/seed/LAYERNAME.json"

   The response will be a JSON string containing information on the currently running seed tasks, if any. For each task, the following array will be shown::

     [TILESCREATED, TOTALTILES, TIMEREMAINING, TASKID, STATUS]

   where:

   * ``TILESCREATED``—Total number of tiles created by that particular task
   * ``TOTALTILES``—Total number of tiles to be created
   * ``TIMEREMAINING``—Estimated time (in seconds) remaining for the task
   * ``TASKID``—Numerical ID for the task. IDs are integers and are given out sequentially.
   * ``STATUS``—Status of the task. Can be one of the following values:

     * ``-1``—Aborted
     * ``0``—Pending
     * ``1``—Running (the most likely value)
     * ``2``—Done

#. For example, assuming two seed tasks were started for a given layer, the response might look like this:

   .. code-block:: json

      {"long-array-array":[[4368,63815852130,1117653851,7,1],[4352,63815852130,1129094656,8,1]]}

   In this case, the first task, where 63 billion tiles need to be created, will be completed in approximately 1.1 billion seconds, or 35.4 years.

#. It is possible to show all seed tasks for all layers by running the following command::

     curl -u ADMINUSER:PASSWORD -XGET "http://GEOSERVER_URL/gwc/rest/seed/LAYERNAME.json"

   The downside is that while all task arrays like above will be displayed, it won't be clear which seed task corresponds to which layer.

.. todo:: So how do you find out which seed task corresponds to which layer?

Killing tasks
-------------

Seed tasks can be stopped (killed) from an HTTP request as well.

Killing a single task
~~~~~~~~~~~~~~~~~~~~~

#. To kill a single task, execute the following request::

     curl -v -u ADMINUSER:PASSWORD -d "kill_thread=1&thread_id=TASKID"  "http://GEOSERVER_URL/gwc/rest/seed/LAYERNAME"

#. The response will contain the following text::

     Requested to terminate task TASKID.

#. If the TASKID is incorrect, you will receive an error::

     Sorry, either task TASKID has not started yet, or it is a truncate task that cannot be interrupted.

Killing all tasks for a given layer
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

#. To kill all tasks for a single layer, execute the following request::

     curl -v -u admin:geoserver -d "kill_all=all"  "http://GEOSERVER_URL/gwc/rest/seed/LAYERNAME"

#. The response will contain the following text::

     Requested to terminate all tasks.

   followed by the layer name in question.

Killing all tasks
~~~~~~~~~~~~~~~~~

#. To kill all tasks for all layers, execute the following request::

     curl -v -u admin:geoserver -d "kill_all=all"  "http://GEOSERVER_URL/gwc/rest/seed"

   .. note:: The only difference between this command and the one above that kills all tasks for a single layer is the endpoint. When the ``LAYERNAME`` is omitted, the process will apply to all layers.

#. The response will contain the following text::

     Requested to terminate all tasks.

   followed by all layers that currently have seed tasks associated with them.

