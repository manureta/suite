.. _processing.processes.raster.cropcoverage:

.. warning:: Document Status: **Requires copyedit review (MP)**

CropCoverage
============

Description
-----------

The ``gs:CropCoverage`` process takes a raster as input as well as a polygon geometry, and outputs a raster whose bounds are equal to the polygon. The resulting output coverage contains the values from the input coverage, but only on those cells that fall within the polygon of the input feature collection. The remaining cells within the extent of the output coverage will have a "no-data" value.

The image below shows a descriptive example of how the ``gs:CropCoverage`` process works.

.. figure:: img/cropcoverage.png

   *gs:CropCoverage*


Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.rasterin`, as well as :ref:`processing.processes.formats.geomin`, and returns :ref:`processing.processes.formats.rasterout`.

Inputs
~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Required
   * - ``coverage``
     - Coverage to be cropped
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Yes     
   * - ``cropShape``
     - Geometry to use for cropping
     - :ref:`Geometry <processing.processes.formats.geomin>`
     - Yes

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - The cropped coverage
     - :ref:`GridCoverage2D <processing.processes.formats.rasterout>`

Usage notes
-----------

* While polygons are most commonly used as the input geometry, this process can be used with geometries of all types.

Examples
--------

The following example shows how to crop the ``medford:elevation`` coverage by the ``medford:citylimits`` feature collection. The :ref:`gs:CollectGeometries <processing.processes.vector.collectgeoms>` process is used as an intermediary step in order to transform ``medford:citylimits`` feature collection into a geometry..

Input parameters for ``gs:CollectGeometries``:

* ``features``: ``medford:citylimits``

Input parameters for ``gs:CropCoverage``:

* ``coverage``: ``medford:elevation``
* ``cropShape``: Output from ``gs:CollectGeometries``

:download:`Download complete chained XML request <xml/cropcoverageexample.xml>`.

.. figure:: img/cropcoverageexampleUI.png

   *gs:CropCoverage example parameters (part 1)*

.. figure:: img/cropcoverageexampleUI2.png  

   *gs:CropCoverage example parameters (part 2)*

.. figure:: img/cropcoverageexample.png

   *gs:CropCoverage example output*

Related processes
-----------------

* To crop a coverage using a feature collection instead of a geometry, use the :ref:`gs:CollectGeometries <processing.processes.vector.collectgeoms>` to create a ``GeometryCollection`` object from the feature collection, and then use that output as input to this process, as shown in the example above.
* Use this process whenever you want to restrict the calculation performed by another process to the area covered by a geometry. No-data values are ignored by most raster processes, so calculations will be performed only on the cells with data values.

