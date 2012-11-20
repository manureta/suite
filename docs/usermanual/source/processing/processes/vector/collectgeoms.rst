.. _processing.processes.vector.collectgeometries:

.. warning:: Document Status: Requires technical review

CollectGeometries
==================

Description
-----------

The ``gs:CollectGeometries`` process turns all the features in a feature collection into a single ``GeometryCollection`` object. This allows using geometries from feature collections in processes that require an input of type ``Geometry``. The process gathers the default geometries from the feature collection and constructs a ``GeometryCollection`` based on them.

.. figure:: img/collectgeoms.png
   
   *gs:CollectGeometries*


Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin` and returns :ref:`processing.processes.formats.geomout` 

Inputs
^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Required
   * - ``features``
     - Input feature collection
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcin>`
     - Yes

Outputs
^^^^^^^

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - The geometry containing default geometries from features in the input feature collection
     - :ref:``GeometryCollectionCollection <processing.processes.formats.geomout>`


Usage notes
--------------

* The :term:`CRS` of the resulting geometry is the same one of the input feature collection.

Related processes
---------------------------------

* This process is of interest for all those processes taking inputs of type ``Geometry``, as it allows to obtain valid input objects from feature collections. To see an example of such usage, see the documentation for the :ref:`gs:Clip <processing.processes.vector.clip>` process.
* To perform the opposite conversion and get a feature collection from a geometry, use the :ref:`gs:Feature <processing.processes.vector.feature>` process.






