.. _processing.processes.vector.feature:

.. warning:: Document Status: **Requires copyedit review (MD)**

Feature
=======

Description
-----------

The ``gs:Feature`` process converts a geometry into a feature collection 
containing a single feature with a geometry attribute having the value of the input geometry.

This process is used for providing explicitly-defined geometry as input to other processes
which take feature collections as input.
   
Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.geomin` and returns :ref:`processing.processes.formats.fcout`.

Inputs
^^^^^^

.. list-table::
   :header-rows: 1
   :widths: 25 35 20 20

   * - Name
     - Description
     - Type
     - Required
   * - ``geometry``
     - Input geometry
     - Geometry
     - Yes
   * - ``crs``
     - CRS identifier
     - String
     - No
   * - ``typeName``
     - Typename for generated feature collection
     - String
     - Yes

Outputs
^^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - Generated feature
     - :ref:`FeatureCollection <processing.processes.formats.fcout>`
     
Usage notes
--------------

* A coordinate reference system (CRS) identifier can be provided to 
  indicate the CRS of the geometry.
  The CRS identifier is a standard EPSG code, such as ``EPSG:4326``.
  Some geometry input formats (such as GML) allow specifying the geometry CRS directly.
* The typename of the output feature collection must be provided.
* The name of the output feature attribute containing the geometry is ``geom``.
* No other attributes are created.


Examples
--------

The following example converts a WKT linestring in WGS84 into a feature collection.

Input parameters:

- ``geometry``: ``LINESTRING ( 0 0, 10 10 )``
- ``crs``: ``EPSG:4326``
- ``typeName``: ``example``

:download:`Download complete XMl request <xml/featureexample.xml>`.

.. figure:: img/featureexampleUI.png

   *gs:Feature example parameters*

Relationship to other processes
---------------------------------

* The ``gt:CollectGeometries`` process is the inverse of this process - it converts a feature collection into a single geometry.

* If additional scalar-valued attributes are needed, they can be added by chaining this process into the :ref:`gt:Transform <processing.processes.vector.transform>`  process.
