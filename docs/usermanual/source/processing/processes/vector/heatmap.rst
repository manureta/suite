.. _processing.processes.vector.heatmap:

.. warning:: Document Status: **Requires images edited and questions answered (MP)**

Heatmap
=======

Description
-----------

The ``gs:Heatmap`` process takes a points feature collection and generates a raster layer representing the density of those features.

Calculation is done by taking a given radius around each cell of the resulting grid coverage and adding up the values from features falling within that radius. The "influence" of a feature is determined by its distance to the cell. Specifically, a `Gaussian function <http://en.wikipedia.org/wiki/Gaussian_function>`_ is used to calculate the influence of a given feature occurrence at a given distance. The resulting coverage is normalized to have values within in the [0,1] range.

.. figure:: img/heatmap.png

   *gs:Heatmap*

Features can be weighted to indicate that the influence of a feature should be lower or higher. For instance, if the resulting grid coverage will be a population density coverage, and it is to be created from points representing cities, features should be weighted using the corresponding attribute which represents the population of each city.

.. figure:: img/heatmapweighted.png

   *gs:Heatmap weighted*

.. todo:: These figures look great, but they show values in the 1-3 range, which conflict with the idea of (0,1) normalization. Change to reflect this?

Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.fcin` and returns :ref:`processing.processes.formats.rasterout`.

Inputs
~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Usage
   * - ``data``
     - Input feature collection
     - :ref:`SimpleFeatureCollection <processing.processes.formats.fcin>`
     - Required
   * - ``radiusPixels``
     - Radius of the density kernel in pixels
     - Integer
     - Required
   * - ``weightAttr``
     - Name of the attribute to use for data point weight
     - String
     - Optional
   * - ``pixelsPerCell``
     - Resolution at which to compute the heatmap (in pixels). Default = 1
     - Integer
     - Optional
   * - ``outputBBOX``
     - Bounding box of the output
     - ReferencedEnvelope
     - Required     
   * - ``outputWidth``
     - Width of output raster in pixels
     - Integer
     - Required
   * - ``outputHeight``
     - Width of output raster in pixels
     - Integer
     - Required     

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - Output heatmap
     - :ref:`GridCoverage2D <processing.processes.formats.rasterout>`


Usage notes
-----------

* The values of the weighting attribute  (``weightAttr``) must resolve to a numeric value, otherwise the feature will have a weight value of 1.
* The values of the weighting attribute (``weightAttr``) do not affect the range of values in the output grid coverage, as the range is always normalized to 1. The resulting coverage represents the probability distribution of the process represented by the input feature collection.
* While this process is typically used with point features, all types of features are accepted. If features other than points are used, the centroid of the geometry is used as its representative point.

.. todo::

   Don't follow this, please revise. What ``ReferenceEnvelope``?

   * The output layer is produced in the CS of the selected ``ReferenceEnvelope``. Input features can have a different CRS, and they will be reprojected if needed.


Examples
--------

Calculating a heatmap of schools
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

.. todo:: This example text doesn't match the example. What is this supposed to be?

The following example creates a heatmap based on the ``medford:schools`` layer.

Input parameters:

* ``data``: ``medford:schools``
* ``radiusPixels``: 100
* ``weightAttr``: [Blank]
* ``pixelsPerCell``: [Blank]
* ``outputBBOX``: 

  * ``minX``: -122.97
  * ``minY``: 42.23
  * ``maxX``: -122.80
  * ``maxY``: 42.44
  * ``CRS``: ``EPSG:4326`` 

* ``outputWidth``: 680
* ``outputHeight``: 840

:download:`Download complete XML request <xml/heatmap.xml>`.

.. figure:: img/heatmapUI.png

   *gs:Heatmap example parameters*

The resulting heatmap looks like this:

.. figure:: img/heatmapexample.png

   *gs:Heatmap example output*

The above result represents the density of school buildings. To compute a density map of students in those schools places, the input feature collection can be weighted by adding the ``weightAttr`` parameter, and setting it to the ``students`` attribute. This attribute shows the number of students in each school.

Input parameters:

* ``data``: ``medford:schools``
* ``radiusPixels``: 100
* ``weightAttr``: ``students``
* ``pixelsPerCell``: [Blank]
* ``outputBBOX``: 

  * ``minX``: -122.97
  * ``minY``: 42.23
  * ``maxX``: -122.80
  * ``maxY``: 42.44
  * ``CRS``: ``EPSG:4326`` 

* ``outputWidth``: 680
* ``outputHeight``: 840

:download:`Download complete XML request <xml/heatmap2.xml>`.

.. figure:: img/heatmapUI2.png

   *gs:Heatmap example parameters*

The resulting heatmap has a slightly different output than before.

.. figure:: img/heatmapexample2.png

   *gs:Heatmap example output (weighted)*


Related processes
-----------------

.. todo::

   ``ReferenceEnvelope`` is never explained, so please do so or alternately remove/rephrase this entry.

   * Since this process requires an input of type ``ReferenceEnvelope`` to set the area covered by the output grid coverage, the ``gs:Bounds`` process can be used to extract the required extent from a given feature collection. Particularly, it is of interest to extract the envelope of the same layer used as input for the ``gs:Heatmap`` process so the extent of the output coverage is that of the input feature collection.

