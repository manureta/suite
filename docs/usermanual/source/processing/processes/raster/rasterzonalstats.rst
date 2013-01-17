.. _processing.processes.raster.rasterzonalstats:

.. warning:: Document Status: **Requires additional technical review and example (MP)**

RasterZonalStatistics
======================

Description
-----------

The ``gs:RasterZonalStatistics`` calculates statistics of a given raster layer within the polygons of a feature collection`` 

.. figure:: img/rasterzonalstats.png

   *gs:rangelookup*

An additional grid coverage with categories can be specified, so statistics are calculated considering both the polygons and the classes defined by this second grid coverage


Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.rasterin` :ref:`processing.processes.formats.fcin`, and returns :ref:`processing.processes.formats.fcout`.

Inputs
~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Usage
   * - ``data``
     - Input grid coverage to reclassify
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required
   * - ``band``
     - Band from which to take values. This index is zero-based. Default is zero (first band).
     - Integer
     - Optional
   * - ``zones``
     - Zone polygon features for which to compute statistics
     - :ref:`SimpleFeaturesCollection<processing.processes.formats.fcin>`
     - Optional       
   * - ``classification``
     - Raster whose values will be used as classes for the statistical analysis
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Optional   
   
       

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``statistics``
     - The output feature collection with statistics of the input grid coverage
     - :ref:`SimpleFeaturesCollection<processing.processes.formats.fcout>`


Usage notes
-----------

* Input layers can have different CRS's, and they will be reprojected if needed.
* If used, the ``classification`` parameter must be a grid coverage with one single band and integer values representing classes.
* The output feature collection contains all the attributes of the original feature collection, along with the following new ones where statistics are stored: *count, min, max, sum, avg, stddev*.
* Original attributes do not keep their original names, but are prefixed with the *z_* prefix instead.
* If a classification grid coverage is used, statistics are partitioned by classes according to the values in that coverage.
* If no cells are found within a given polygon to calculate of the parameters above, a NaN (Not a number) value will be assigned.

Examples
---------

Calculating elevation statistics for parks
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following example calculates elevation statistics for the parks in the Medford area. To do so, it uses the ``medford:elevation`` coverage and the ``medford:parks`` feature collection

Input parameters:

* ``data``: ``medford:elevation``
* ``band``: [Blank]
* ``zones``: ``medford:parks``
* ``classification``: [Blank]

:download:`Download complete XML request <xml/rasterzonalstats.xml>`

.. figure:: img/rasterzonalstatsUI.png

   *gs:RasterZonalStatistics example parameters*



Related processes
-----------------

* The ``gs:VectorZonalStats<processing.processes.raster.vectorzonalstats>`` performs a similar analysis, but taking a points layer instead of a raster layer, and calculating statistics of those points that fall within each polygon.

