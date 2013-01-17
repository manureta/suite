.. _processing.processes.raster.rangelookup:

.. warning:: Document Status: **Requires additional technical review and example (MP)**

RangeLookup
=================

Description
-----------

The ``gs:RangeLookup`` process takes an input grid coverage and reclassifies its values according to a set of defined ranges. For each range, a new value is defined, and all cells with values within the range will have that corresponding new value in the output grid coverage

.. figure:: img/rangelookup.png

   *gs:rangelookup*



Inputs and outputs
------------------

This process accepts :ref:`processing.processes.formats.rasterin` and returns :ref:`processing.processes.formats.fcout`.

Inputs
~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
     - Usage
   * - ``coverage``
     - Input grid coverage to reclassify
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required
   * - ``band``
     - Band from which to take values. This index is zero-based. Default is zero (first band).
     - Integer
     - Optional
   * - ``ranges``
     - List of ranges to use to reclassify the input coverage
     - List
     - Optional       
   * - ``outputPixelValues``
     - List of values to be assigned to the defined ranges
     - Boolean
     - No   
   * - ``noData``
     - Value to be assigned to pixels outside any range (defaults to 0)
     - Integer
     - Optional
       

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``reclassified``
     - The output reclassified grid coverage
     - :ref:`GridCoverage2D<processing.processes.formats.rasterout>`


Usage notes
-----------

* The output grid coverage has the same CRS and cellsize as the input grid.
* The data type of the output grid coverage is the smallest one that can hold the values used as new class values (those in the ``outputPixelvalues`` parameter).
* The number of elements in the list representing ranges has to be the same as the number of elements in that representing the output values.
* If two ranges overlap, the process will assign the value corresponding to the first of them to all cells in the overlapping range.
* The ``ranges`` parameter is entered as a string containing space-separated ranges. Each range is defined as a string in the form ``(START;END)``. If ``START`` is omitted, there is no lower limit in the range. If ``END`` is omitted, there is no upper limit. Instead of common brackets, square brackets ``[]`` can be used to indicate that the ``START`` or ``END`` value belong to the range.
     * To create ranges every 50 units from 0 to 200, the following string would be used: ``[0;50] [50;100] [100;150] [150;200]``
     * To create two ranges, one with all the values less than or equal to 1000, and another one with all values greater than 1000, the following string would be used: ``(;1000] (1000;)``


Examples
---------

Selecting suitable slope areas
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Assuming that a given process takes only place on slopes of less than 30 degrees, find out suitable areas using a slope grid. The ``medford:slope`` contains slope values in percentage, and the following example classifies them in two groups (suitable = 1, not suitable = 0) using the ``gs:RangeLookup`` process.


Input parameters:

* ``data``: ``medford:slope``
* ``band``: [Blank]
* ``ranges``: (0;15)
* ``noData``: [Blank]
* ``outputPixelValues``: 1

:download:`Download complete XML request <xml/rangelookup.xml>`

.. figure:: img/rangelookupUI.png

   *gs:RangeLookup example parameters*

.. figure:: img/rangelookupexample.png

   *gs:RangeLookup example result*


Related processes
-----------------

* The ``gs:PolygonExtraction<processing.processes.raster.polygonextraction>`` process extracts polygons from a grid coverage, performing a similar reclassification first, to create an intermediate grid from which polygons can be extracted.

