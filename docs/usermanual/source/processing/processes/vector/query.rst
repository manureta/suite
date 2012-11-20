.. _processing.processes.vector.query:

.. warning:: Document Status: **Requires copyedit review (MD)**

Query
=====

Description
-----------

The ``gs:Query`` process queries a feature collection, 
using an optional filter expression to select a subset of the features.
Optionally, a subset of the feature collection attributes 
can be specified to be returned.
It can be thought of as an equivalent to a SQL ``SELECT...FROM...WHERE...`` statement.
   
Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin` and returns :ref:`processing.processes.formats.fcout`.

Inputs
^^^^^^

.. list-table::
   :header-rows: 1
   :widths: 25 35 20 20

   * - Name
     - Description
     - Type
     - Required
   * - ``features``
     - Input features
     - :ref:`FeatureCollection <processing.processes.formats.fcin>`
     - Yes
   * - ``filter``
     - The filter to apply
     - String
     - No
   * - ``attribute``
     - Name of an attribute to include in the output
     - String
     - No

Outputs
^^^^^^^

.. list-table::
   :header-rows: 1
   :widths: 25 35 40

   * - Name
     - Description
     - Type
   * - ``result``
     - The feature collection that is the result of the query
     - :ref:`FeatureCollection <processing.processes.formats.fcout>`
     
Usage notes
--------------

* The ``filter`` can be specified using either the XML Filter Encoding language
  or the textual CQL language
* If no attributes are specified explicitly all input attributes are output

Examples
--------

The following example queries the Medford zoning layer to return large zoning parcels 
(using a CQL filter and the ``area`` filter function), with a subset of the attributes.

Input Parameters:

- ``features``: ``medford:zoning``
- ``filter``: ``area(the_geom) > 0.0001``
- ``attribute``: ``the_geom``
- ``attribute``: ``cityzoning``
- ``attribute``: ``zone``

:download:`Download complete XMl request <xml/queryexample.xml>`.

.. figure:: img/queryexampleUI.png

   *gs:Query example parameters*

Relationship to other processes
---------------------------------

* The :ref:`gt:Transform <processing.processes.vector.transform>` process offers complementary functionality.  
  It allows attributes to be created or recomputed.
  The two processes can be chained to allow both filtering and computing new attributes.


