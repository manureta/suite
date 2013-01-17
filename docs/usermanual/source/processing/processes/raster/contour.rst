.. _processing.processes.raster.contour:


Contour
=======================

Description
-----------

The ``gs:Contour`` process a feature collection of lines representing contour lines, extracted from a grid coverage. These contour lines (also known as *isolines*) represent curves along which the variable represented by the grid coverage has the same value. 


.. figure:: img/contour.png

   *gs:Contour*



Inputs and outputs
------------------

``gs:Contour`` accepts :ref:`processing.processes.formats.rasterin` and returns :ref:`processing.processes.formats.fcout`

Inputs
~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Usage
   * - ``data``
     - Input grid coverage
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required
   * - ``band``
     - Band to use for values to be contoured. If not specified, the first band of the coverage will be used
     - Integer
     - Optional
   * - ``levels``
     - Values at which to calculate contour lines
     - Double
     - Optional
   * - ``interval``
     - Interval between contour lines
     - Double
     - Optional
   * - ``simplify``
     - Indicates whether contour lines are simplified
     - Boolean
     - Optional
   * - ``smooth``
     - Indicates whether contour lines are smoothed using Bezier smoothing
     - Boolean
     - Optional
   * - ``roi``
     - Geometry delineating the region of interest (in raster coordinate system)
     - Geometry
     - Optional
   
     

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - Contour line features. Contour level is in value attribute 
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcout>`


Usage notes
-----------

* The resulting feature collection has the same CRS as the input grid coverage.
* The value corresponding to the coverage is added to the attributes of the resulting feature collection, in an attribute named ``value``.
* If the base coverage has a coarse resolution, contour lines ae likely to have a jagged appearance. The ``smooth`` parameter can be set to true to solve this. Be aware that, however, this does not create more precise contour lines. It applies a cosmetic modification to get better-looking lines, but it cannot overcome the limitations of the source data.

Examples
--------

Creating elevation contour lines
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The ``medford:elevation`` layer contains elevation data. The following example uses it to create elevation contour lines with an interval of 100 meters.

Input parameters:

* ``data``: ``medford:elevation``
* ``band``: [Blank]
* ``levels``: [Blank]
* ``interval``: 100
* ``simplify``: False
* ``smooth``: False
* ``roi``: [Blank]

:download:`Download complete XML request <xml/contour.xml>`

.. figure:: img/contourexampleUI.png

   *gs:BarnesSurface example parameters*

The resulting lines feature collection looks like this:

.. figure:: img/contourexample.png

   *gs:Contour output*



Creating temperature contour lines
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following example chains the ``gs:Contour`` process with the `gs:BarnesSurface<processing.processes.raster.barnes>` process to extract contour lines from a points feature collection. The ``gs:BarnesSeurface`` process computes an intermediate layer that is then vectorized to lines by the ``gs:Contour`` process.

Contour lines are computed for an interval of 1 degrees, and the computation is limited to the area of peninsular Spain.


Input parameters for ``gs:BarnesSurface``:

* ``data``: ``world_globedata_temp``
* ``valueAttr``: MxTmp
* ``dataLimit``: [Blank]
* ``scale``: 1
* ``convergence``: [Blank]
* ``passes``: 3
* ``minObservations``: [Blank]
* ``maxObservationDistance``: [Blank]
* ``noDataValue``: [Blank]
* ``pixelsPerCell``: [Blank]
* ``queryBuffer``: [Blank]
* ``outputBBOX``: 

  * ``minX``: -9.5
  * ``minY``: 36
  * ``maxX``: 3.5
  * ``maxY``: 43.5
  * ``CRS``: ``EPSG:4326`` 

* ``outputWidth``: 780
* ``outputHeight``: 450

Input parameters for ``gs:Contour``.

* ``data``: Output from ``gs:BarnesSurface``
* ``band``: [Blank]
* ``levels``: [blank]
* ``interval``: 5
* ``simplify``: False
* ``smooth``: False
* ``roi``: [Blank]

:download:`Download complete chained XML query <xml/cropcoverageexample.xml>`.

.. figure:: img/contourexampleUI2.png

   *gs:Contour example parameters (part 1)*

.. figure:: img/contourexampleUI3.png  

   *gs:Contour example parameters (part 2)*

.. figure:: img/contourexample2.png

   *gs:Contour example output*

Notice that, in this case, the detail of contour lines is directly affected by the size defined by the interpolation process, since that defines the resolution of the intermediate coverage from which contour lines are calculated.

Related processes
-----------------

* Other processes are available for converting a grid coverage into a feature collection (vectorizing). To extract polygons from a grid coverage, the :ref:`gs:PolygonExtraction <processing.processes.raster.polygonextraction>` can be used. To create a points feature collection, use the :ref:`gs:RasterAsPointCollection <processing.processes.raster.rasteraspoints>` process.

* The line simplification obtained by setting to true ``simplify`` parameter can be obtained by applying the `gs:Simplify<processing.processes.vector.simplify>`- process the the non-simplified contour lines obtained from the ``gs:Contour`` process when the ``simplify`` parameter is set to false.


More information
-----------------

This process can be used as a rendering transform in GeoServer. A common case for that is to use it along with the `gs:BarnesSurface<processing.processes.raster.barnes>`_ process, which can also be used as a render transform, to extract contour lines directly from points, as seen in the example above.



