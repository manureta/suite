.. _processing.processes.raster.georectifycoverage:

.. warning:: Document Status: **Requires example and images edited (MP)**

GeorectifyCoverage
==================

Description
-----------

The ``gs:GeorectifyCoverage`` process takes a non-georeferenced grid coverage and, along with a set of "ground-control points" (GCPs), creates a georeferenced grid coverage.  This process is knows as *georectification*.

.. figure:: img/georectifycoverage.png

   *gs:GeorectifyCoverage*

.. todo:: I don't think this image gets the idea of warping across.

A ground-control point is comprised of a pair of point coordinates, one containing the grid coordinates (row, column) and the other containing the world coordinates of the corresponding point location. A set of GCPs allows for computation of a mathematical relationship between grid and world coordinates, thus enabling georectification of a full grid coverage.

The process calls the external application ``gdal_warp`` which performs the actual georectification process.


Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.rasterin` and returns :ref:`processing.processes.formats.rasterout`.

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
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required
   * - ``gcp``
     - List of Ground Control Points
     - String
     - Required
   * - ``bbox``
     - Bounding box of the output grid coverage
     - Envelope
     - Optional
   * - ``targetCRS``
     - CRS to use for the output coverage
     - CoordinateReferenceSystem
     - Required
   * - ``width``
     - Width of the resulting grid coverage, in cells
     - Integer
     - Optional
   * - ``height``
     - Height of the resulting grid coverage, in cells
     - Integer
     - Optional
   * - ``warpOrder``
     - Order of the warping polynomial (1 to 3)
     - Integer
     - Required
   * - ``transparent``
     - Force output to have transparent background
     - Boolean
     - Required  
   * - ``store``
     - Indicates whether to keep the output file after processing
     - Boolean
     - Required  
   * - ``pathName``
     - Pathname where the output file is stored
     - String
     - Required       

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - The georectified output grid coverage.
     - :ref:`GridCoverage2D <processing.processes.formats.rasterout>`
   * - ``path``
     - The pathname of the generated raster in the server.
     - String 


Usage notes
-----------

* Points are entered as a string of comma *and* space-separated values in the form of [x, y] or [x, y, z].
* Each ground-control point is formed by two coordinates. The first contains the grid coordinates, and the second contains the world coordinates.
* The output layer is produced in the CRS of the selected ``targetCRS``. Coordinates of GCPs are assumed to use the same CRS.
* If the ``bbox`` parameter is used, the output layer will be clipped to that extent.
* Coordinates of the clipping bounding box are assumed to be in the selected ``targetCRS``.
* If ``width`` and ``height`` parameters are used, the resulting georectified coverage will be resampled to adapt to those values.
* The ``width`` and ``height`` parameters must both be provided, otherwise either will be ignored.

.. todo:: Example needed.

.. todo:: Please elaborate on ``warpOrder``. It's not clear to me what this parameter does.
