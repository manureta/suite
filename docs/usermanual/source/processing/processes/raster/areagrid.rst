.. _processing.processes.raster.addoverages:

.. warning:: Document Status: **Requires additional technical review and example (MP)**

AreaGrid
=================

Description
-----------

The ``gs:AreaGrid`` creates a new grid coverage with a given bounding box and cellsize, in which each cell contains the actual area it occupies.

.. figure:: img/areagrid.png

   *gs:AreaGrid*



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
   * - ``coverageA``
     - Bounding box for the resulting raster, in WGS84 geographic coordinates.
     - ReferencedEnvelope
     - Required
   * - ``width``
     - Width of the resulting grid coverage, in cells
     - Integer
     - Required    
   * - ``height``
     - Height of the resulting grid coverage, in cells
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
     - The output grid coverage with area values
     - :ref:`GridCoverage2D<processing.processes.formats.rasterout>`


Usage notes
-----------

* The input envelope is assumed to use WGS84. If it uses a different CRS, that information will be ignored, and only its coordinates will be used.
* The output grid coverages has WGS84 as its CRS as well.
* Area values are expressed in square meters.
* Areas are calculated by reprojecting the original WGS84 coordinates of each cell boundary into an EckertIV projection.


Examples
---------

Calculating areas for a grid coverage of 1-minute cells 
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following example creates an area grid that covers the territory of peninsular Spain, with a cell resolution of 1 minute.

Input parameters:

* ``envelope``: 

  * ``minX``: -9.5
  * ``minY``: 36
  * ``maxX``: 3.5
  * ``maxY``: 43.5
  * ``CRS``: ``EPSG:4326`` 

* ``width``: 780
* ``height``: 450

:download:`Download complete XML request <xml/areagrid.xml>`

.. figure:: img/areagridUI.png

   *gs:AreaGrid example parameters*

.. figure:: img/areagridexample.png

   *gs:AreaGrid example result*




