.. _processing.processes.raster.multiplycoverages:

.. warning:: Document Status: **Requires additional technical review and example (MP)**

MultiplyCoverages
=================

Description
-----------

The ``gs:MultiplyCoverages`` process takes two input grid coverages and perform a cell by cell multiplication on them, generating a new grid coverage. Each cell in the output grid has the value resulting from the multiplication of the corresponding values for that same cell in the input grids.

.. figure:: img/multiplycoverages.png

   *gs:MultiplyCoverages*



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
     - First input coverage
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required
   * - ``coverageB``
     - Second input coverage
     - :ref:`GridCoverage2D <processing.processes.formats.rasterin>`
     - Required    

Outputs
~~~~~~~

.. list-table::
   :header-rows: 1

   * - Name
     - Description
     - Type
   * - ``result``
     - The output grid coverage
     - :ref:`GridCoverage2D<processing.processes.formats.rasterout>`


Usage notes
-----------

* Both input grid coverages must have the same bounding box and the same cellsize. That means they must have the same width and height (in cells), so they match and they can be multiplied.
* The output grid coverage will have the same bounding box and cellsize as the input grids.
* Both input grids must have only one band.
* Given two probability raster with values in the (0,1) range (such as those produced by the ``gs:Heatmap`` process), applying this process to them represents the equivalent of a fuzzy logic AND operation.
* Multipliying two raster layers can be used to mask out certain cells in one of them, by using a mask grid with  zero or no-data values in those cells to be masked out. The  example proposed below shows a particular case of this application. This is useful as a data-preparation technique before executing other processes in which the input data should be restricted to a given area to get meaningful results, excluding values that are not within that area, but fall within the arbitrary boundary of the grid bounding box.


Examples
---------

Masking out areas outside of an elevation range
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The following example masks out all elevation areas beyond a certain elevation range, by multiplying the ``medford:elevation`` layer by a mask grid created using the ``gs:RangeLookup`` process and that same layer. This will allow for further calculation with values restricted to those cells within the elevation range.


Input parameters for ``gs:RangeLookup``

* ``data``: ``medford:elevation``
* ``band``: [Blank]
* ``ranges``: (1000;1500)
* ``noData``: [Blank]
* ``outputPixelValues``: 1

Input parameters for ``gs:MultiplyCoverages``

* ``coverageA``: ``medford:elevation``
* ``coverageA``: result of ``gs:RangeLookup``

:download:`Download complete chained XML request <xml/multiplycoverages.xml>`

.. figure:: img/multiplycoveragesUI.png

   *gs:MultiplyCoverages example parameters (part 1)*

.. figure:: img/multiplycoveragesUI2.png

   *gs:MultiplyCoverages example parameters (part 2)*   

.. figure:: img/multiplycoveragesexample.png

   *gs:MultiplyCoverages example result*


Related processes
-----------------

* The ``gs:AddCoverages``<processing.processes.raster.addcoverages>`` process performs a similar operation, adding cell values from two input grids instead of multiplying them.
* The masking process mentioned above can be performed using a polygon feature collection as mask, instead of a grid coverage. The ``gs:CropCoverage<processing.processes.raster.cropcoverage>`` process should be used in that case.

